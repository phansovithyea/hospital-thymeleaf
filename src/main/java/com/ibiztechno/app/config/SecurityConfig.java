package com.ibiztechno.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ibiztechno.app.repository.SqlRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	@Value("${ldap.url}") private String url;
//	@Value("${ldap.domain}") private String domain;
//  @Value("${ldap.userDNPattern:}") private String userDNPattern;
	
	@Autowired
	SqlRepository sqlRepository; 
	
//	@Autowired
//    private LdapAuthenticationProvider adProvider;
    @Autowired
    private CustomAuthenticationProvider authProvider;
    @Autowired
    private CustomLogoutHandler logoutHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

	http.authorizeRequests()
		.antMatchers("/login**", "/static/**","/register**","/register/verified**","/reset**","/reset/verified**").permitAll()
		.antMatchers(HttpMethod.POST, "/register**").permitAll()
		.antMatchers(HttpMethod.POST, "/register/verified**").permitAll()
		.anyRequest()
		.authenticated()// any other request just need authentication
		
		.and().formLogin()
			.loginPage("/login")
		 	.defaultSuccessUrl("/")
//		 	.failureUrl("/login")
		.and().logout()
			.logoutUrl("/logout")
			.addLogoutHandler(logoutHandler)
			.logoutSuccessUrl("/login");
	
	
	
//			.loginPage("/login")
//	          .loginProcessingUrl("/perform_login")
//	          .defaultSuccessUrl("/", true)
//	          .failureUrl("/login?error=true");

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
    	
//    	if (isAuthAD()>0) {
    		 
//			auth
//			.ldapAuthentication()
//			  .userDnPatterns("uid={0},ou=people")
//			  .groupSearchBase("ou=groups")
//			  .contextSource()
//			  .url(url)
////			  .url("ldap://localhost:8389/dc=springframework,dc=org")
//			  .and()
//			  .passwordCompare()
//			  .passwordEncoder(new BCryptPasswordEncoder())
//			  .passwordAttribute("userPassword");
//    		auth.authenticationProvider(adProvider);
//    	} else {
    		auth.authenticationProvider(authProvider);
//    	}
    
//		builder.inMemoryAuthentication().withUser("tim").password("{noop}123").roles("").and().withUser("joe")
//				.password("234").roles("USER");
	
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
//    @Override
//    protected UserDetailsService userDetailsService() {
//        // TODO Auto-generated method stub
//        return super.userDetailsService();
//    }
}
