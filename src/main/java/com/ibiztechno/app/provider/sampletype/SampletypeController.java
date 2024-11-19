package com.ibiztechno.app.provider.sampletype;


import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
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
import com.ibiztechno.app.repository.sql.SqlRepository;

@Controller
@RequestMapping("/sampletype")
public class SampletypeController {

    @Autowired
    SqlRepository sqlRepository;
    @Autowired
    ExcelReader excelReader = new ExcelReader();
	@Autowired
	ReportHelper reportHelper=new ReportHelper();
    List<ListType> exportListTypes;
    List<FormType> forms;
//	action
    List<CodeName> actions;
    List<CodeName> links;
    List<CodeName> enquiries;
    List<CodeName> imports;
    List<CodeName> exports;
    List<CodeName> maintenances;
    List<CodeName> trees;
    List<CodeName> reports;
//	search
    List<CodeName> filters;
    List<CodeName> groupBys;
    List<CodeName> favorites;

//	template parameter to create
    List<CodeName> typegroups;
    List<CodeName> temptypes;
    String lanType="001";

    @SuppressWarnings("unchecked")
    @RequestMapping()
    public String index(@RequestParam(required = false) String search, Principal principal, HttpSession session, 
    		Model model, HttpServletRequest request) throws Exception {
    	String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
    			new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
    				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
    		
    	session.setAttribute("permission", "");
		if (permission.length()>0) {
		    session.setAttribute("permission",permission);
		    return "redirect:/";
		}
		String userId = principal.getName();
	
		LoadForm(model, principal);
		
		session.setAttribute("btnmodule","/"+forms.get(0).getWebModule());
		session.setAttribute("modulename", forms.get(0).getFuncDesc());
		
		session.setAttribute("btncreate", forms.get(0).getWebModule()+"/create");
		session.setAttribute("pathString", forms.get(0).getFuncDesc()+" > "+forms.get(0).getWebListDesc());
		session.setAttribute("display", forms.size() > 0 ? forms.get(0).getWebListType() : "D");
		
		actions = sqlRepository.GetParam("TemplateType_GetActionList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", ""));
		links = sqlRepository.GetParam("TemplateType_GetLinkList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", ""));
		imports = sqlRepository.GetParam("TemplateType_GetImportList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", ""));
		enquiries = sqlRepository.GetParam("TemplateType_GetEnquiryList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", ""));
		exports = sqlRepository.GetParam("TemplateType_GetExportList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", ""));
		maintenances = sqlRepository.GetParam("TemplateType_GetMainList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", ""));
		reports = sqlRepository.GetParam("TemplateType_GetReportList",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", ""));
		
		filters = sqlRepository.GetParam("TemplateType_GetCriteriaList", 
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", ""));
		groupBys = sqlRepository.GetParam("TemplateType_GetTreeList", 
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", ""));
		favorites = sqlRepository.GetParam("TemplateType_GetUserCriteriaList", 
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", ""));
	
		String criteria="";
		if(search != null) {
		    if( !search.equals("")) {
			criteria =" AND Search like '%"+search+"%'";
		    }
	//	    criteria=" AND " + SearchData.get("TypeGroup") +" like '%"+SearchData.get+""
	//	    for
		    
		}
		trees = sqlRepository.GetParam("TemplateType_Treeview",
				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", "")
				.addValue("TreeCode", "001").addValue("criteria", criteria));
		
		List<Map<String, Object>> listData = sqlRepository.GetList("TemplateType_ListView",
			new MapSqlParameterSource().addValue("listcode", "L01").addValue("lantype", "001")
				.addValue("criteria", criteria).addValue("userid", userId).addValue("orderby", "")
				.addValue("pagenumber", 1));
	
		
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
		session.setAttribute("search", "/sampletype");
	
		model.addAttribute("listData", listData);
		model.addAttribute("search",search);
	
		return "/index";
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String View(@RequestParam String id, Model model, Principal principal, HttpSession session,HttpServletRequest request) throws Exception {

    	String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
    			new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
    				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
    	
    		session.setAttribute("permission", "");
    		if (permission.length() > 0) {
    		    session.setAttribute("permission", permission);
    		    return "redirect:/";
    		}
    		
    	LoadForm(model, principal);
    	LoadDropdown(model, principal);
    	
		List<SampletypeView> templates = (List<SampletypeView>) (Object) sqlRepository.GetList(
			"TemplateType_GetByID", new MapSqlParameterSource().addValue("id", id), SampletypeView.class);
	
		if (templates.size() > 0) {
		    model.addAttribute("sampletype", templates.get(0));
		    session.setAttribute("pathString",forms.get(0).getFuncDesc() + " > View > "+templates.get(0).getTypeDesc());
			
		} else {
		    model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
		    model.addAttribute("sampletype", new SampletypeView());
		}
	
		model.addAttribute("id", id);
		LoadModel(model, principal);
		
		return "/sampletype/view";
    }

    
    @RequestMapping(value = "/create", method = RequestMethod.GET) // model = viewBag
    public String Create(Sampletype template, Model model, Principal principal, HttpSession session, HttpServletRequest request) throws Exception {
    	String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
    			new MapSqlParameterSource().addValue("TypeID", "N").addValue("UserID", principal.getName())
    				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
    	
		session.setAttribute("permission", "");
		if (permission.length() > 0) {
		    session.setAttribute("permission", permission);
		    return "redirect:/";
		}
    		
		session.setAttribute("pathString",forms.get(0).getFuncDesc() + " > Create");
		
		model.addAttribute("sampletype", template);
		
		return "/sampletype/create";
    }

//    @SuppressWarnings("unused")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String Create(@Valid Sampletype template, Errors errors, Model model, Principal principal, HttpSession session, HttpServletRequest request) {
		
    	model.addAttribute("sampletype", template);
    	LoadModel(model, principal);
    	
		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldErrors().get(0).getDefaultMessage()).addAttribute("class", "alert alert-danger");	
		    return "/sampletype/create";
		}
	
		try {			
			String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
	    			new MapSqlParameterSource().addValue("TypeID", "N").addValue("UserID", principal.getName())
	    				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
	    	
			if (permission.length()>0) {
			    model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
			    model.addAttribute("sampletype", new SampletypeView());
			    return "/sampletype/create";
			}
	    		
		    Long result = sqlRepository.GetLong("TemplateType_Create",new MapSqlParameterSource()
		    		.addValue("TypeCode", template.getTypeCode())
				    .addValue("TypeDesc", template.getTypeDesc())
				    .addValue("TypeGroup", template.getTypeGroup())
				    .addValue("TypeStatus", template.isTypeStatus() ? 1 : 0)
				    .addValue("UserID", principal.getName())
				    .addValue("RemoteAddr", request.getRemoteAddr())
				    .addValue("retval", 0)
	
		    );
		    if (result > 0) {
		    	model.addAttribute("sampletype",  new Sampletype());
				model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
		    }
		} catch (Exception e) {
		    model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}
	
		return "/sampletype/create";
    }

    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String Update(@RequestParam String id, Model model, Principal principal, HttpSession session,HttpServletRequest request) throws Exception {

    	String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
    			new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
    				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
    	
		session.setAttribute("permission", "");
		if (permission.length() > 0) {
		    session.setAttribute("permission", permission);
		    return "redirect:/";
		}
    	
		LoadForm(model, principal);
		
		LoadDropdown(model, principal);

		List<SampletypeView> templates = (List<SampletypeView>) (Object) sqlRepository.GetList(
			"TemplateType_GetByID", new MapSqlParameterSource().addValue("id", id), SampletypeView.class);
	
		if (templates.size() > 0) {
		    model.addAttribute("sampletype", templates.get(0));
		    session.setAttribute("pathString", forms.get(0).getFuncDesc() +" > Update > "+templates.get(0).getTypeDesc());
		} else {
		    model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
		    model.addAttribute("sampletype", new SampletypeView());
		}
	
		LoadModel(model, principal);
		
		return "/sampletype/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String Update(@Valid SampletypeView template, Errors errors, Model model, Principal principal, 
    		HttpServletRequest request) {

		model.addAttribute("sampletype", template);
		LoadModel(model, principal);
		
		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldErrors().get(0).getDefaultMessage()).addAttribute("class", "alert alert-danger");	
		    return "/sampletype/update";
		}
	
		try {
			String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
	    			new MapSqlParameterSource().addValue("TypeID", "E").addValue("UserID", principal.getName())
	    				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
	    	
	    	if (permission.length()>0) {
			    model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
			    model.addAttribute("sampletype", new SampletypeView());
			    return "/sampletype/update";
			}
	    	
		    Long result = sqlRepository.GetLong("TemplateType_Update", new MapSqlParameterSource()
			    .addValue("TypeCode", template.getTypeCode())
			    .addValue("TypeDesc", template.getTypeDesc())
			    .addValue("TypeGroup", template.getTypeGroup())
			    .addValue("TypeStatus", template.isTypeStatus() ? 1 : 0)
			    .addValue("UserID", principal.getName())
			    .addValue("RemoteAddr", request.getRemoteAddr())
			    .addValue("retval", 0)
	
		    );
		    if (result > 0) {
				model.addAttribute("sampletype", new SampletypeView());
				model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
		    }
		} catch (Exception e) {
		    model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}
	
		return "/sampletype/update";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String Delete(@RequestParam String id, Model model, Principal principal, HttpSession session, 
    		HttpServletRequest request) throws Exception {

    	String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
    			new MapSqlParameterSource().addValue("TypeID", "D").addValue("UserID", principal.getName())
    				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
    	
		session.setAttribute("permission", "");
		if (permission.length() > 0) {
		    session.setAttribute("permission", permission);
		    return "redirect:/";
		}
    	
		LoadForm(model, principal);
		
		LoadDropdown(model, principal);
		
		List<SampletypeView> templates = (List<SampletypeView>) (Object) sqlRepository.GetList(
			"TemplateType_GetByID", new MapSqlParameterSource().addValue("id", id), SampletypeView.class);
	
		if (templates.size() > 0) {
		    model.addAttribute("sampletype", templates.get(0));
		    session.setAttribute("pathString", forms.get(0).getFuncDesc() +" > Delete > "+templates.get(0).getTypeDesc());
		} else {
		    model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
		    model.addAttribute("sampletype", new SampletypeView());
		}
		
		LoadModel(model, principal);
		
		return "/sampletype/delete";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String Delete(SampletypeView templateView, Model model, Principal principal, HttpServletRequest request) {

		if (templateView.getTypeCode().equals("")) {
		    model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
		    return "/sampletype/delete";
		}
	
		try {
			String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
	    			new MapSqlParameterSource().addValue("TypeID", "D").addValue("UserID", principal.getName())
	    				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
	    	
	    	if (permission.length()>0) {
			    model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
			    model.addAttribute("sampletype", new SampletypeView());
			    return "/sampletype/delete";
			}
	    	
		    Long result = sqlRepository.GetLong("TemplateType_Delete",
			    new MapSqlParameterSource().addValue("TypeCode", templateView.getTypeCode())
				    .addValue("UserID", principal.getName())
				    .addValue("RemoteAddr", request.getRemoteAddr())
				    .addValue("retval", 0)
	
		    );
		    if (result > 0) {
			templateView = new SampletypeView();
			model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
		    }
		} catch (Exception e) {
		    model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}
	
		model.addAttribute("sampletype", templateView);
	
		return "/sampletype/delete";
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public String Authorize(@RequestParam(required = false) String search, Model model, Principal principal,
	    HttpSession session, HttpServletRequest request) throws Exception {

    	String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
    			new MapSqlParameterSource().addValue("TypeID", "A").addValue("UserID", principal.getName())
    				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
    	
		session.setAttribute("permission", "");
		if (permission.length() > 0) {
		    session.setAttribute("permission", permission);
		    return "redirect:/";
		}
		
		String criteria = "";
		if (search != null) {
		    if (!search.equals("")) {
			criteria = " AND Search like '%" + search + "%'";
		    }
		}
		
		LoadForm(model, principal);
		
		List<Map<String, Object>> listData = sqlRepository.GetList("TemplateType_ListView",
			new MapSqlParameterSource().addValue("listcode", "A01").addValue("lantype", "001")
				.addValue("criteria", criteria).addValue("userid", principal.getName()).addValue("orderby", "")
				.addValue("pagenumber", 1));
	
		session.setAttribute("btncreate", "/sampletype/create");
		session.setAttribute("pathString",forms.get(0).getFuncDesc() + " > Authorize");
		session.setAttribute("display", "L");
	
		session.setAttribute("filters", filters);
		session.setAttribute("groupBys", groupBys);
		session.setAttribute("favorites", favorites);
		session.setAttribute("search", "/sampletype/authorize");
	
		model.addAttribute("listData", listData);
		model.addAttribute("search", search);
		return "/authorize";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/authorize/{id}", method = RequestMethod.GET)
    public String AuthorizeView(@PathVariable String id, Model model, Principal principal, HttpSession session, HttpServletRequest request) throws Exception {

    	String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
    			new MapSqlParameterSource().addValue("TypeID", "A").addValue("UserID", principal.getName())
    				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
    	
		session.setAttribute("permission", "");
		if (permission.length() > 0) {
		    session.setAttribute("permission", permission);
		    return "redirect:/";
		}
		
		LoadForm(model, principal);
		
		LoadDropdown(model, principal);
		
		List<SampletypeView> templates = (List<SampletypeView>) (Object) sqlRepository.GetList(
			"TemplateType_GetByID", new MapSqlParameterSource().addValue("id", id), SampletypeView.class);
	
		if (templates.size() > 0) {
		    model.addAttribute("sampletype", templates.get(0));
		    session.setAttribute("pathString", forms.get(0).getFuncDesc() +" > Authorize > "+templates.get(0).getTypeDesc());
		} else {
		    model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
		    model.addAttribute("sampletype", new SampletypeView());
		}
		
		model.addAttribute("id", id);
		LoadModel(model, principal);
		
		return "/sampletype/authorizeview";
    }

//    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/authorize/{id}", method = RequestMethod.POST)
    public String AuthorizePost(@PathVariable String id, SampletypeView templateView, Model model, Principal principal, HttpServletRequest request)
	    throws Exception {
    	
    	model.addAttribute("id", id);
    	model.addAttribute("sampletype", templateView);
    	
    	try {
    		
    	
	    	String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
	    			new MapSqlParameterSource().addValue("TypeID", "A").addValue("UserID", principal.getName())
	    				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
	    	
	    	if (permission.length()>0) {
			    model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
			    model.addAttribute("sampletype", new SampletypeView());
			    return "/sampletype/authorizeview";
			}
			
			Long result = sqlRepository.GetLong("TemplateType_Authorize",
				new MapSqlParameterSource().addValue("TypeCode", templateView.getTypeCode())
					.addValue("UserID", principal.getName())
					.addValue("RemoteAddr", request.getRemoteAddr())
					.addValue("retval", id));
		
			if (result > 0) {
			    model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
			    model.addAttribute("sampletype", new SampletypeView());
			}
    	} catch (Exception e) {
		    model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
    	}
	
		return "/sampletype/authorizeview";
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public String Export(@RequestParam String id, Model model, Principal principal,HttpSession session, HttpServletRequest request)
	    throws Exception {

    	String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
    			new MapSqlParameterSource().addValue("TypeID", "X").addValue("UserID", principal.getName())
    				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
    		
		session.setAttribute("permission", "");
		if (permission.length()>0) {
		    session.setAttribute("permission",permission);
		    return "redirect:/";
		}
		
		LoadForm(model, principal);
		
		List<ListType> exportListTypes = (List<ListType>) (Object) sqlRepository.GetList(
			"TemplateType_GetListType",
			new MapSqlParameterSource().addValue("ListCode", id).addValue("LanType", "001"), ListType.class);
	
		if (exportListTypes.size() > 0) {
		    model.addAttribute("exportListType", exportListTypes.get(0));
		    session.setAttribute("pathString", forms.get(0).getFuncDesc() + " > Export >"+exportListTypes.get(0).getListTypeDesc());
		} else {
		    model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
		    model.addAttribute("exportListType", new ListType());
		}
	
		return "/sampletype/export";
    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public String Export(ListType exportListType, Model model, Principal principal, HttpServletRequest request,
	    HttpServletResponse response) {

		try {
			String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
					new MapSqlParameterSource().addValue("TypeID", "X").addValue("UserID", principal.getName())
						.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
				
	
				if (permission.length()>0) {
				    model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
				    model.addAttribute("exportListType", new ListType());
				    return "/sampletype/export";
				}
				
		    List<Map<Object, Object>> listData = sqlRepository.GetListObj("TemplateType_ListView",
			    new MapSqlParameterSource().addValue("listcode", exportListType.getListViewCode())
				    .addValue("lantype", "001").addValue("criteria", "").addValue("userid", principal.getName())
				    .addValue("orderby", "").addValue("pagenumber", 1));
	
		    //Workbook workbook = workbookBuilderRepository.createWorkbook(listData);
		    // Sheet sheet = workbook.createSheet(exportListType.ListViewDesc.replace(" ",
		    // "_"));
		    Workbook workbook = new HSSFWorkbook();
	//	    for (int i=0;i<5;i++) {
		    	String sheetName=exportListType.getListViewDesc().replace(" ","_");
		    	Sheet sheet = workbook.createSheet(sheetName);
		    	ExcelSheetHelper.SheetData(listData, sheet);
	//	    }
			
			String fileName=sheetName + ".xls";
		    response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		    workbook.write(response.getOutputStream());
		    return null;
		} catch (Exception e) {
		    model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}
	
		model.addAttribute("exportListType", exportListType);
	
		return "/sampletype/export";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String Report(@RequestParam String id, Model model, Principal principal, HttpSession session, HttpServletRequest request)
	    throws Exception {

		String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
			new MapSqlParameterSource().addValue("TypeID", "R").addValue("UserID", principal.getName())
				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
	
		if (permission != "") {
		    model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
		    model.addAttribute("reportType", new ReportView());
		    return "/sampletype/report";
		}
		
		LoadForm(model, principal);
		
		List<ReportView> reportType = (List<ReportView>) (Object) sqlRepository.GetList(
			"TemplateType_GetReportType", new MapSqlParameterSource().addValue("ReportCode", id),
			ReportView.class);
	
		if (reportType.size() > 0) {
		    model.addAttribute("reportType", reportType.get(0));
		    session.setAttribute("pathString",  forms.get(0).getFuncDesc() +" > Report"+reportType.get(0).getReportName());
		} else {
		    model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
		    model.addAttribute("reportType", new ReportView());
		}
	
		return "/sampletype/report";
    }
    
    
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/import", method = RequestMethod.GET)
    public String Import(@RequestParam String id, Model model, Principal principal, HttpSession session,
	    HttpServletRequest request) throws Exception {
		String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
			new MapSqlParameterSource().addValue("TypeID", "V").addValue("UserID", principal.getName())
				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
	
		session.setAttribute("permission", "");
		if (permission.length() > 0) {
		    session.setAttribute("permission", permission);
		    return "redirect:/";
		}
		LoadForm(model, principal);
		
		exportListTypes = (List<ListType>) (Object) sqlRepository.GetList(
			"TemplateType_GetListType",
			new MapSqlParameterSource().addValue("ListCode", id).addValue("LanType", "001"), ListType.class);
	
		if (exportListTypes.size() > 0) {
		    model.addAttribute("exportListType", exportListTypes.get(0));
		    session.setAttribute("pathString", forms.get(0).getFuncDesc() + " > Import " + exportListTypes.get(0).getListViewDesc());
		} else {
		    model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger delay5000");
		    model.addAttribute("exportListType", new ListType());
		}
	
		
		model.addAttribute("id", id);
		return "/template/import";
    }

//    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String Import(@RequestParam String id, @RequestParam("file") MultipartFile file, Model model,
	    HttpServletRequest request, Principal principal) throws Exception {
    	
    	String permission = sqlRepository.GetString("TemplateType_VerifyPermission",
    			new MapSqlParameterSource().addValue("TypeID", "R").addValue("UserID", principal.getName())
    				.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", ""));
    	
    		if (permission != "") {
    		    model.addAttribute("message", permission).addAttribute("class", "alert alert-danger");
    		    model.addAttribute("exportListType", new ListType());
    		    return "/sampletype/import";
    		}
    		
		model.addAttribute("id", id);
		if (exportListTypes.size() > 0) {
		    model.addAttribute("exportListType", exportListTypes.get(0));
		} else {
		    model.addAttribute("message", "Not Found").addAttribute("class", "alert alert-danger");
		    model.addAttribute("exportListType", new ListType());
		}
	
		try {
		    String criteria = "";
		    	
		    Long upload = sqlRepository.GetLong("TemplateType_Upload",
			    new MapSqlParameterSource().addValue("ListCode", id).addValue("LanType", "001")
				    .addValue("UserId", principal.getName()).addValue("RemoteAddr", request.getRemoteAddr())
				    .addValue("retval", 0));
		    if (upload>0) {
			    excelReader.read(file, exportListTypes.get(0).getTableName());
			    Long i =sqlRepository.GetLong("TemplateType_Import",
				    new MapSqlParameterSource().addValue("listcode", id).addValue("lantype", "001")
					    .addValue("criteria", criteria).addValue("UserId", principal.getName())
					    .addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", 0));
		
			    if (i>0) {
				    model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
				    return "redirect:/sampletype";
			    }
		    }
		} catch (Exception e) {
		    model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}
	
		return "/sampletype/import";
    }
    
    @SuppressWarnings("unchecked")
    public void LoadForm(Model model, Principal principal) throws Exception {
    	forms = (List<FormType>) (Object) sqlRepository.GetList("TemplateType_GetForm",
				new MapSqlParameterSource().addValue("userid", principal.getName()).addValue("lantype", lanType),FormType.class);
    	
    	lanType=forms.get(0).getLanType();
    	
    	List<CodeName> captions = sqlRepository.GetParam("TemplateType_GetCaption",new MapSqlParameterSource()
				.addValue("LanType", forms.get(0).getLanType()));
    	model.addAttribute("captions", captions);
    }
    
//  @SuppressWarnings("unchecked")
  public void LoadDropdown(Model model, Principal principal) throws Exception {
  	
		typegroups = sqlRepository.GetParam("TemplateType_GetGroupList",new MapSqlParameterSource()
				.addValue("Active", ""));
		
//		reportformats = sqlRepository.GetParam("TemplateType_GetReportFormat",new MapSqlParameterSource());
				
  }
  
//  @SuppressWarnings("unchecked")
  public void LoadModel(Model model, Principal principal)  {
  			
		model.addAttribute("typegroups", typegroups);

//		model.addAttribute("reportformats", reportformats);
  }
  
    @RequestMapping(value = "/sampletype/{id}")
	public List<CodeName> Getsampletype(@PathVariable String id) throws Exception {
		return sqlRepository.GetParam("TemplateType_GetTypeList", new MapSqlParameterSource().addValue("Active", "1").addValue("TypeGroup", id));
	}
}
