//package com.ibiztechno.app.provider.error;
//
//import java.security.Principal;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import com.ibiztechno.app.repository.sql.SqlRepository;
//
//@Controller
//public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
//
//@Autowired
//SqlRepository accountRepository;
//
//@Override
//public String getErrorPath() {
//	return "/error";
//}
//
//@GetMapping(value = "/error")
//public String handleError(HttpServletRequest request, Principal p) {
//
//	try {
//	    Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
//	    accountRepository.GetString("ProcedureLog_Add",
//		    new MapSqlParameterSource().addValue("ObjectID", "0").addValue("ParamDefinition", p.getName())
//			    .addValue("DatabaseID", "0").addValue("AdditionalInfo", exception.getMessage()));
//	} catch (Exception ex) {
//	}
//
//	return "/error/error";
//}
//}
