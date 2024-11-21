package com.ibiztechno.app.module.register;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibiztechno.app.repository.SqlRepository;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	SqlRepository accountRepository;

	Account _account;

	@RequestMapping()
	public String register(Model model) {
		_account = new Account();

		model.addAttribute("account", _account);
		return "/register/register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String ContinueRegister(@Valid Account account, Model model, HttpServletRequest request) {

		try {
			Long result = accountRepository.GetLong("Account_VerifyEmail",
					new MapSqlParameterSource().addValue("Email", account.getEmail())
							.addValue("sidcard", account.getSidcard()).addValue("RemoteAddr", request.getRemoteHost())
							.addValue("retval", 0));

			if (result > 0) {
				_account.setEmail(account.getEmail());
				_account.setSidcard(account.getSidcard());
				return "redirect:/register/verified";
			}

		} catch (Exception e) {
			model.addAttribute("message", e.getMessage()).addAttribute("class", "alert alert-danger");
		}
		model.addAttribute("account", _account);
		return "/register/register";
	}

	@RequestMapping(value = "/verified", method = RequestMethod.GET)
	public String verified(Model model) {
		try {
			if (_account.getEmail().equalsIgnoreCase("")) {
				return "redirect:/register";
			}
		} catch (Exception ex) {
			_account = new Account();
			return "redirect:/register";
		}

		model.addAttribute("account", _account);
		model.addAttribute("message",
				"Your user have been sent to your mail box. Please fill up data from your mail box below")
				.addAttribute("class", "alert alert-success");
		return "/register/verified";
	}

	@RequestMapping(value = "/verified", method = RequestMethod.POST)
	public String saveVerified(Account account, Model model, HttpServletRequest request) {
		account.setEmail(_account.getEmail());
		account.setSidcard(_account.getSidcard());
		model.addAttribute("account", account);
		try {
			Long result = accountRepository.GetLong("Account_Create",
					new MapSqlParameterSource().addValue("sidcard", account.getSidcard())
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
