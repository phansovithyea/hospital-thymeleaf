package com.ibiztechno.app.provider.samplegroup;


import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ibiztechno.app.helper.ExcelReader;
import com.ibiztechno.app.helper.ExcelSheetHelper;
import com.ibiztechno.app.helper.ReportHelper;
import com.ibiztechno.app.model.CodeName;
import com.ibiztechno.app.model.ListType;
import com.ibiztechno.app.model.FormType;
import com.ibiztechno.app.model.ReportView;
import com.ibiztechno.app.model.TreeType;
import com.ibiztechno.app.model.TreeView;
import com.ibiztechno.app.repository.sql.SqlRepository;
import com.ibiztechno.app.provider.samplegroup.SamplegroupService;
import com.ibiztechno.app.model.Account;

@Controller
@RequestMapping("/samplegroup")
public class SamplegroupController {
	@Autowired
	SamplegroupService service;
	@Autowired
	ExcelReader excelReader = new ExcelReader();
	@Autowired
	ReportHelper reportHelper = new ReportHelper();
	List<ListType> exportListTypes;

	List<FormType> forms;
//	action
	List<CodeName> actions;
	List<CodeName> links;
	List<CodeName> enquiries;
	List<CodeName> imports;
	List<CodeName> exports;
	List<CodeName> maintenances;
	List<CodeName> reports;
//	search
	List<CodeName> filters;
	List<CodeName> groupBys;
	List<CodeName> favorites;
	
	List<TreeView> trees;
	
//	template parameter to create
	List<CodeName> typegroups;
	List<CodeName> temptypes;
	String userId;
	String lanType;

	@RequestMapping()
	public String index(@RequestParam(required = false) String search, Principal principal, HttpSession session,
			Model model, HttpServletRequest request, Account account,ListType listType, TreeType treeType) throws Exception {

		userId = principal.getName();
		lanType = account.getLanType(); // forms.get(0).getLanType();

		String permission = service.verifyPermission("V", principal.getName(), request.getRemoteAddr());

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		forms = service.getForms(lanType, userId);

		session.setAttribute("btnmodule", "/" + forms.get(0).getWebModule());
		session.setAttribute("modulename", forms.get(0).getFuncDesc());

		session.setAttribute("btncreate", forms.get(0).getWebModule() + "/create");
		session.setAttribute("pathString", forms.get(0).getFuncDesc() + " > " + forms.get(0).getWebListDesc());
		session.setAttribute("display", forms.size() > 0 ? forms.get(0).getWebListType() : "D");

		actions = service.getActions(lanType, userId);
		links = service.getLinks(lanType, userId);
		imports = service.getImports(lanType, userId);
		enquiries = service.getEnquiries(lanType, userId);
		exports = service.getExports(lanType, userId);
		maintenances = service.getMaintenances(lanType, userId);
		reports = service.getReports(lanType, userId);

		filters = service.getFilters(lanType, userId);
		groupBys = service.getGroupBys(lanType, userId);
		favorites = service.getFavorites(lanType, userId);

		String criteria = "";
		if (search != null) {
			if (!search.equals("")) {
				criteria = " AND Search like '%" + search + "%'";
			}
			// criteria=" AND " + SearchData.get("GroupDesc") +" like '%"+SearchData.get+""
			// for

		}
		treeType.setLanType(lanType);
		treeType.setUserID(userId);
		treeType.setCriteria(criteria);
		trees = service.getTrees(treeType);
		
		listType.setLanType(lanType);
		listType.setUserID(userId);
		listType.setCriteria(criteria);
		List<Map<String, Object>> listData = service.getList(listType);

		session.setAttribute("actions", actions);
		session.setAttribute("links", links);
		session.setAttribute("imports", imports);
		session.setAttribute("exports", exports);
		session.setAttribute("maintenances", maintenances);
		session.setAttribute("trees", trees);
		session.setAttribute("reports", reports);

		session.setAttribute("filters", filters);
		session.setAttribute("groupBys", groupBys);
		session.setAttribute("favorites", favorites);
		session.setAttribute("search", "/samplegroup");

		model.addAttribute("listData", listData);
		model.addAttribute("search", search);

		return "/index";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET) // model = viewBag
	public String Create(Samplegroup template, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {
		String permission = service.verifyPermission("N", principal.getName(), request.getRemoteAddr());

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		forms = service.getForms(lanType, userId);

		session.setAttribute("pathString", forms.get(0).getFuncDesc() + " > Create");

		return "/samplegroup/create";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String Create(@Valid Samplegroup template, Errors errors, Model model, Principal principal,
			HttpSession session, HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		
		model.addAttribute("samplegroup", template);
		template.setUserID(principal.getName());
		template.setRemoteAddr(request.getRemoteAddr());
		
		if (errors.hasErrors()) {
			return "/samplegroup/create";
		}

		try {
			String permission = service.verifyPermission("N", principal.getName(), request.getRemoteAddr());

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("samplegroup", new SamplegroupView());
				return "/samplegroup/create";
			}
			
			Long result = service.create(template);
			
			if (result > 0) {

				if (file.getOriginalFilename().length() > 0) {
					result = service.uploadFile(template, file);
				}
				if (result > 0) {
					model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
					model.addAttribute("samplegroup", new Samplegroup());
					return "redirect:/samplegroup";
				}
			}
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}

		return "/samplegroup/create";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String View(@RequestParam String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = service.verifyPermission("V", principal.getName(), request.getRemoteAddr());

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		forms = service.getForms(lanType, userId);

		List<SamplegroupView> contexts = service.getDetail(id);

		if (contexts.size() > 0) {
			model.addAttribute("samplegroup", contexts.get(0));
			session.setAttribute("pathString", forms.get(0).getFuncDesc() + " > View > " + contexts.get(0).getGroupDesc());
		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("samplegroup", new SamplegroupView());
		}

		model.addAttribute("id", id);

		return "/samplegroup/view";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String Update(@RequestParam String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = service.verifyPermission("E", principal.getName(), request.getRemoteAddr());

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		forms = service.getForms(lanType, userId);

		List<SamplegroupView> templates = service.getDetail(id);

		if (templates.size() > 0) {
			model.addAttribute("samplegroup", templates.get(0));
			session.setAttribute("pathString", forms.get(0).getFuncDesc() + " > Edit > " + templates.get(0).getGroupDesc());
		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("samplegroup", new SamplegroupView());
		}

		return "/samplegroup/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String Update(@Valid SamplegroupView template, Errors errors, Model model, Principal principal,
			HttpServletRequest request) {

		model.addAttribute("typegroups", typegroups);
		template.setUserID(principal.getName());
		template.setRemoteAddr(request.getRemoteAddr()); 
		 
		if (errors.hasErrors()) {
			return "/samplegroup/update";
		}

		try {
			String permission = service.verifyPermission("E", principal.getName(), request.getRemoteAddr());

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("samplegroup", new SamplegroupView());
				return "/samplegroup/update";
			}

			Long result = service.update(template);

			if (result > 0) {
				template = new SamplegroupView();
				model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
			}
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}

		model.addAttribute("samplegroup", template);

		return "/samplegroup/update";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String Delete(@RequestParam String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = service.verifyPermission("D", principal.getName(), request.getRemoteAddr());

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		forms = service.getForms(lanType, userId);

		List<SamplegroupView> templates =service.getDetail(id);

		if (templates.size() > 0) {
			model.addAttribute("samplegroup", templates.get(0));
			session.setAttribute("pathString",
					forms.get(0).getFuncDesc() + " > Delete > " + templates.get(0).getGroupDesc());
		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("samplegroup", new SamplegroupView());
		}

		return "/samplegroup/delete";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String Delete(SamplegroupView template, Model model, Principal principal, HttpServletRequest request) {

		model.addAttribute("samplegroup", template);
		template.setUserID(principal.getName());
		template.setRemoteAddr(request.getRemoteAddr()); 
		
		if (template.getGroupCode().equals("")) {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			return "/samplegroup/delete";
		}

		try {
			String permission = service.verifyPermission("D", principal.getName(), request.getRemoteAddr());

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("samplegroup", new SamplegroupView());
				return "/samplegroup/delete";
			}

			Long result = service.delete(template);

			if (result > 0) {
				model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
				model.addAttribute("samplegroup", new SamplegroupView());
				return "redirect:/samplegroup";
			}
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}

		return "/samplegroup/delete";
	}

	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public String Authorize(@RequestParam(required = false) String search, Model model, Principal principal,
			HttpSession session, HttpServletRequest request,ListType listType) throws Exception {

		String permission = service.verifyPermission("A", principal.getName(), request.getRemoteAddr());

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		forms = service.getForms(lanType, userId);

		String criteria = "";
		if (search != null) {
			if (!search.equals("")) {
				criteria = " AND Search like '%" + search + "%'";
			}
		}
		
		listType.setLanType(lanType);
		listType.setUserID(principal.getName());
		listType.setCriteria(criteria);
		List<Map<String, Object>> listData = service.getAuthorizeList(listType);

		session.setAttribute("btncreate", "/samplegroup/create");
		session.setAttribute("pathString",forms.get(0).getFuncDesc() + " > Authorize");
		session.setAttribute("display", "L");

		session.setAttribute("filters", filters);
		session.setAttribute("groupBys", groupBys);
		session.setAttribute("favorites", favorites);
		session.setAttribute("search", "/samplegroup/authorize");

		model.addAttribute("listData", listData);
		model.addAttribute("search", search);
		return "/authorize";
	}

	@RequestMapping(value = "/authorize/{id}", method = RequestMethod.GET)
	public String AuthorizeView(@PathVariable String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request,FormType formType) throws Exception {
		
		String permission = service.verifyPermission("A", principal.getName(), request.getRemoteAddr());

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		forms = service.getForms(lanType, userId);

		List<SamplegroupView> templates = service.getDetail(id);

		if (templates.size() > 0) {
			model.addAttribute("samplegroup", templates.get(0));
			session.setAttribute("pathString",
					forms.get(0).getFuncDesc() + " > Authorize > " + templates.get(0).getGroupDesc());
		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("samplegroup", new SamplegroupView());
		}

		model.addAttribute("id", id);
		
		return "/samplegroup/authorizeview";
	}

	@RequestMapping(value = "/authorize/{id}", method = RequestMethod.POST)
	public String AuthorizePost(@PathVariable String id, SamplegroupView template, Model model, Principal principal,
			HttpServletRequest request) throws Exception {
		
		model.addAttribute("id", id);
		model.addAttribute("samplegroup", template);
		template.setUserID(principal.getName());
		template.setRemoteAddr(request.getRemoteAddr()); 
		
		String permission = service.verifyPermission("A", principal.getName(), request.getRemoteAddr());

		if (permission.length() > 0) {
			model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
			model.addAttribute("samplegroup", new SamplegroupView());
			return "/samplegroup/authorizeview";
		}

		Long result =service.authorize(template);

		if (result > 0) {
			model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
			model.addAttribute("samplegroup", new SamplegroupView());
		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("samplegroup", template);
		}
		
		return "/samplegroup/authorizeview";
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String Export(@RequestParam String id,ListType listType, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = service.verifyPermission("X", principal.getName(), request.getRemoteAddr());

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

//		forms=service.getForms(lanType, principal.getName());

		List<ListType> exportListTypes = service.getListType(lanType, id);

		if (exportListTypes.size() > 0) {
			model.addAttribute("exportListType", exportListTypes.get(0));
			session.setAttribute("pathString",
					forms.get(0).getFuncDesc() + " > Export > " + exportListTypes.get(0).getListTypeDesc());
		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("exportListType", new ListType());
		}

		return "/samplegroup/export";
	}

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public String Export(ListType listType, Model model, Principal principal, HttpServletRequest request,
			HttpServletResponse response) {
		
		model.addAttribute("exportListType", listType);
		listType.setUserID(principal.getName());
		listType.setRemoteAddr(request.getRemoteAddr()); 
		
		try {
			String permission = service.verifyPermission("X", principal.getName(), request.getRemoteAddr());

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("exportListType", new ListType());
				return "/samplegroup/export";
			}
			String criteria = "";
			listType.setCriteria(criteria);
			
			List<Map<Object, Object>> listData = service.getListView(listType);

			// Workbook workbook = workbookBuilderRepository.createWorkbook(listData);
			// Sheet sheet = workbook.createSheet(exportListType.ListViewDesc.replace(" ",
			// "_"));
			Workbook workbook = new HSSFWorkbook();
			// for (int i=0;i<5;i++) {
			String sheetName = listType.getListViewDesc().replace(" ", "_");
			Sheet sheet = workbook.createSheet(sheetName);
			ExcelSheetHelper.SheetData(listData, sheet);
			// }

			String fileName = sheetName + ".xls";
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			workbook.write(response.getOutputStream());
			return null;
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}

		model.addAttribute("exportListType", listType);

		return "/samplegroup/export";
	}

	@RequestMapping(value = "/import", method = RequestMethod.GET)
	public String Import(@RequestParam String id,ListType listType, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {
		String permission = service.verifyPermission("I", principal.getName(), request.getRemoteAddr());

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

//		forms=service.getForms(lanType, userId);

		exportListTypes = service.getListType(lanType, id);

		if (exportListTypes.size() > 0) {
			model.addAttribute("exportListType", exportListTypes.get(0));
		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger delay5000");
			model.addAttribute("exportListType", new ListType());
		}

		session.setAttribute("pathString",
				forms.get(0).getFuncDesc() + " > Import > " + exportListTypes.get(0).getListViewDesc());
		model.addAttribute("id", id);
		return "/samplegorup/import";
	}

//    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public String Import(@RequestParam String id, ListType listType, @RequestParam("file") MultipartFile file,
			Model model, HttpServletRequest request, Principal principal) throws Exception {

		model.addAttribute("id", id);
		listType.setUserID(principal.getName());
		listType.setRemoteAddr(request.getRemoteAddr());

		String permission = service.verifyPermission("I", principal.getName(), request.getRemoteAddr());

		if (permission != "") {
			model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
			model.addAttribute("exportListType", new ListType());
			return "/samplegroup/import";
		}

//		if (exportListTypes.size() > 0) {
//			model.addAttribute("exportListType", exportListTypes.get(0));
//		} else {
//			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
//			model.addAttribute("exportListType", new ListType());
//		}

		try {
			String criteria = "";

			Long upload = service.upload(exportListTypes.get(0));

			if (upload > 0) {
				excelReader.read(file, exportListTypes.get(0).getTableName());
				Long i = service.importData(exportListTypes.get(0));
				if (i > 0) {
					model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
					return "redirect:/samplegroup";
				}
			}
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}

		return "/samplegorup/import";
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String Report(@RequestParam String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = service.verifyPermission("R", principal.getName(), request.getRemoteAddr());

		if (permission != "") {
			model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
			model.addAttribute("reportType", new ReportView());
			return "/samplegroup/report";
		}

		forms = service.getForms(lanType, userId);

		List<ReportView> reportType = service.getReportType(id);

		if (reportType.size() > 0) {
			model.addAttribute("reportType", reportType.get(0));
			session.setAttribute("pathString",
					forms.get(0).getFuncDesc() + " > Report > " + reportType.get(0).getReportName());
		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("reportType", new ReportView());
		}

		return "/samplegroup/report";
	}
}
