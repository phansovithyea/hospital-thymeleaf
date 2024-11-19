package com.ibiztechno.app.provider.home;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibiztechno.app.helper.ExcelReader;
import com.ibiztechno.app.helper.ReportHelper;
import com.ibiztechno.app.model.Account;
import com.ibiztechno.app.model.CodeName;
import com.ibiztechno.app.model.CodeValue;
import com.ibiztechno.app.model.Dashboard;
import com.ibiztechno.app.model.ListType;
import com.ibiztechno.app.model.FormType;
import com.ibiztechno.app.model.Pagging;
import com.ibiztechno.app.repository.sql.SqlRepository;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	SqlRepository sqlRepository;
//	@Autowired
//    ExcelReader excelReader = new ExcelReader();
//	@Autowired
//	ReportHelper reportHelper=new ReportHelper();
//	List<ExportListType> exportListTypes;
//	List<FormType> forms;
//	List<Pagging> page;
//	List<CodeName> reportformats;
//	List<CodeName> modules;
////	action
//	List<CodeName> actions;
//	List<CodeName> links;
//	List<CodeName> enquiries;
//	List<CodeName> imports;
//	List<CodeName> exports;
//	List<CodeName> reports;
//	List<CodeName> maintenances;
//	List<CodeName> trees;
////	search
//	List<CodeName> filters;
//	List<CodeName> groupBys;
//	List<CodeName> favorites;
//	List<CodeName> pages;
////	Data
//	List<Map<String, Object>> listData;
//	List<Dashboard> dashboardList;
//	List<CodeValue> dashboardData;
//	List<Map<String, Object>> kanbanData;
//	List<Map<String, Object>> listTotal;
	//
	List<Menu> menus;
	List<MenuGroup> menuGroups;
	List<Account> account;
	String lanType = "001";
	
	@SuppressWarnings("unchecked")
	@RequestMapping()
	public ModelAndView index(HttpSession session, Principal principal) throws Exception {
		// session.setAttribute("permission", "");
		String userId = principal.getName();
		
//		modules = sqlRepository.GetParam("Account_GetUserFormList",
//				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
//		actions = sqlRepository.GetParam("Account_GetActionList",
//				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
//		links = sqlRepository.GetParam("Account_GetLinkList",
//				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
//		enquiries = sqlRepository.GetParam("Account_GetEnquiryList",
//				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
//		imports = sqlRepository.GetParam("Account_GetImportList",
//				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
//		exports = sqlRepository.GetParam("Account_GetExportList",
//				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
//		reports = sqlRepository.GetParam("Account_GetReportList",
//				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
//		maintenances = sqlRepository.GetParam("Account_GetMainList",
//				new MapSqlParameterSource().addValue("UserId", userId).addValue("lantype", lanType));
		
		// Principal principal,
		menus = (List<Menu>) (Object) sqlRepository.GetList( "Account_WebMenu",
				new MapSqlParameterSource().addValue("UserID", principal.getName()).addValue("FuncType", "TEM") //
						.addValue("WebMenu", "").addValue("FuncDesc", ""),
				Menu.class);
		menuGroups = new MenuGroup().createMenuGroups(menus);
		account = (List<Account>) (Object) sqlRepository.GetList( "Account_Fundamentals",
				new MapSqlParameterSource().addValue("UserID", principal.getName()), Account.class);
		
		session.setAttribute("account", account.get(0));

		return new ModelAndView("/home/index").addObject("menuGroups", menuGroups);
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String ChangePassword(ChangePassword changePassword, Principal principal, HttpSession session) {

		changePassword.setUsername(principal.getName());
		session.setAttribute("changePassword", changePassword);

		return "/home/changePassword";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String ChangePassword(@Valid ChangePassword changePassword, Errors errors, Model model, Principal principal,
			HttpSession session) {

		session.setAttribute("changePassword", changePassword);

		if (errors.hasErrors()) {
			return "/home/changePassword";
		} else {

			try {
				Long result = sqlRepository.GetLong("Account_ChangePassword",
						new MapSqlParameterSource().addValue("UserID", principal.getName())// changePassword.getUsername()
								.addValue("OldPass", changePassword.getOldPassword())
								.addValue("NewPass", changePassword.getNewPassword()));

				if (result == 1) {
					changePassword = new ChangePassword();
					model.addAttribute("message", "Success");
					session.setAttribute("permission", "Success");
					return "redirect:/";
				}

			} catch (Exception e) {
				model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
			}

			return "/home/changePassword";
		}

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String Profile(Model model, Principal principal, HttpSession session) throws Exception {

		LoadDropdown(model, principal);

		account = (List<Account>) (Object) sqlRepository.GetList( "Account_Fundamentals",
				new MapSqlParameterSource().addValue("UserID", principal.getName()), Account.class);
		model.addAttribute("account", account.get(0));
//		session.setAttribute("account", account.get(0));

		return "/home/profile";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String Profile(@Valid Account context, Errors errors, Model model, Principal principal,
			HttpSession session, HttpServletRequest request) {

		model.addAttribute("account", context);

		if (errors.hasErrors()) {
			model.addAttribute("message", errors.getFieldErrors().get(0).getDefaultMessage()).addAttribute("class",
					"alert alert-danger");
			return "/home/profile";
		}

		try {
			Long result = sqlRepository.GetLong("Account_Update",
					new MapSqlParameterSource().addValue("UserID", context.getUserId())
							.addValue("UFName", context.getUFName()).addValue("ULName", context.getULName())
							.addValue("Email", context.getEmail()).addValue("UDLang", context.getUDLang())
							.addValue("UDesc", context.getUDesc()).addValue("RemoteAddr", request.getRemoteAddr())
							.addValue("retval", 0));

			if (result > 0) {
				session.setAttribute("account", new Account());
				model.addAttribute("account", new Account());
				model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
				session.setAttribute("permission", "Success");
				return "redirect:/";
			}

		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}

		return "/home/profile";
	}

//  @SuppressWarnings("unchecked")
	public void LoadDropdown(Model model, Principal principal) throws Exception {

		List<CodeName> lantypes = sqlRepository.GetParam("Account_GetLanguageTypeList",
				new MapSqlParameterSource().addValue("Active", "1"));
		model.addAttribute("lantypes", lantypes);

	}
}
