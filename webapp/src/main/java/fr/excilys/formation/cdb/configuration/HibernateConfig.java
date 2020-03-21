package fr.excilys.formation.cdb.configuration;

import fr.excilys.formation.cdb.model.Company;
import fr.excilys.formation.cdb.model.Computer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    Environment environment;

    private final String DRIVER = "dataSource.driverClassName";
    private final String URL = "dataSource.jdbcUrl";
    private final String USER = "dataSource.username";
    private final String PASSWORD = "dataSource.password";

    @Bean
    DataSource dataSource(Environment environment) {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setDriverClassName(environment.getRequiredProperty(DRIVER));
        driverManagerDataSource.setUrl(environment.getRequiredProperty(URL));
        driverManagerDataSource.setUsername(environment.getRequiredProperty(USER));
        driverManagerDataSource.setPassword(environment.getRequiredProperty(PASSWORD));

        return driverManagerDataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource(environment));
        factoryBean.setAnnotatedClasses(Computer.class, Company.class);
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}