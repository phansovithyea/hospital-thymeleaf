package com.ibiztechno.app.provider.ResetPassword;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ibiztechno.app.provider.register.Account;
import com.ibiztechno.app.repository.sql.SqlRepository;

@Controller
@RequestMapping("/reset")
public class ResetPasswordController {
    @Autowired
    SqlRepository accountRepository;

    AccountReset _account;

    @RequestMapping()
    public String index(Model model) {
	_account = new AccountReset();

	model.addAttribute("account", _account);
	return "/reset/reset";// parth user interface
    }

    @RequestMapping(method = RequestMethod.POST)
    public String ContinueReset(@Valid AccountReset account, Model model, HttpServletRequest request) {

		try {
		    Long result = accountRepository.GetLong("Account_ResetPassByEmail",
			    new MapSqlParameterSource().addValue("Email", account.getEmail())
				    .addValue("UserID", account.getUserID()).addValue("RemoteAddr", request.getRemoteHost())
				    .addValue("retval", 0));
	
		    if (result > 0) {
			_account.setEmail(account.getEmail());
			_account.setUserID(account.getUserID());
			return "redirect:/reset/verified";
		    }
	
		} catch (Exception e) {
		    model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}
		model.addAttribute("account", _account);
		return "/reset/reset";
    }

    @RequestMapping(value = "/verified", method = RequestMethod.GET)
    public String verified(Model model) {
		try {
		    if (_account.getEmail().equalsIgnoreCase("")) {
			return "redirect:/reset";
		    }
		} catch (Exception ex) {
		    _account = new AccountReset();
		    return "redirect:/reset";
		}
	
		model.addAttribute("account", _account);
		model.addAttribute("message",
			"Your recovery password have been sent to your mail. Please fill up data from your mail to box below")
			.addAttribute("class", "alert alert-success");
		return "/reset/verified";
    }

    @RequestMapping(value = "/verified", method = RequestMethod.POST)
    public String saveVerified(Account account, Model model, HttpServletRequest request) {
	account.setEmail(_account.getEmail());
	account.setSidcard(_account.getSidcard());
	model.addAttribute("account", account);
	try {
	    Long result = accountRepository.GetLong("Account_ResetPassword",
	    		new MapSqlParameterSource()//.addValue("sidcard", account.getSidcard())
			    .addValue("Email", account.getEmail()).addValue("FullName", account.getFullName())
			    .addValue("UserID", account.getUserID()).addValue("Password", account.getPassword())
			    .addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", 0));


	    if (result > 0) {
			account = new Account();
			model.addAttribute("message", "Success").addAttribute("class", "alert alert-success");
			return "redirect:/login";
	    }
	    model.addAttribute("message", "Failed").addAttribute("class", "alert alert-danger");
	} catch (Exception e) {
	    model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
	}
	return "/register/verified";
    }
}
