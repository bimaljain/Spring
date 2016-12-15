package _001;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

public class _003_Query {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_002_JdbcTemplate.xml");
		UserDAO_003 userDAO = (UserDAO_003)ctx.getBean("userDAO_003");
		System.out.println(userDAO.getAllUsers());
		System.out.println(userDAO.getUser());
		System.out.println(userDAO.getUser2());
		System.out.println(userDAO.getUser3());
	}
}

//@Component
class UserDAO_003{	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate=new JdbcTemplate(dataSource); //we don't use dataSource directly anymore
	}
	
	public List<User_003> getAllUsers(){
		List<User_003> users = jdbcTemplate.query("SELECT * FROM USER_DETAILS",
				new RowMapper<User_003>(){
				public User_003 mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_003 user = new User_003(rs.getInt(1),rs.getString(2),rs.getString(3));
					return user;
				}			
			});
		return users;
	}
	
	public List<User_003> getUser(){
		List<User_003> user = jdbcTemplate.query("SELECT * FROM USER_DETAILS WHERE ID=?", new Object[]{9}, 
				new RowMapper<User_003>(){
				public User_003 mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_003 user = new User_003(rs.getInt(1),rs.getString(2),rs.getString(3));
					return user;
				}			
			});
		return user;
	}	
	
	public List<User_003> getUser2(){
		List<User_003> user = jdbcTemplate.query("SELECT * FROM USER_DETAILS WHERE ID=?", new Object[]{9}, new int[]{Types.INTEGER}, 
				new RowMapper<User_003>(){
				public User_003 mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_003 user = new User_003(rs.getInt(1),rs.getString(2),rs.getString(3));
					return user;
				}			
			});
		return user;
	}
	
	public List<User_003> getUser3(){
		List<User_003> user = jdbcTemplate.query("SELECT * FROM USER_DETAILS WHERE ID=? and USER_ID=? ", 
				new RowMapper<User_003>(){
				public User_003 mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_003 user = new User_003(rs.getInt(1),rs.getString(2),rs.getString(3));
					return user;
				}			
			}, 9, "bharat");
		return user;
	}
}

class User_003{
	private int id;
	private String name;
	private String password;
	
	public User_003(int id, String name, String password) {
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

