/*
This example is when target object does not implements any interface

Here we are casting the proxy to the Impl and not to the interface, as in the previous example.
So we cannot create JDK proxy here and only option is to create CGLIB proxy. 
So we can only have: <aop:config proxy-target-class="true"> and we must set this, else get an exception
*/

package com.jain;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class _002_Xml {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_002.xml");
		User user = (User)ctx.getBean("user");
		System.out.println(user.getClass());
		user.createUser();
		user.updateUser();
	}
}	