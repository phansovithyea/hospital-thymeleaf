package com.ibiztechno.app.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ibiztechno.app.repository.SqlRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
    private LdapClient ldapClient;
	
	@Autowired
	SqlRepository sqlRepository; 

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		try {
//			if (isAuthAD()>0) {
//				
//				ldapClient.authenticate(username, password);
//				
//				return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
//			} else {
				Long result = sqlRepository.GetLong("Account_Login", new MapSqlParameterSource()
						.addValue("UserID", username).addValue("Password", password)
						.addValue("RemoteAddr", GetHostName()).addValue("retval", 0));
			
				if (result > 0) {
	//				 use the credentials
	//				 and authenticate against the third-party system
	
					return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
				} else {
					throw new BadCredentialsException("Invalid Cerdential");
				}
//			}
		} catch (Exception e) {
			throw new BadCredentialsException(sqlRepository.messageTrimmer(e.getMessage()));
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	public String GetHostName() {
		try {
			InetAddress id = InetAddress.getLocalHost();
			return id.getHostName();
		} catch (UnknownHostException e) {
			return "";
		}
	}
	
	public Long isAuthAD()throws Exception{
    	try {

			Long authad=sqlRepository.GetLong("Account_GetAuthAD", new MapSqlParameterSource()
					.addValue("UserID", "").addValue("retval", 0));
			
			return authad;
			
    	} catch (Exception e) {
			throw new  Exception(e.getMessage());
		}
    }
	
	public Long getUserbyAD()throws Exception{
    	try {

			Long authad=sqlRepository.GetLong("Account_GetAuthAD", new MapSqlParameterSource()
					.addValue("UserID", "").addValue("retval", 0));
			
			return authad;
			
    	} catch (Exception e) {
			throw new  Exception(e.getMessage());
		}
    }
}
