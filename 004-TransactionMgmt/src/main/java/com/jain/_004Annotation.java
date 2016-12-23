/*
tx:annotation-driven element is used to tell Spring context that we are using annotation based transaction management configuration. 
transaction-manager attribute is used to provide the transaction manager bean name. transaction-manager default value is transactionManager but I am 
still having it to avoid confusion. proxy-target-class attribute is used to tell Spring context to use class based proxies, without it you will get 
runtime exception with message such as Exception in thread “main” org.springframework.beans.factory.BeanNotOfRequiredTypeException: Bean named 
‘customerManager’ must be of type [com.journaldev.spring.jdbc.service.CustomerManagerImpl], but was actually of type [com.sun.proxy.$Proxy6]

Since we are using JDBC, we are creating transactionManager bean of type org.springframework.jdbc.datasource.DataSourceTransactionManager. This is 
very important and we should use proper transaction manager implementation class based on our transaction API use.
 */

package com.jain;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@Component("annotation")
public class _004Annotation{

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("004annotation.xml");
		_004Annotation userDAO = (_004Annotation)context.getBean("annotation");
		// Check this. There is no proxy created. Which is correct since there is no transaction related aspect in the xml.
		System.out.println(userDAO.getClass());
		userDAO.createUser();
	}

	private JdbcTemplate jdbcTemplateObject;
	@Autowired
	public void setDataSource(DataSource dataSource) { 
		this.jdbcTemplateObject = new JdbcTemplate(dataSource); 
	}

	@Transactional
	public void createUser(){
		try{
			int count = jdbcTemplateObject.update("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)", new Object[]{"xxx","xxx"});
			System.out.println("inserting user " + count);
			count = jdbcTemplateObject.update("UPDATE USER_DETAILS SET USER_ID=? WHERE USER_ID=?", new Object[]{"yyy","xxx"});
			System.out.println("updating user " + count);
			// to simulate the exception.
			throw new RuntimeException("Error in creating user, rolling back..") ;
		} catch (DataAccessException e) {
			throw e;
		}
	}
}