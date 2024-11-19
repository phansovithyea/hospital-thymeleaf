package com.ibiztechno.app.provider.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login") // parth url
public class LoginController {

    @RequestMapping()
    public ModelAndView index() {
    	return new ModelAndView("/login/login");// parth user interface
    }
}
