package com.ibiztechno.app.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.ibiztechno.app.repository.sql.SqlRepository;

@Service
public class CustomLogoutHandler implements LogoutHandler {

	@Autowired
	SqlRepository accountRepository;

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		String name = authentication.getName();
		try {
			accountRepository.GetLong("Account_Logout", new MapSqlParameterSource().addValue("UserID", name)
					.addValue("RemoteAddr", request.getRemoteAddr()).addValue("retval", 0));
		} catch (Exception ex) {

		}
		authentication.setAuthenticated(false);
	}

}
