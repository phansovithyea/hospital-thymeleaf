package com.ibiztechno.app.module.logout;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/logout") // parth url
public class LogoutController {

	@RequestMapping()
	public ModelAndView index() {
		return new ModelAndView("/logout/logout");// parth user interface
	}

}
