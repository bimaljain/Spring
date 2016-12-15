/*
By extended the JdbcDaoSupport, set the datasource and JdbcTemplate in your class is no longer required, you just need to inject the correct 
datasource into JdbcCustomerDAO. And you can get the JdbcTemplate by using a getJdbcTemplate() method.

JdbcDaoSupport has:
public final void setDataSource(DataSource dataSource) { ... }

which is inherited by your DAO class. We inject DataSource using setter DI.
 */

package _001;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class _009_JdbcDaoSupport {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_009_JdbcTemplate.xml");
		UserDAO_009 userDAO = (UserDAO_009)ctx.getBean("userDAO_009");
		System.out.println(userDAO.getUser());
	}
}

class UserDAO_009 extends JdbcDaoSupport{	

	public User_009 getUser(){
		User_009 user = getJdbcTemplate().queryForObject("SELECT * FROM USER_DETAILS WHERE ID=?", new Object[]{9}, 
				new RowMapper<User_009>(){
				public User_009 mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_009 user = new User_009(rs.getInt(1),rs.getString(2),rs.getString(3));
					return user;
				}			
			});
		return user;
	}
}

class User_009{
	private int id;
	private String name;
	private String password;
	
	public User_009(int id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String toString(){
		return id + ": " + name + ": " + password;
	}
}

