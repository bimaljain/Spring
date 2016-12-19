/*
------------------------------------
Spring - NamedParameterJdbcTemplate:
------------------------------------
Functionally, there's no difference between Spring's JdbcTemplate and it's variant, NamedParameterJdbcTemplate except for : NamedParameterJdbcTemplate 
provides a better approach for assigning sql dynamic parameters instead of using multiple '?' in the statement.

Followings are the key points to understand the use of NamedParameterJdbcTemplate:

It provides a way of specifying Named Parameters placeholders starting with ':' (colon). For example :firstName is the named placeholder in this 
query:  "select * from PERSON where FIRST_NAME = :firstName" 
It delegates all JDBC low level functionality to the wrapped JdbcTemplate
To bind the named placeholders to the real values into the sql query, we can use java.util.Map or we have a better option, that is to use an 
implementation of the interface, SqlParameterSource. Here are the commonly used implementations provided by Spring:
MapSqlParameterSource: It wraps java.util.Map and provides convenience method chaining for adding multiple param values.
BeanPropertySqlParameterSource: It obtains parameter values from our domain/pojo/entity object, given that the object has proper getters and setters 
(Per JavaBean specifications). Also in our sql query placeholder names should be same as our object variable names. It is the fastest way to bind
values. We just have to pass our entity object to it's constructor.

 */

package _001;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import _002_helper.User;

public class _012_NamedParameterJdbcTemplate {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_Jdbc.xml");
		UserDAO_011 userDAO = (UserDAO_011)ctx.getBean("userDAO_011");
		userDAO.createUser1();
		userDAO.createUser2();
		System.out.println(userDAO.getUser3());
	}
}

class UserRowMapper011 implements RowMapper<User>{
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User(rs.getInt(1),rs.getString(2),rs.getString(3));
		return user;
	}	
}

@Component
class UserDAO_011{	
	private NamedParameterJdbcTemplate npJdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource){
		this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public void createUser1(){
		int count = npJdbcTemplate.update("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(:userId, :password)", 
				new BeanPropertySqlParameterSource(new User("bimal","bakliwal")));
		System.out.println("# of user created: " + count);
	}	
	
	public void createUser2(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", "bimal");
		map.put("password", "bakliwal");
		int count = npJdbcTemplate.update("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(:userId, :password)", map);
		System.out.println("# of user created: " + count);
	}
	
	public List<User> getUser3(){
		List<User> userList = npJdbcTemplate.query("SELECT * FROM USER_DETAILS WHERE ID=:id and USER_ID=:userId", 
				new MapSqlParameterSource().addValue("id",2).addValue("userId", "bimal"),
				new UserRowMapper011());
		return userList;
	}
}

