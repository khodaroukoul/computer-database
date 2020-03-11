package fr.excilys.formation.cdb.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
@ComponentScan(basePackages = {"fr.excilys.formation.cdb.servlets",
		"fr.excilys.formation.cdb.dao","fr.excilys.formation.cdb.connection",
		"fr.excilys.formation.cdb.service","fr.excilys.formation.cdb.validator"})
public class SpringContextConfig  extends AbstractContextLoaderInitializer {

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(SpringContextConfig.class);
		return applicationContext;
	}
}
