/*
with JDK proxy
 */
package com.jain;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

//@Component("declarative001")
public class _001Declarative implements IUserDAO{

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("001declarative.xml");
		IUserDAO userDAO = (IUserDAO)context.getBean("declarative001");
		System.out.println(userDAO.getClass());
		userDAO.createUser();
	}
	
	private JdbcTemplate jdbcTemplateObject;
	@Autowired
	public void setDataSource(DataSource dataSource) { 
		this.jdbcTemplateObject = new JdbcTemplate(dataSource); }

	public void createUser(){
		try {
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

interface IUserDAO {
	public void createUser();
}