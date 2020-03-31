package fr.excilys.formation.cdb.configuration;

import fr.excilys.formation.cdb.model.Company;
import fr.excilys.formation.cdb.model.Computer;
import fr.excilys.formation.cdb.model.UserRole;
import fr.excilys.formation.cdb.model.Users;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class HibernateConfig {

    final Environment environment;

    public HibernateConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    DataSource dataSource(Environment environment) {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        String DRIVER = "dataSource.driverClassName";
        driverManagerDataSource.setDriverClassName(environment.getRequiredProperty(DRIVER));
        String URL = "dataSource.jdbcUrl";
        driverManagerDataSource.setUrl(environment.getRequiredProperty(URL));
        String USER = "dataSource.username";
        driverManagerDataSource.setUsername(environment.getRequiredProperty(USER));
        String PASSWORD = "dataSource.password";
        driverManagerDataSource.setPassword(environment.getRequiredProperty(PASSWORD));

        return driverManagerDataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource(environment));
        factoryBean.setAnnotatedClasses(Computer.class, Company.class, Users.class, UserRole.class);

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());

        return transactionManager;
    }
}