//package com.ibiztechno.app.config;
//
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
//import org.springframework.stereotype.Component;
//
//@Component
//public class LdapAuthenticationProvider implements AuthenticationProvider {
////	@Value("${ldap.url:ldap://mycompany.com:389}") private String url;
////    @Value("${ldap.domain:mycompany.com}") private String domain;
//	@Value("${ldap.url}") private String url;
//    @Value("${ldap.domain}") private String domain;
//    @Value("${ldap.userDNPattern}") private String userDNPattern;
//    
////	@Autowired
////	SqlRepository sqlRepository;
//
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		
//		String name = authentication.getName();
//		String password = authentication.getCredentials().toString();
//		
//		try {
//			Long out = (long) 0;
//			
//			ActiveDirectoryLdapAuthenticationProvider adProvider = 
//                    new ActiveDirectoryLdapAuthenticationProvider(domain,url);
//	        adProvider.setConvertSubErrorCodesToExceptions(true);
//	        adProvider.setUseAuthenticationRequestCredentials(true);
//	
//	        // set pattern if it exists
//	        // The following example would authenticate a user if they were a member
//	        // of the ServiceAccounts group
//	        // (&(objectClass=user)(userPrincipalName={0})
//	        //   (memberof=CN=ServiceAccounts,OU=alfresco,DC=mycompany,DC=com))
//	        if (userDNPattern != null && userDNPattern.trim().length() > 0)
//	        {
//	            adProvider.setSearchFilter(userDNPattern);
//	            adProvider.authenticate(authentication);
//	            
//	            out = (long) 1;
////		        auth.authenticationProvider(adProvider);
//	        }
//	        
//	        // don't erase credentials if you plan to get them later
//	        // (e.g using them for another web service call)
////	        auth.eraseCredentials(false);
//        
//			//sqlRepository.GetLong("Account_Login", new MapSqlParameterSource().addValue("UserID", name)
////					.addValue("Password", password).addValue("RemoteAddr", GetHostName()).addValue("retval", 0));
//
//			if (out > 0) {
//				// use the credentials
//				// and authenticate against the third-party system
//
//				return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
//			} else {
//				throw new BadCredentialsException("Invalid Cerdential");
//			}
//		} catch (Exception e) {
//			throw new BadCredentialsException(e.getMessage());
//		}
//	}
//	
//	@Override
//	public boolean supports(Class<?> authentication) {
//		return authentication.equals(UsernamePasswordAuthenticationToken.class);
//	}
//}
