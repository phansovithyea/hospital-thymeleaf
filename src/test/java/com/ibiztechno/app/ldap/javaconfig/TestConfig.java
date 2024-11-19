package com.ibiztechno.app.ldap.javaconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.test.TestContextSourceFactoryBean;

import com.ibiztechno.app.config.LdapClient;

@Configuration
@PropertySource("classpath:application.properties")
//@ComponentScan(basePackages = {"com.ibiztechno.app.ldap.*"})
//@EnableLdapRepositories(basePackages = "com.ibiztechno.app.ldap.**")
//@Profile("testlive")
public class TestConfig {
	@Autowired
    private Environment env;

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public TestContextSourceFactoryBean testContextSource() {
        TestContextSourceFactoryBean contextSource = new TestContextSourceFactoryBean();
        contextSource.setDefaultPartitionName(env.getRequiredProperty("ldap.partition"));
        contextSource.setDefaultPartitionSuffix(env.getRequiredProperty("ldap.partitionSuffix"));
        contextSource.setPrincipal(env.getRequiredProperty("ldap.principal"));
        contextSource.setPassword(env.getRequiredProperty("ldap.password"));
        contextSource.setLdifFile(resourceLoader.getResource(env.getRequiredProperty("ldap.ldiffile")));
        contextSource.setPort(Integer.valueOf(env.getRequiredProperty("ldap.port")));
        return contextSource;
    }

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(env.getRequiredProperty("ldap.url"));
        contextSource.setBase(env.getRequiredProperty("ldap.partitionSuffix"));
        contextSource.setUserDn(env.getRequiredProperty("ldap.principal"));
        contextSource.setPassword(env.getRequiredProperty("ldap.password"));
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

    @Bean
    public LdapClient ldapClient() {
        return new LdapClient();
    }
}
