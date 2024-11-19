package com.ibiztechno.app.helper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class Main {

	public static void main(String[] args) {

//System.out.println(CreateGet(Customerblacklist.class));

//System.out.println(CreatePost(Assetcategory.class));
//	System.out.println(CreateHtml(Assetcontpay.class, true));	

//System.out.println(CreateGet(Empleave.class));
//System.out.println(CreatePost(Assetcontract.class));
//	System.out.println(CreateHtml(AssetcontractView.class, true));	

//	System.out.println(ViewGet(Nationality.class));

//System.out.println(CreatePost(Customerblacklist.class));
		// System.out.println(CreateHtml(Customerblacklist.class, true));
//	System.out.println(ViewGet(AssetcontractView.class));

	}

	public static String CreateGet(Class c) {
		String strJava = "";

		strJava += "@RequestMapping(value = \"/create\", method = RequestMethod.GET)\r\n" + "	public String Create("
				+ c.getSimpleName() + " " + c.getSimpleName().toLowerCase() + ", Model model) {\r\n" + "\r\n"
				+ "		\r\n" + "\r\n" + "		return \"/" + c.getSimpleName().toLowerCase() + "/create\";\r\n"
				+ "	}";
		return strJava;
	}

	public static String CreateHtml(Class c, Boolean isDisabled) {
		String strJava = "";

		strJava += "<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "\r\n"
				+ "<head th:replace=\"layout/header :: header\"></head>\r\n" + "\r\n" + "<body>\r\n"
				+ "    <nav th:replace=\"layout/navbar_menu :: navbar_menu\"></nav>\r\n" + "\r\n" + "\r\n" + "\r\n"
				+ "    <form th:action=\"@{/" + c.getSimpleName().toLowerCase()
				+ "/create}\" method=\"POST\" th:object=\"${" + c.getSimpleName().toLowerCase() + "}\">\r\n"
				+ "        <div  style=\"padding-top: 4rem; padding-left: 1rem; padding-right: 1rem; \">\r\n"
				+ "            <button type=\"submit\" class=\"btn btn-sm btn-primary\">Save</button>\r\n"
				+ "            <a th:href=\"@{/" + c.getSimpleName().toLowerCase()
				+ "}\" class=\"btn btn-sm btn-warning\">Cancel</a>\r\n" + "        </div>\r\n" + "        <hr>\r\n"
				+ "\r\n" + "        <div class=\"container\">\r\n" + "\r\n"
				+ "            <div th:if=\"${message}\" th:text=\"${message}\" th:class=\"${class}\" role=\"alert\"></div>\r\n"
				+ "\r\n";
		int i = 2;
		for (Field f : c.getDeclaredFields()) {
			Class t = f.getType();
			if (i % 2 == 0) {
				strJava += " 	 <div class=\"row form-group\">\r\n";
			}

			if (f.getType() == Boolean.class) {
				strJava += "                <label for=\"" + f.getName()
						+ "\" class=\"control-label col-md-2 text-sm-left text-md-right\"></label>\r\n";
				strJava += "                <div class=\"col-md-4\">\r\n";
				strJava += "				<input type=\"checkbox\" " + (isDisabled == true ? "disabled" : "")
						+ " th:field=\"*{" + f.getName() + "}\" th:check=\"*{" + f.getName() + "}\">";
				strJava += "                		<label for=\"" + f.getName() + "\" class=\"control-label\">"
						+ f.getName() + "</label>\r\n";
				strJava += "            \r\n    </div>\r\n\n";
			} else if (f.getType() == Integer.class || f.getType() == BigDecimal.class || f.getType() == Float.class
					|| f.getType() == Double.class || f.getType() == Long.class || f.getType() == Short.class
					|| f.getType() == Byte.class) {
				strJava += "                <label for=\"" + f.getName()
						+ "\" class=\"control-label col-md-2 text-sm-left text-md-right\">" + f.getName()
						+ "</label>\r\n";
				strJava += "               <div class=\"col-md-4\">\r\n";
				strJava += "				<input type=\"number\" th:field=\"*{" + f.getName()
						+ "}\" th:class=\"${#fields.hasErrors('" + f.getName()
						+ "')} ? 'form-control form-control-sm is-invalid' : 'form-control form-control-sm'\">";
				strJava += "               \r\n </div>\r\n";
			} else {
				strJava += "\r\n";
				strJava += "                <label for=\"" + f.getName()
						+ "\" class=\"control-label col-md-2 text-sm-left text-md-right\">" + f.getName()
						+ "</label>\r\n";
				strJava += "                <div class=\"col-md-4\">\r\n";
				strJava += "		  <input type=\"text\" th:field=\"*{" + f.getName()
						+ "}\" th:class=\"${#fields.hasErrors('" + f.getName()
						+ "')} ? 'form-control form-control-sm is-invalid' : 'form-control form-control-sm'\">";
				strJava += "\r\n    		</div>\r\n";
			}
//	strJava +=	"                <label for=\""+f.getName()+"\" class=\"control-label col-md-2 text-sm-left text-md-right\">"+f.getName()+"</label>\r\n" + 
//		"                <div class=\"col-md-4\">\r\n" + 
//		"                    <input type=\"text\" class=\"form-control\" th:field=\"*{"+f.getName()+"}\">\r\n" + 
//		"                </div>\r\n" + 
//		"\r\n" ;

			if (i % 2 != 0) {
				strJava += "           </div>\r\n";
			}
			i++;
		}
		strJava += "\r\n" +

				"        </div>\r\n" + "    </form>\r\n" + "</body>\r\n"
				+ "<div th:replace=\"layout/script :: script\"></div>\r\n" + "\r\n" + "</html>";

		return strJava;
	}

	public static String CreatePost(Class c) {
		String strJava = "";

		strJava += "@SuppressWarnings(\"unused\")\r\n"
				+ "	@RequestMapping(value = \"/create\", method = RequestMethod.POST)\r\n"
				+ "	public String Create(@Valid " + c.getSimpleName() + " " + c.getSimpleName().toLowerCase()
				+ ", Errors errors, Model model, Principal principal) {\r\n" + "\r\n" +

				"		if (errors.hasErrors()) {\r\n" + "			return \"/" + c.getSimpleName().toLowerCase()
				+ "/create\";\r\n" + "		}\r\n" + "\r\n" + "		try {\r\n"
				+ "			Long result = accountRepository.GetLong(\"\",\r\n"
				+ "					new MapSqlParameterSource()\r\n";

		for (Field f : c.getDeclaredFields()) {
			Class t = f.getType();
			strJava += "					.addValue(\"" + f.getName() + "\", " + c.getSimpleName().toLowerCase()
					+ ".get" + f.getName() + "())\r\n";
		}
//	    	"					.addValue(\"Active\", \"\")\r\n" +

		strJava += "\r\n" + "			);\r\n" + "			if (result > 0) {\r\n" + "\r\n" + "				"
				+ c.getSimpleName().toLowerCase() + " = new " + c.getSimpleName() + "();\r\n"
				+ "				model.addAttribute(\"message\", \"Success\").addAttribute(\"class\", \"alert alert-success\");\r\n"
				+ "\r\n" + "			}\r\n" + "		} catch (Exception e) {\r\n"
				+ "			model.addAttribute(\"message\", e.getMessage()).addAttribute(\"class\", \"alert alert-danger\");\r\n"
				+ "		}\r\n" + "\r\n" + "		return \"/" + c.getSimpleName().toLowerCase() + "/create\";\r\n"
				+ "	}";

		return strJava;
	}

}
