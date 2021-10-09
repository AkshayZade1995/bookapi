package com.books.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import static org.hibernate.cfg.Environment.*;

import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScans( value = {
		@ComponentScan("com.books.dao"),
		@ComponentScan("com.books.service")
})
public class AppConfig {
	
	@Autowired
	private Environment env;
	
	
	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		
		Properties properties = new Properties();
		
		properties.put(DRIVER, env.getProperty("mysql.driver"));
		properties.put(URL, env.getProperty("mysql.url"));
		properties.put(USER, env.getProperty("mysql.user"));
		properties.put(PASS, env.getProperty("mysql.password"));
		
		
		//Setting hibernate properties
		properties.put(SHOW_SQL, env.getProperty("hibernate.show_sql"));
		properties.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));
		
		//Setting C3P0 Connection pool properties
		properties.put(C3P0_MIN_SIZE, env.getProperty("hibernate.c3p0.min_size"));
		properties.put(C3P0_MAX_SIZE, env.getProperty("hibernate.c3p0.max_size"));
		properties.put(C3P0_ACQUIRE_INCREMENT, env.getProperty("hibernate.c3p0.aquire_increment"));
		properties.put(C3P0_TIMEOUT, env.getProperty("hibernate.c3p0.timeout"));
		properties.put(C3P0_MAX_STATEMENTS, env.getProperty("hibernate.c3p0.max_statements"));
		
		factoryBean.setHibernateProperties(properties);
		
		factoryBean.setPackagesToScan("com.bookapi.model");
		
		return factoryBean;

	}
	
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}

}
