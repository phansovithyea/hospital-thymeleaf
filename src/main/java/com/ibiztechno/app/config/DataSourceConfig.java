package com.ibiztechno.app.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
public class DataSourceConfig {
	@Autowired
	Environment env;

//    @Bean(name = "CBS")
//    public JdbcTemplate jdbcCBSTemplate(@Qualifier(value = "CBSDataSource") DataSource CBSDataSource) {
//	return new JdbcTemplate(CBSDataSource);
//    }

//	@Bean(name = "ERP")
//	public JdbcTemplate jdbcERPTemplate(@Qualifier(value = "ERPDataSource") DataSource ERPDataSource) {
//		return new JdbcTemplate(ERPDataSource);
//	}

//    @Bean(name = "SVFE")
//    public NamedParameterJdbcTemplate jdbcSVFETemplate(@Qualifier(value = "SVFEDataSource") DataSource SVFEDataSource) {
//	return new NamedParameterJdbcTemplate(SVFEDataSource);
//    }

//    @Bean(name = "CBSDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.cbs")
//    public DataSource CBS() {
//	DriverManagerDataSource dataSource = new DriverManagerDataSource();
//	dataSource.setDriverClassName(env.getProperty("spring.datasource.cbs.driverClassName"));
//	dataSource.setUrl(env.getProperty("spring.datasource.cbs.url"));
//	dataSource.setUsername(env.getProperty("spring.datasource.cbs.username"));
//	dataSource.setPassword(env.getProperty("spring.datasource.cbs.password"));
//
//	return dataSource;
//    }

//	@Primary
//	@Bean(name = "ERPDataSource")
//	@ConfigurationProperties(prefix = "spring.datasource.erp")
//	public DataSource ERP() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(env.getProperty("spring.datasource.erp.driverClassName"));
//		dataSource.setUrl(env.getProperty("spring.datasource.erp.url"));
//		dataSource.setUsername(env.getProperty("spring.datasource.erp.username"));
//		dataSource.setPassword(env.getProperty("spring.datasource.erp.password"));
//
//		return dataSource;
//	}

//    @Primary
//    @Bean(name = "SVFEDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.svfe")
//    public DataSource SVFE() {
//	DriverManagerDataSource dataSource = new DriverManagerDataSource();
//	dataSource.setDriverClassName(env.getProperty("spring.datasource.svfe.driverClassName"));
//	dataSource.setUrl(env.getProperty("spring.datasource.svfe.url"));
//	dataSource.setUsername(env.getProperty("spring.datasource.svfe.username"));
//	dataSource.setPassword(env.getProperty("spring.datasource.svfe.password"));
//
//	return dataSource;
//    }
	@Bean
    public DatabaseProperties databaseProperties() {
    	return new DatabaseProperties();
    }
    
    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
    	return new TomcatServletWebServerFactory() {
			@Override
			protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
				tomcat.enableNaming();
				return super.getTomcatWebServer(tomcat);
			}
			
			@Override
			protected void postProcessContext(Context context) {
				ContextResource resource = new ContextResource();

				resource.setType("org.apache.tomcat.jdbc.pool.DataSource");
				resource.setName(databaseProperties().getJndiName());
				resource.setProperty("factory", "org.apache.tomcat.jdbc.pool.DataSourceFactory");
				resource.setProperty("driverClassName", databaseProperties().getDriverClassName());
				resource.setProperty("url", databaseProperties().getUrl());
				resource.setProperty("password", databaseProperties().getPassword());
				resource.setProperty("username", databaseProperties().getUsername());

				context.getNamingResources().addResource(resource);
			}
		};
    }
    
//    @Primary
    @Bean(destroyMethod = "")
	public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName("java:comp/env/" + databaseProperties().getJndiName());
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		bean.afterPropertiesSet();
		return (DataSource) bean.getObject();
	}
}
