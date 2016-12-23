package com.jain;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

//@Component("programatic003")
public class _003Programatic{

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("003programatic.xml");
		_003Programatic userDAO = (_003Programatic)context.getBean("programatic003");
		// Check this. There is no proxy created. Which is correct since there is no transaction related aspect in the xml.
		System.out.println(userDAO.getClass());
		userDAO.createUser();
	}
	
	private JdbcTemplate jdbcTemplateObject;
	private PlatformTransactionManager transactionManager;
	@Autowired
	public void setDataSource(DataSource dataSource) { 
		this.jdbcTemplateObject = new JdbcTemplate(dataSource); 
	}
	@Autowired
	public void setTransactionManager(PlatformTransactionManager transactionManager) { 
		this.transactionManager = transactionManager; 
	}

	public void createUser(){
		TransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(def);
		try {
			int count = jdbcTemplateObject.update("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)", new Object[]{"xxx","xxx"});
			System.out.println("inserting user " + count);
//			below sql has error and so above sql will be rolled back
			count = jdbcTemplateObject.update("UPDATE USER_DETAILS SET USER_ID = = ? WHERE USER_ID=?", new Object[]{"zzz","lll"});
			System.out.println("updating user " + count);
			transactionManager.commit(status);
		} catch (DataAccessException e) {
			System.out.println("Error in creating record, rolling back");
			transactionManager.rollback(status);
			throw e;
		}
	}
}