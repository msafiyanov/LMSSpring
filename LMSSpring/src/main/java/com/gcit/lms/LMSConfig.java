package com.gcit.lms;

import javax.naming.AuthenticationException;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;

@EnableTransactionManagement
@Configuration
public class LMSConfig {
	
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/library";
	private String user = "root";
	private String pass = "root";
	
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(user);
		ds.setPassword(pass);
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager txManager() {
		DataSourceTransactionManager tx = new DataSourceTransactionManager();
		tx.setDataSource(dataSource());
		return tx;
	}

	@Bean
	//@Scope(value="prototype")
	AuthorDAO aDAO() {
		AuthorDAO aDAO = new AuthorDAO();
		
		return aDAO;
	}
	
	@Bean
	BookDAO bDAO() {
		BookDAO bDAO = new BookDAO();
		
		return bDAO;
	}
	
	@Bean
	PublisherDAO pDAO() {
		PublisherDAO pDAO = new PublisherDAO();
		
		return pDAO;
	}
	
	@Bean
	LibraryBranchDAO lbDAO() {
		LibraryBranchDAO lbDAO = new LibraryBranchDAO();
		
		return lbDAO;
	}
	
	@Bean
	BorrowerDAO borDAO() {
		BorrowerDAO borDAO = new BorrowerDAO();
		
		return borDAO;
	}
	
	@Bean
	GenreDAO gDAO() {
		GenreDAO gDAO = new GenreDAO();
		
		return gDAO;
	}
	
}
