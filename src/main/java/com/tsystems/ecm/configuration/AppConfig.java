package com.tsystems.ecm.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:database.properties")
@ComponentScan("com.tsystems.ecm")
public class AppConfig {

    private static final Logger log = LogManager.getLogger(AppConfig.class);


    @Autowired
    private Environment environment;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.tsystems.ecm.entity");

        Properties properties = new Properties();

        String hibernateDialect = "hibernate.dialect";
        String hibernateShowSQL = "hibernate.show_sql";
        String hibernateFormatSQL = "hibernate.format_sql";
        String hibernateHBM2DDLAuto = "hibernate.hbm2ddl.auto";

        properties.put(hibernateDialect, environment.getRequiredProperty(hibernateDialect));
        properties.put(hibernateShowSQL, environment.getRequiredProperty(hibernateShowSQL));
        properties.put(hibernateFormatSQL, environment.getRequiredProperty(hibernateFormatSQL));
        properties.put(hibernateHBM2DDLAuto, environment.getRequiredProperty(hibernateHBM2DDLAuto));
        sessionFactory.setHibernateProperties(properties);

        log.trace("Setting Hibernate properties (hibernate.dialect: {}, hibernate.show_sql: {}, hibernate.format_sql: {}, hibernate.hbm2ddl.auto: {})",
                sessionFactory.getHibernateProperties().getProperty(hibernateDialect),
                sessionFactory.getHibernateProperties().getProperty(hibernateShowSQL),
                sessionFactory.getHibernateProperties().getProperty(hibernateFormatSQL),
                sessionFactory.getHibernateProperties().getProperty(hibernateHBM2DDLAuto));

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("db.driver"));
        dataSource.setUsername(environment.getRequiredProperty("db.username"));
        dataSource.setPassword(environment.getRequiredProperty("db.password"));
        dataSource.setJdbcUrl(environment.getRequiredProperty("db.url"));
        log.trace("Setting dataSource with parameters (username: {}, password: [PROTECTED], JDBC URL: {})", dataSource.getUsername(), dataSource.getJdbcUrl());

        return dataSource;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}
