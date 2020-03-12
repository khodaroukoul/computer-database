package fr.excilys.formation.cdb.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"fr.excilys.formation.cdb.servlets",
		"fr.excilys.formation.cdb.dao","fr.excilys.formation.cdb.service",
		"fr.excilys.formation.cdb.validator"})
public class SpringConfig  extends AbstractContextLoaderInitializer {

	@Autowired
	Environment environment;

	private final String DRIVER = "dataSource.driverClassName";
	private final String URL = "dataSource.jdbcUrl";
	private final String USER = "dataSource.username";
	private final String PASSWORD = "dataSource.password";
	
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(SpringConfig.class);
		return applicationContext;
	}

	@Bean
	DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

		driverManagerDataSource.setDriverClassName(environment.getRequiredProperty(DRIVER));
		driverManagerDataSource.setUrl(environment.getRequiredProperty(URL));
		driverManagerDataSource.setUsername(environment.getRequiredProperty(USER));
		driverManagerDataSource.setPassword(environment.getRequiredProperty(PASSWORD));
		
		return driverManagerDataSource;
	}	
}
