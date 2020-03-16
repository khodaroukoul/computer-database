package fr.excilys.formation.cdb.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"fr.excilys.formation.cdb.controller",
		"fr.excilys.formation.cdb.dao","fr.excilys.formation.cdb.service",
		"fr.excilys.formation.cdb.validator"})
public class SpringConfig  implements WebApplicationInitializer {
	
	@Autowired
	Environment environment;

	private final String DRIVER = "dataSource.driverClassName";
	private final String URL = "dataSource.jdbcUrl";
	private final String USER = "dataSource.username";
	private final String PASSWORD = "dataSource.password";
	
	@Bean
	DataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

		driverManagerDataSource.setDriverClassName(environment.getRequiredProperty(DRIVER));
		driverManagerDataSource.setUrl(environment.getRequiredProperty(URL));
		driverManagerDataSource.setUsername(environment.getRequiredProperty(USER));
		driverManagerDataSource.setPassword(environment.getRequiredProperty(PASSWORD));
		
		return driverManagerDataSource;
	}

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(WebConfig.class, SpringConfig.class);
		ctx.setServletContext(container);
		DispatcherServlet dv = new DispatcherServlet(ctx);
		ServletRegistration.Dynamic servlet = container.addServlet("dashboard", dv);
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}	
}
