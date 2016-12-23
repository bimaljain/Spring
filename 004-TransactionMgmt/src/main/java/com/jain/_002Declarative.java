/*
with CGLIB proxy. target object has no interface
 */

package com.jain;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

//@Component("declarative002")
public class _002Declarative{

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("002declarative.xml");
		_002Declarative userDAO = (_002Declarative)context.getBean("declarative002");
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
			count = jdbcTemplateObject.update("UPDATE USER_DETAILS SET USER_ID=? WHERE USER_ID=?", new Object[]{"zzz","yyy"});
			System.out.println("updating user " + count);
			// to simulate the exception.
			throw new RuntimeException("Error in creating user, rolling back..") ;
		} catch (DataAccessException e) {
			throw e;
		}
	}
}