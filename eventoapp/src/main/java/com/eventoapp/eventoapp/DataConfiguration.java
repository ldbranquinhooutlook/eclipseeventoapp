package com.eventoapp.eventoapp;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DataConfiguration {

	@Bean
	public DataSource ds() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		//ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");		
		ds.setUrl("jdbc:mysql://localhost:3306/evento");
		ds.setUsername("root");
		ds.setPassword("C@d02ware");
		return ds;
	}
	
	@Bean
	public JpaVendorAdapter jpaConn () {
		
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(true);;
		adapter.setGenerateDdl(true);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
		adapter.setPrepareConnection(true);
		return adapter;
	}
	
}
