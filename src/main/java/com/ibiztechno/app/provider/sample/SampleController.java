package com.ibiztechno.app.provider.sample;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.sql.Connection;
import java.util.Base64;
import java.util.HashMap;
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
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ibiztechno.app.helper.ExcelReader;
import com.ibiztechno.app.helper.ExcelSheetHelper;
import com.ibiztechno.app.helper.ReportHelper;
import com.ibiztechno.app.model.CodeName;
import com.ibiztechno.app.model.CodeValue;
import com.ibiztechno.app.model.Dashboard;
import com.ibiztechno.app.model.ListType;
import com.ibiztechno.app.model.FormType;
import com.ibiztechno.app.model.Pagging;
import com.ibiztechno.app.model.ReportView;
import com.ibiztechno.app.repository.sql.SqlRepository;

@Controller
@RequestMapping("/sample")
public class SampleController {

	@Autowired
	SqlRepository sqlRepository;
	@Autowired
    ExcelReader excelReader = new ExcelReader();
	@Autowired
	ReportHelper reportHelper=new ReportHelper();
	List<ListType> exportListTypes;
	List<FormType> forms;
	List<Pagging> page;
	List<CodeName> reportformats;
	List<CodeName> modules;
//	action
	List<CodeName> actions;
	List<CodeName> links;
	List<CodeName> enquiries;
	List<CodeName> imports;
	List<CodeName> exports;
	List<CodeName> reports;
	List<CodeName> maintenances;
	List<CodeName> trees;
//	search
	List<CodeName> filters;
	List<CodeName> groupBys;
	List<CodeName> favorites;
	List<CodeName> pages;
//	Data
	List<Map<String, Object>> listData;
	List<Dashboard> dashboardList;
	List<CodeValue> dashboardData;
	List<Map<String, Object>> kanbanData;
	List<Map<String, Object>> listTotal;

//	template parameter to create
	List<CodeName> typegroups;
	List<CodeName> temptypes;
	// history
	List<Map<String, Object>> histories;
	List<Map<String, Object>> listFile;
	String lanType = "001";

	@SuppressWarnings("unchecked")
	@RequestMapping()
	public String index(@RequestParam(required = false) String search,
			@RequestParam(required = false, defaultValue = "") String screenType,
			@RequestParam(required = false, defaultValue = "1") Integer pageNumber, Model model, Principal principal,
			HttpSession session, HttpServletRequest request) throws Exception {

		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		LoadForm(model, principal, request, "L");

		String userId = principal.getName();

		modules = sqlRepository.GetParam("Template_GetUserFormList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		actions = sqlRepository.GetParam("Template_GetActionList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		links = sqlRepository.GetParam("Template_GetLinkList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		enquiries = sqlRepository.GetParam("Template_GetEnquiryList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		imports = sqlRepository.GetParam("Template_GetImportList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		exports = sqlRepository.GetParam("Template_GetExportList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		reports = sqlRepository.GetParam("Template_GetReportList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		maintenances = sqlRepository.GetParam("Template_GetMainList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));

		filters = sqlRepository.GetParam("Template_GetCriteriaList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		groupBys = sqlRepository.GetParam("Template_GetTreeList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		favorites = sqlRepository.GetParam("Template_GetUserCriteriaList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));

		String criteria = "";
		if (search != null) {
			if (!search.equals("")) {
				criteria = " AND Search like '%" + search + "%'";
			}
			// criteria=" AND " + SearchData.get("TempName") +" like '%"+SearchData.get+""
			// for
			
			//sdf/
			///
		}

		page = (List<Pagging>) (Object) sqlRepository.GetList("Template_ListPage",
				new MapSqlParameterSource().addValue("userid", principal.getName()).addValue("lantype", lanType)
						.addValue("listcode", forms.get(0).getWebListCode()).addValue("criteria", criteria)
						.addValue("pagenumber", pageNumber),
				Pagging.class);

		pages = sqlRepository.GetParam("Template_GetPageList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", "")
						.addValue("listcode", forms.get(0).getWebListCode()).addValue("criteria", criteria)
						.addValue("pagenumber", 1));

		session.setAttribute("page", page);
		session.setAttribute("pages", pages);

		trees = sqlRepository.GetParam("Template_Treeview", new MapSqlParameterSource().addValue("UserId", userId)
				.addValue("lantype", lanType).addValue("TreeCode", "001").addValue("criteria", criteria));

		if (forms.size() > 0) {
			if (screenType.equalsIgnoreCase("")) {
				screenType = forms.get(0).getWebListType();
			} else {
				Long result = sqlRepository.GetLong("Template_SetListType",
						new MapSqlParameterSource().addValue("ListType", screenType)
								.addValue("UserID", principal.getName()).addValue("RemoteAddr", request.getRemoteAddr())
								.addValue("retval", 0));
			}
		}

		if (screenType.equalsIgnoreCase("D")) {
			dashboardList = (List<Dashboard>) (Object) sqlRepository.GetList( "Template_GetListName",
							new MapSqlParameterSource().addValue("lantype", lanType)
									.addValue("userid", principal.getName()).addValue("ListType", "D"),
							Dashboard.class);

			for (int i = 0; i < dashboardList.size(); i++) {
				dashboardData = (List<CodeValue>) (Object) sqlRepository.GetList( "Template_Dashboard",
						new MapSqlParameterSource().addValue("listcode", dashboardList.get(i).getCode())
								.addValue("lantype", lanType).addValue("criteria", criteria)
								.addValue("userid", principal.getName()),
						CodeValue.class);

				dashboardList.get(i).setDashboardData(dashboardData);
			}

			model.addAttribute("dashboardList", dashboardList);
		} else if (screenType.equalsIgnoreCase("K")) {
			kanbanData = sqlRepository.GetList("Template_KanbanView",
					new MapSqlParameterSource().addValue("listcode", "K01").addValue("lantype", lanType)
							.addValue("criteria", criteria).addValue("userid", userId).addValue("orderby", "")
							.addValue("pagenumber", pageNumber));
		} else {
			listData = sqlRepository.GetList("Template_GetList",
					new MapSqlParameterSource().addValue("lantype", lanType).addValue("criteria", criteria)
							.addValue("userid", userId).addValue("orderby", "").addValue("pagenumber", pageNumber));

			listTotal = sqlRepository.GetList("Template_GetTotal",
					new MapSqlParameterSource().addValue("lantype", lanType).addValue("criteria", criteria)
							.addValue("userid", userId).addValue("orderby", "").addValue("pagenumber", pageNumber));
		}

		session.setAttribute("modules", modules);
		session.setAttribute("actions", actions);
		session.setAttribute("links", links);
		session.setAttribute("enquiries", enquiries);
		session.setAttribute("imports", imports);
		session.setAttribute("exports", exports);
		session.setAttribute("reports", reports);
		session.setAttribute("maintenances", maintenances);
		session.setAttribute("trees", trees);

		session.setAttribute("filters", filters);
		session.setAttribute("groupBys", groupBys);
		session.setAttribute("favorites", favorites);
		session.setAttribute("search", "/" + forms.get(0).getWebModule());

		session.setAttribute("btnmodule", "/" + forms.get(0).getWebModule());
		session.setAttribute("modulename", forms.get(0).getFuncDesc());

		session.setAttribute("btncreate", forms.get(0).getWebModule() + "/create");
		session.setAttribute("pathString", forms.get(0).getFuncDesc() + " > " + forms.get(0).getWebListDesc());
		session.setAttribute("display", forms.size() > 0 ? forms.get(0).getWebListType() : "D");

		model.addAttribute("screenType", screenType);
		model.addAttribute("listData", listData);
		model.addAttribute("listTotal", listTotal);
		model.addAttribute("kanbanData", kanbanData);
		model.addAttribute("search", search);

		return "/sample/index";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String View(@RequestParam String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		LoadForm(model, principal, request, "V");
		LoadDropdown(model, principal);

		List<SampleView> contexts = (List<SampleView>) (Object) sqlRepository.GetList( "Template_GetByID",
				new MapSqlParameterSource().addValue("id", id), SampleView.class);

		if (contexts.size() > 0) {
			model.addAttribute("sample", contexts.get(0));
			session.setAttribute("pathString", forms.get(0).getFuncDesc() + " > View " + contexts.get(0).getTempName());

			LoadTab(contexts, model, principal);

		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("sample", new SampleView());
		}

		LoadModel(model, principal);
		model.addAttribute("id", id);

		return "/sample/view";
	}

//    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/create", method = RequestMethod.GET) // model = viewBag
	public String Create(Sample context, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {
		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		LoadForm(model, principal, request, "N");
		LoadDropdown(model, principal);
		LoadModel(model, principal);
		model.addAttribute("sample", context);

		session.setAttribute("pathString", forms.get(0).getFuncDesc() + " > Create");

		return "/sample/create";
	}

//    @SuppressWarnings("unused")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String Create(@Valid Sample context, Errors errors, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) {

		LoadModel(model, principal);
		model.addAttribute("sample", context);

		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldErrors().get(0).getDefaultMessage()).addAttribute("class",
					"alert alert-danger");
			return "/sample/create";
		}

		try {
			context.setUserID(principal.getName());
			context.setRemoteAddr(request.getRemoteAddr());

			String permission = sqlRepository.GetString("Template_VerifyPermission",
					new MapSqlParameterSource().addValue("TypeID", "N").addValue("UserID", principal.getName())
							.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("sample", new Sample());
				return "/sample/create";
			}

			Long result = sqlRepository.GetLong("Template_Create", new MapSqlParameterSource()
					.addValue("TempCode", context.getTempCode()).addValue("TempShort", context.getTempShort())
					.addValue("TempName", context.getTempName()).addValue("TempNameU", context.getTempNameU())
					.addValue("TempOrder", context.getTempOrder()).addValue("TempPass", context.getTempPass())
					.addValue("TempFax", context.getTempFax()).addValue("TempEmail", context.getTempEmail())
					.addValue("TempWebsite", context.getTempWebsite()).addValue("TempPhone", context.getTempPhone())
					.addValue("TempDate", context.getTempDate())
					// .addValue("TypeGroup", context.getTypeGroup())
					.addValue("TempType", context.getTempType()).addValue("TempSSN", context.getTempSSN())
					.addValue("TempNumeric", context.getTempNumeric()).addValue("TempInt", context.getTempInt())
					.addValue("TempSmallint", context.getTempSmallint()).addValue("TempMoney", context.getTempMoney())
					.addValue("TempRemark", context.getTempRemark())
					.addValue("TempStatus", context.isTempStatus() ? 1 : 0).addValue("UserID", context.getUserID())
					.addValue("RemoteAddr", context.getRemoteAddr()).addValue("retval", 0)

			);
			if (result > 0) {
				model.addAttribute("sample", new Sample());
				model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
				return "redirect:/sample";

			}
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}

		return "/sample/create";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String Update(@RequestParam String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		LoadForm(model, principal, request, "E");
		LoadDropdown(model, principal);
		LoadModel(model, principal);

		List<SampleView> contexts = (List<SampleView>) (Object) sqlRepository.GetList( "Template_GetByID",
				new MapSqlParameterSource().addValue("id", id), SampleView.class);

		if (contexts.size() > 0) {
			model.addAttribute("sample", contexts.get(0));
			session.setAttribute("pathString",
					forms.get(0).getFuncDesc() + " > Update > " + contexts.get(0).getTempName());

			temptypes = sqlRepository.GetParam("Template_GetTypeList", new MapSqlParameterSource()
					.addValue("Active", "1").addValue("TypeGroup", contexts.get(0).getTypeGroup()));

			LoadTab(contexts, model, principal);

		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("sample", new SampleView());
		}

		return "/sample/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String Update(@RequestParam String id, @Valid SampleView context, Errors errors, Model model,
			Principal principal, HttpServletRequest request) {

		LoadModel(model, principal);
		model.addAttribute("sample", context);
		model.addAttribute("id", id);

		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldErrors().get(0).getDefaultMessage()).addAttribute("class",
					"alert alert-danger");
			return "/sample/update";
		}

		try {
			String permission = sqlRepository.GetString("Template_VerifyPermission",
					new MapSqlParameterSource().addValue("TypeID", "E").addValue("UserID", principal.getName())
							.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("sample", new SampleView());
				return "/sample/update";
			}

			Long result = sqlRepository.GetLong("Template_Update", new MapSqlParameterSource()
					.addValue("TempCode", context.getTempCode()).addValue("TempShort", context.getTempShort())
					.addValue("TempName", context.getTempName()).addValue("TempNameU", context.getTempNameU())
					.addValue("TempOrder", context.getTempOrder()).addValue("TempRemark", context.getTempRemark())
					.addValue("TempPass", context.getTempPass()).addValue("TempDate", context.getTempDate())
					.addValue("TempStatus", context.isTempStatus() ? 1 : 0)
					.addValue("TempNumeric", context.getTempNumeric()).addValue("TempInt", context.getTempInt())
					.addValue("TempSmallint", context.getTempSmallint()).addValue("TempMoney", context.getTempMoney())
					.addValue("TempWebsite", context.getTempWebsite()).addValue("TempType", context.getTempType())
					// .addValue("TypeGroup", context.getTypeGroup())
					.addValue("TempPhone", context.getTempPhone()).addValue("TempSSN", context.getTempSSN())
					.addValue("TempFax", context.getTempFax()).addValue("TempEmail", context.getTempEmail())
					.addValue("UserID", principal.getName()).addValue("RemoteAddr", request.getRemoteAddr())
					.addValue("retval", id));
			if (result > 0) {
				model.addAttribute("sample", new SampleView());
				model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
				return "redirect:/sample";
			}
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}

		return "/sample/update";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String Delete(@RequestParam String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		LoadForm(model, principal, request, "D");
		LoadDropdown(model, principal);
		LoadModel(model, principal);

		List<SampleView> contexts = (List<SampleView>) (Object) sqlRepository.GetList( "Template_GetByID",
				new MapSqlParameterSource().addValue("id", id), SampleView.class);

		if (contexts.size() > 0) {
			model.addAttribute("sample", contexts.get(0));
			session.setAttribute("pathString",
					forms.get(0).getFuncDesc() + " > Delete " + contexts.get(0).getTempName());

			LoadTab(contexts, model, principal);

		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("sample", new SampleView());
		}

		model.addAttribute("id", id);

		return "/sample/delete";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String Delete(@RequestParam String id, SampleView context, Errors errors, Model model, Principal principal,
			HttpServletRequest request) {

		LoadModel(model, principal);
		model.addAttribute("sample", context);
		model.addAttribute("id", id);

		if (context.getTempCode().equals("")) {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			return "/sample/delete";
		}

		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldErrors().get(0).getDefaultMessage()).addAttribute("class",
					"alert alert-danger");
			return "/sample/delete";
		}

		try {
			String permission = sqlRepository.GetString("Template_VerifyPermission",
					new MapSqlParameterSource().addValue("TypeID", "D").addValue("UserID", principal.getName())
							.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("sample", new SampleView());
				return "/sample/delete";
			}

			Long result = sqlRepository.GetLong("Template_Delete",
					new MapSqlParameterSource().addValue("TempCode", context.getTempCode())
							.addValue("UserID", principal.getName()).addValue("RemoteAddr", request.getRemoteAddr())
							.addValue("retval", id)

			);
			if (result > 0) {
				// context = new SampleView();
				model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
				return "redirect:/sample";
			}
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
			model.addAttribute("sample", new SampleView());
		}

		return "/sample/delete";
	}

//    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public String Authorize(@RequestParam(required = false) String search, Model model, Principal principal,
			HttpSession session, HttpServletRequest request) throws Exception {

		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "A").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		LoadForm(model, principal, request, "A");

		String criteria = "";
		if (search != null) {
			if (!search.equals("")) {
				criteria = " AND Search like '%" + search + "%'";
			}
		}

		List<Map<String, Object>> listData = sqlRepository.GetList("Template_GetAuthorizeList",
				new MapSqlParameterSource().addValue("lantype", lanType).addValue("criteria", criteria)
						.addValue("userid", principal.getName()).addValue("orderby", "").addValue("pagenumber", 1));

		session.setAttribute("btnmodule", "/" + forms.get(0).getWebModule());
		session.setAttribute("modulename", forms.get(0).getFuncDesc());
		session.setAttribute("btncreate", "/" + forms.get(0).getWebModule() + "/authorize");
		// session.setAttribute("btncreate", null); --To disable Authorize button in
		// Authorize List screen
		session.setAttribute("pathString", forms.get(0).getFuncDesc() + " > Authorize");
		session.setAttribute("display", "L");

		session.setAttribute("filters", filters);
		session.setAttribute("groupBys", groupBys);
		session.setAttribute("favorites", favorites);
		session.setAttribute("search", "/" + forms.get(0).getWebModule() + "/authorize");

		model.addAttribute("listData", listData);
		model.addAttribute("search", search);

		return "/authorize";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/authorize/{id}", method = RequestMethod.GET)
	public String AuthorizeView(@PathVariable String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "A").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		LoadForm(model, principal, request, "A");
		LoadDropdown(model, principal);

		List<SampleView> contexts = (List<SampleView>) (Object) sqlRepository.GetList( "Template_GetByID",
				new MapSqlParameterSource().addValue("id", id), SampleView.class);

		if (contexts.size() > 0) {
			model.addAttribute("sample", contexts.get(0));
			session.setAttribute("pathString",
					forms.get(0).getFuncDesc() + " > Authorize " + contexts.get(0).getTempName());

			LoadTab(contexts, model, principal);

		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("sample", new SampleView());
		}

		LoadModel(model, principal);
		model.addAttribute("id", id);

		return "/sample/authorizeview";
	}

//    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/authorize/{id}", method = RequestMethod.POST)
	public String AuthorizePost(@PathVariable String id, SampleView context, Errors errors, Model model,
			Principal principal, HttpServletRequest request) throws Exception {

		LoadModel(model, principal);
		model.addAttribute("id", id);
		model.addAttribute("sample", context);

		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldErrors().get(0).getDefaultMessage()).addAttribute("class",
					"alert alert-danger");
			return "/sample/authorizeview";
		}

		try {

			String permission = sqlRepository.GetString("Template_VerifyPermission",
					new MapSqlParameterSource().addValue("TypeID", "A").addValue("UserID", principal.getName())
							.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("sample", new SampleView());
				return "/sample/authorizeview";
			}

			Long result = sqlRepository.GetLong("Template_Authorize",
					new MapSqlParameterSource().addValue("TempCode", context.getTempCode())
							.addValue("UserID", principal.getName()).addValue("RemoteAddr", request.getRemoteAddr())
							.addValue("retval", id));

			if (result > 0) {
				model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
				model.addAttribute("sample", new SampleView());
				return "redirect:/sample/authorize";
			} else {
				model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
				model.addAttribute("sample", new SampleView());
			}

		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}

		return "/sample/authorizeview";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public String Export(@RequestParam String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		LoadForm(model, principal, request, "X");

		List<ListType> exportListTypes = (List<ListType>) (Object) sqlRepository.GetList(
				"Template_GetListType",
				new MapSqlParameterSource().addValue("ListCode", id).addValue("lantype", lanType),
				ListType.class);

		if (exportListTypes.size() > 0) {
			model.addAttribute("exportListType", exportListTypes.get(0));
			session.setAttribute("pathString",
					forms.get(0).getFuncDesc() + " > Export " + exportListTypes.get(0).getListViewDesc());
		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("exportListType", new ListType());
		}

		String criteria = "";
		List<Map<String, Object>> listData = sqlRepository.GetList("Template_ListView",
				new MapSqlParameterSource().addValue("listcode", id).addValue("lantype", lanType)
						.addValue("criteria", criteria).addValue("userid", principal.getName()).addValue("orderby", "")
						.addValue("pagenumber", 1));

		model.addAttribute("listData", listData);

		return "/sample/export";
	}

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public String Export(ListType exportListType, Model model, Principal principal, HttpServletRequest request,
			HttpServletResponse response, Errors errors) {

		model.addAttribute("exportListType", exportListType);

		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldErrors().get(0).getDefaultMessage()).addAttribute("class",
					"alert alert-danger");
			return "/sample/export";
		}

		try {
			String permission = sqlRepository.GetString("Template_VerifyPermission",
					new MapSqlParameterSource().addValue("TypeID", "X").addValue("UserID", principal.getName())
							.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("exportListType", new ListType());
				return "/sample/export";
			}

			List<Map<Object, Object>> listData = sqlRepository.GetListObj("Template_ListView",
					new MapSqlParameterSource().addValue("listcode", exportListType.getListViewCode())
							.addValue("lantype", lanType).addValue("criteria", "")
							.addValue("userid", principal.getName()).addValue("orderby", "").addValue("pagenumber", 1));

			Workbook workbook = new HSSFWorkbook();

			String sheetName = exportListType.getListViewDesc().replace(" ", "_");
			Sheet sheet = workbook.createSheet(sheetName);
			ExcelSheetHelper.SheetData(listData, sheet);

			String fileName = sheetName + ".xls";
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			workbook.write(response.getOutputStream());

//		    return null;
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}

		return "/sample/export";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/import", method = RequestMethod.GET)
	public String Import(@RequestParam String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}
		LoadForm(model, principal, request, "I");

		exportListTypes = (List<ListType>) (Object) sqlRepository.GetList( "Template_GetListType",
				new MapSqlParameterSource().addValue("ListCode", id).addValue("lantype", lanType),
				ListType.class);

		if (exportListTypes.size() > 0) {
			model.addAttribute("exportListType", exportListTypes.get(0));
			session.setAttribute("pathString",
					forms.get(0).getFuncDesc() + " > Import > " + exportListTypes.get(0).getListViewDesc());

		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger delay5000");
			model.addAttribute("exportListType", new ListType());
		}

		String criteria = "";
		List<Map<String, Object>> listData = sqlRepository.GetList("Template_ListView",
				new MapSqlParameterSource().addValue("listcode", id).addValue("lantype", lanType)
						.addValue("criteria", criteria).addValue("userid", principal.getName()).addValue("orderby", "")
						.addValue("pagenumber", 1));

		model.addAttribute("listData", listData);

		model.addAttribute("id", id);
		return "/sample/import";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public String Import(@RequestParam String id, @RequestParam("file") MultipartFile file, Model model,
			Principal principal, HttpServletRequest request) {

		model.addAttribute("id", id);

		if (exportListTypes.size() > 0) {
			model.addAttribute("exportListType", exportListTypes.get(0));
		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("exportListType", new ListType());
		}

		try {
			String permission = sqlRepository.GetString("Template_VerifyPermission",
					new MapSqlParameterSource().addValue("TypeID", "I").addValue("UserID", principal.getName())
							.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("exportListType", new ListType());
				return "/sample/import";
			}

			String criteria = "";

			Long upload = sqlRepository.GetLong("Template_Upload",
					new MapSqlParameterSource().addValue("ListCode", id).addValue("lantype", lanType)
							.addValue("UserId", principal.getName()).addValue("RemoteAddr", request.getRemoteAddr())
							.addValue("retval", 0));

			if (upload > 0) {
				excelReader.read(file, exportListTypes.get(0).getTableName());
				Long i = sqlRepository.GetLong("Template_Import",
						new MapSqlParameterSource().addValue("listcode", id).addValue("lantype", lanType)
								.addValue("criteria", criteria).addValue("UserId", principal.getName())
								.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", 0));

				if (i > 0) {
					model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
					return "redirect:/sample";
				}
			}
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
			model.addAttribute("exportListType", new ListType());
		}

		return "/sample/import";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public String Report(@RequestParam String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {
		model.addAttribute("id", id);
		try {
			String permission = sqlRepository.GetString("Template_VerifyPermission",
					new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
							.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("reportType", new ReportView());
				return "/sample/report";
			}

			LoadForm(model, principal, request, "R");
			LoadDropdown(model, principal);

			List<ReportView> reportType = (List<ReportView>) (Object) sqlRepository.GetList(
					"Template_GetReportType", new MapSqlParameterSource().addValue("ReportCode", id), ReportView.class);

			if (reportType.size() > 0) {
				model.addAttribute("reportType", reportType.get(0));
				session.setAttribute("pathString",
						forms.get(0).getFuncDesc() + " > Report " + reportType.get(0).getReportName());
			} else {
				model.addAttribute("message", "Not Found Report").addAttribute("class", "alert alert-danger");
				model.addAttribute("reportType", new ReportView());
			}
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}

		LoadModel(model, principal);

		return "/sample/report";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/report", method = RequestMethod.POST)
	public String Report(ReportView reportType, Model model, Principal principal, HttpServletRequest request,
			HttpServletResponse response, Errors errors) throws Exception {

		model.addAttribute("reportType", reportType);

		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldErrors().get(0).getDefaultMessage()).addAttribute("class",
					"alert alert-danger");
			return "/sample/report";
		}
		try {
			String permission = sqlRepository.GetString("Template_VerifyPermission",
					new MapSqlParameterSource().addValue("TypeID", "R").addValue("UserID", principal.getName())
							.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("reportType", new ReportView());
				return "/sample/report";
			}
			String id = reportType.getReportCode();

			List<ReportView> reportTypes = (List<ReportView>) (Object) sqlRepository.GetList(
					"Template_GetReportType", new MapSqlParameterSource().addValue("ReportCode", id), ReportView.class);

			model.addAttribute("id", id);
			if (reportTypes.size() > 0) {
				model.addAttribute("reportType", reportTypes.get(0));
			} else {
				model.addAttribute("message", "Not Found Report").addAttribute("class", "alert alert-danger");
				model.addAttribute("reportType", new ReportView());
			}

			Map<String, Object> parameters = new HashMap<>();

			reportHelper.dowload(reportTypes.get(0).getFileName(), reportTypes.get(0).getFileName(), 
		    		reportTypes.get(0).getFomatTypeExt(), parameters, response);

			return null;
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
			model.addAttribute("reportType", new ReportView());
		}
		return "/sample/report";
	}

//    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String Search(Sample context, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		LoadForm(model, principal, request, "S");

		session.setAttribute("pathString", forms.get(0).getFuncDesc() + " > Search");

		return "/sample/search";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String Search(Sample context, Errors errors, Model model, Principal principal, HttpServletRequest request,
			HttpServletResponse response) {

		LoadModel(model, principal);
		model.addAttribute("sample", context);

		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldErrors().get(0).getDefaultMessage()).addAttribute("class",
					"alert alert-danger");
			return "/sample/search";
		}
		try {
			String permission = sqlRepository.GetString("Template_VerifyPermission",
					new MapSqlParameterSource().addValue("TypeID", "Q").addValue("UserID", principal.getName())
							.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("sample", new Sample());
				return "/sample/search";
			}

			String criteria = sqlRepository.GetString("Template_GetCriteria",
					new MapSqlParameterSource().addValue("retval", "").addValue("TempCode", context.getTempCode())
							.addValue("TempName", context.getTempName()).addValue("TypeGroup", context.getTypeGroup())
							.addValue("TempType", context.getTempType()));

			List<Map<String, Object>> listData = sqlRepository.GetList("Template_GetSearchList",
					new MapSqlParameterSource().addValue("lantype", lanType).addValue("criteria", criteria)
							.addValue("userid", principal.getName()).addValue("orderby", "").addValue("pagenumber", 1));

			model.addAttribute("listData", listData);

		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}

		return "/sample/search";
	}

	@RequestMapping(value = "/enquiry", method = RequestMethod.GET)
	public String Eqnuiry(Sample context, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		LoadForm(model, principal, request, "C");

		session.setAttribute("pathString", forms.get(0).getFuncDesc() + " > Enquiry");

		return "/sample/enquiry";
	}

	@RequestMapping(value = "/enquiry", method = RequestMethod.POST)
	public String Enquiry(Sample context, Errors errors, Model model, Principal principal, HttpServletRequest request,
			HttpServletResponse response) {

		LoadModel(model, principal);
		model.addAttribute("sample", context);

		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldErrors().get(0).getDefaultMessage()).addAttribute("class",
					"alert alert-danger");
			return "/sample/enquiry";
		}
		try {
			String permission = sqlRepository.GetString("Template_VerifyPermission",
					new MapSqlParameterSource().addValue("TypeID", "Q").addValue("UserID", principal.getName())
							.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("sample", new Sample());
				return "/sample/enquiry";
			}

			String criteria = sqlRepository.GetString("Template_GetCriteria",
					new MapSqlParameterSource().addValue("retval", "").addValue("TempCode", context.getTempCode())
							.addValue("TempName", context.getTempName()).addValue("TypeGroup", context.getTypeGroup())
							.addValue("TempType", context.getTempType()));

			return "/sample?search=" + criteria;
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}

		return "/sample";
	}

	@SuppressWarnings("unchecked")
	public void LoadForm(Model model, Principal principal, HttpServletRequest request, String formType)
			throws Exception {
		Long result = sqlRepository.GetLong("Template_LogForm",
				new MapSqlParameterSource().addValue("FormType", formType).addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", 0));

		if (result > 0) {
			forms = (List<FormType>) (Object) sqlRepository.GetList( "Template_GetForm",
					new MapSqlParameterSource().addValue("userid", principal.getName()).addValue("lantype", lanType),
					FormType.class);
			model.addAttribute("forms", forms);

			lanType = forms.get(0).getLanType();

			List<CodeName> captions = sqlRepository.GetParam("Template_GetCaption",
					new MapSqlParameterSource().addValue("LanType", forms.get(0).getLanType()));
			model.addAttribute("captions", captions);
		}
	}

//    @SuppressWarnings("unchecked")
	public void LoadDropdown(Model model, Principal principal) throws Exception {

		typegroups = sqlRepository.GetParam("Template_GetGroupList",
				new MapSqlParameterSource().addValue("Active", ""));

		reportformats = sqlRepository.GetParam("Template_GetReportFormat", new MapSqlParameterSource());

	}

//    @SuppressWarnings("unchecked")
	public void LoadModel(Model model, Principal principal) {

		model.addAttribute("typegroups", typegroups);
		model.addAttribute("temptypes", temptypes);
		model.addAttribute("reportformats", reportformats);
	}

	public void LoadTab(List<SampleView> context, Model model, Principal principal) throws Exception {

		histories = sqlRepository.GetList("Template_GetHistoryList",
				new MapSqlParameterSource().addValue("lantype", lanType)
						.addValue("tempcode", context.get(0).getTempCode()).addValue("userid", principal.getName())
						.addValue("orderby", "").addValue("pagenumber", 1));
		model.addAttribute("histories", histories);

		listFile = GetFileList(context.get(0).getTempCode(), model, principal);

	}

	@RequestMapping(value = "/sampletype/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<CodeName> GetTemplateType(@PathVariable String id) throws Exception {
		temptypes = sqlRepository.GetParam("Template_GetTypeList",
				new MapSqlParameterSource().addValue("Active", "1").addValue("TypeGroup", id));
		return this.temptypes;
	}

	@RequestMapping(value = "/jsearch/{src}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Map<String, Object>> GetSearchList(@PathVariable String src, Principal principal) throws Exception {
		String criteria = "";
		if (src != null) {
			if (!src.equals("")) {
				criteria = " AND Search like '%" + src + "%'";
			}
		}
		return sqlRepository.GetList("Template_GetSearchList",
				new MapSqlParameterSource().addValue("LanType", "001").addValue("criteria", criteria)
						.addValue("userid", principal.getName()).addValue("orderby", "").addValue("pagenumber", 1));
	}

	@RequestMapping(value = "/jhistory/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Map<String, Object>> GetHistoryList(@PathVariable String id, Model model, Principal principal)
			throws Exception {
		this.histories = sqlRepository.GetList("Template_GetHistoryList",
				new MapSqlParameterSource().addValue("LanType", "001").addValue("tempcode", id)
						.addValue("userid", principal.getName()).addValue("orderby", "").addValue("pagenumber", 1));
		model.addAttribute("histories", this.histories);

		return this.histories;
	}

	@RequestMapping(value = "/jfile/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Map<String, Object>> GetFileList(@PathVariable String id, Model model, Principal principal)
			throws Exception {
		this.listFile = sqlRepository.GetList("Template_GetFileList",
				new MapSqlParameterSource().addValue("LanType", "001").addValue("tempcode", id)
						.addValue("userid", principal.getName()).addValue("orderby", "").addValue("pagenumber", 1));
		model.addAttribute("listFile", this.listFile);

		return this.listFile;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/jsample/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Sample GetSample(@PathVariable String id, Model model, Principal principal) throws Exception {
		List<Sample> contexts = (List<Sample>) (Object) sqlRepository.GetList( "Template_Fundamentals",
				new MapSqlParameterSource().addValue("TempCode", id), Sample.class);

		if (contexts.size() > 0) {
			model.addAttribute("sample", contexts.get(0));
			return contexts.get(0);
		} else {
			return new Sample();
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String Upload(@RequestParam String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {
		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		LoadForm(model, principal, request, "U");
//		LoadDropdown(model, principal);
//		LoadModel(model, principal);

		List<SampleView> contexts = (List<SampleView>) (Object) sqlRepository.GetList( "Template_GetByID",
				new MapSqlParameterSource().addValue("id", id), SampleView.class);

		if (contexts.size() > 0) {
			model.addAttribute("sample", contexts.get(0));
			session.setAttribute("pathString",
					forms.get(0).getFuncDesc() + " > Upload > " + contexts.get(0).getTempName());

//			temptypes = sqlRepository.GetParam("Template_GetTypeList", new MapSqlParameterSource()
//					.addValue("Active", "1").addValue("TypeGroup", contexts.get(0).getTypeGroup()));

//			LoadTab(contexts, model, principal);

			listFile = GetFileList(contexts.get(0).getTempCode(), model, principal);

		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("sample", new SampleView());
		}

		model.addAttribute("id", id);
		return "/sample/upload";
	}

//	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String Upload(@RequestParam String id, SampleView context, @RequestParam("file") MultipartFile file,
			Model model, Principal principal, HttpServletRequest request) {

		model.addAttribute("id", id);
		model.addAttribute("sample", context);

		try {
//			String id;
			// String s = Base64.getEncoder().encodeToString(file.getBytes()); //convert
			// byte file as base 64 and save to db
			byte[] b = file.getBytes();

			String result = sqlRepository.GetString("Template_UploadFile",
					new MapSqlParameterSource().addValue("TempCode", context.getTempCode())
							// .addValue("FileType", "")
							.addValue("FilePath", file.getOriginalFilename()) // "home.jpg"
							.addValue("FileExt", FilenameUtils.getExtension(file.getOriginalFilename())) // "jpg"
							.addValue("ContentType", file.getContentType()) // "application/octet-stream"
							.addValue("FileImage", b)
							// .addValue("RecStatus", 1)
							.addValue("UserID", principal.getName()).addValue("RemoteAddr", request.getRemoteAddr())
							.addValue("retval", 0));

			if (result.length() > 0) {
				model.addAttribute("message", "Success").addAttribute("class", "alert alert-success delay5000");

//				listFile = GetFileList(context.getTempCode(), model, principal);
				return "redirect:/sample/upload";
			} else {
				model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
				model.addAttribute("sample", new SampleView());
			}
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
//		} catch (IOException e) {
//			e.printStackTrace();
		}
		return "/sample/upload";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/download", method = RequestMethod.GET) // ,produces =
																		// MediaType.APPLICATION_OCTET_STREAM_VALUE
	public String Download(@RequestParam String id, Model model, Principal principal, HttpServletResponse response) // @RequestParam
																													// String
																													// id,
			throws Exception {

		try {
//			String permission = sqlRepository.GetString("Template_VerifyPermission",
//					new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
//							.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
//
//			session.setAttribute("permission", "");
//			if (permission.length() > 0) {
//				session.setAttribute("permission", permission);
//				return "redirect:/";
//			}

//			LoadForm(model, principal,request,"O");
//			LoadDropdown(model, principal);

			List<SampleView> contexts = (List<SampleView>) (Object) sqlRepository.GetList( "Template_GetFile",
					new MapSqlParameterSource().addValue("id", id), SampleView.class);

			if (contexts.size() > 0) {
				model.addAttribute("sample", contexts.get(0));
//				session.setAttribute("pathString", forms.get(0).getFuncDesc() + " > View " + contexts.get(0).getTempName());

//				LoadTab(contexts, model, principal);
				// byte[] fileByte =
				// Base64.getDecoder().decode("TG9hbiBVSSBvbiB1cGRhdGUgDQpDaGFuZ2UgZnJvbSBmaXhlZCBJUCB0byBob3N0DQpSZXN0cnVjdHVyZSBjdXJyZW50IEVJIGFuZCBpbk1lc3NhZ2UNCg0KSm1ldGVyIDEgTSB0ZXN0aW5nIFVJLiBuZXh0IHdlZWs=");
				// //get file from db
				byte[] fileByte = contexts.get(0).getFileImage();

				response.setContentType(contexts.get(0).getContentType());
				response.setHeader("Content-disposition", "inline; filename=\"" + contexts.get(0).getFilePath() + "\"");
//				response.setHeader("Content-disposition", "attachment; filename=\"" + contexts.get(0).getFilePath()+"\"");
				OutputStream out = response.getOutputStream();
				out.write(fileByte);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} else {
				model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
				model.addAttribute("sample", new SampleView());
				return null;
			}

			LoadModel(model, principal);
			model.addAttribute("id", id);

			return null;

		} catch (Exception ex) {
			return "/sample/download";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detach", method = RequestMethod.GET)
	public String Detach(@RequestParam String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		LoadForm(model, principal, request, "D");
		LoadDropdown(model, principal);
		LoadModel(model, principal);

		List<SampleView> contexts = (List<SampleView>) (Object) sqlRepository.GetList( "Template_GetFile",
				new MapSqlParameterSource().addValue("id", id), SampleView.class);

		if (contexts.size() > 0) {
			model.addAttribute("sample", contexts.get(0));
			session.setAttribute("pathString",
					forms.get(0).getFuncDesc() + " > Detach > " + contexts.get(0).getTempName());

			LoadTab(contexts, model, principal);

		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("sample", new SampleView());
		}

		model.addAttribute("id", id);

		return "/sample/detach";
	}

	@RequestMapping(value = "/detach", method = RequestMethod.POST)
	public String Detach(@RequestParam String id, SampleView context, Errors errors, Model model, Principal principal,
			HttpServletRequest request) {

		LoadModel(model, principal);
		model.addAttribute("sample", context);
		model.addAttribute("id", id);

		if (context.getTFileID().equals("")) {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			return "/sample/detach";
		}

		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldErrors().get(0).getDefaultMessage()).addAttribute("class",
					"alert alert-danger");
			return "/sample/detach";
		}

		try {
			String permission = sqlRepository.GetString("Template_VerifyPermission",
					new MapSqlParameterSource().addValue("TypeID", "D").addValue("UserID", principal.getName())
							.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("sample", new SampleView());
				return "/sample/detach";
			}

			Long result = sqlRepository.GetLong("Template_Detach",
					new MapSqlParameterSource().addValue("TFileID", context.getTFileID())
							.addValue("UserID", principal.getName()).addValue("RemoteAddr", request.getRemoteAddr())
							.addValue("retval", id)

			);
			if (result > 0) {
				model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
				return "redirect:/sample/detach";
			}
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
			model.addAttribute("sample", new SampleView());
		}

		return "/sample/detach";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delfile", method = RequestMethod.GET)
	public String Delfile(@RequestParam String id, Model model, Principal principal, HttpSession session,
			HttpServletRequest request) throws Exception {

		String permission = sqlRepository.GetString("Template_VerifyPermission",
				new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

		session.setAttribute("permission", "");
		if (permission.length() > 0) {
			session.setAttribute("permission", permission);
			return "redirect:/";
		}

		LoadForm(model, principal, request, "D");
		LoadDropdown(model, principal);
		LoadModel(model, principal);

		List<SampleView> contexts = (List<SampleView>) (Object) sqlRepository.GetList( "Template_GetFile",
				new MapSqlParameterSource().addValue("id", id), SampleView.class);

		if (contexts.size() > 0) {
			model.addAttribute("sample", contexts.get(0));
			session.setAttribute("pathString",
					forms.get(0).getFuncDesc() + " > Delete File > " + contexts.get(0).getTempName());

			LoadTab(contexts, model, principal);

		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			model.addAttribute("sample", new SampleView());
		}

		model.addAttribute("id", id);

		return "/sample/delfile";
	}

	@RequestMapping(value = "/delfile", method = RequestMethod.POST)
	public String Delfile(@RequestParam String id, SampleView context, Errors errors, Model model, Principal principal,
			HttpServletRequest request) {

		LoadModel(model, principal);
		model.addAttribute("sample", context);
		model.addAttribute("id", id);

		if (context.getTFileID().equals("")) {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
			return "/sample/delfile";
		}

		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldErrors().get(0).getDefaultMessage()).addAttribute("class",
					"alert alert-danger");
			return "/sample/delfile";
		}

		try {
			String permission = sqlRepository.GetString("Template_VerifyPermission",
					new MapSqlParameterSource().addValue("TypeID", "D").addValue("UserID", principal.getName())
							.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));

			if (permission.length() > 0) {
				model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				model.addAttribute("sample", new SampleView());
				return "/sample/delfile";
			}

			Long result = sqlRepository.GetLong("Template_DeleteFile",
					new MapSqlParameterSource().addValue("TFileID", context.getTFileID())
							.addValue("UserID", principal.getName()).addValue("RemoteAddr", request.getRemoteAddr())
							.addValue("retval", id)

			);
			if (result > 0) {
				model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
				return "redirect:/sample/delfile";
			}
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
			model.addAttribute("sample", new SampleView());
		}

		return "/sample/delfile";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/showimage", method = RequestMethod.GET)
	public String ShowImage(@RequestParam String id, Model model, Principal principal
			, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<SampleImage> contexts = (List<SampleImage>) (Object) sqlRepository.GetList("Template_GetPhoto",
				new MapSqlParameterSource().addValue("id", id), SampleImage.class);
		
		if (contexts.size() > 0) {
			model.addAttribute("sample", contexts.get(0));

			response.setContentType(contexts.get(0).getContentType());				
			response.getOutputStream().write(contexts.get(0).getFileImage());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} else {
			model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
		}
		
		return null;
	}
}
