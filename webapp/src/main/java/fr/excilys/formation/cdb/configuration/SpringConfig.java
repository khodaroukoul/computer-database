package fr.excilys.formation.cdb.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

@Configuration
@ComponentScan(basePackages = {"fr.excilys.formation.cdb.controller",
        "fr.excilys.formation.cdb.dao", "fr.excilys.formation.cdb.service",
        "fr.excilys.formation.cdb.validator", "fr.excilys.formation.cdb.mapper",
        "fr.excilys.formation.cdb.model"})
public class SpringConfig implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {
        DispatcherServlet dispatcherServlet;
        try (AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext()) {
            ctx.register(SpringConfig.class, HibernateConfig.class, WebConfig.class);
            ctx.setServletContext(container);
            dispatcherServlet = new DispatcherServlet(ctx);
        }
        ServletRegistration.Dynamic servlet = container.addServlet("dashboard", dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
    }
}