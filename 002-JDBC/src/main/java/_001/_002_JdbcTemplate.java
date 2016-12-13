/*
In the last example, you have to create many redundant codes (create connection , close connection , handle exception) in all the DAO database 
operation methods – insert, update and delete. It just not efficient, ugly, error prone and tedious.

In Spring JDBC development, you can use JdbcTemplate and JdbcDaoSupport classes to simplify the overall database operation processes.
 */

package _001;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

public class _002_JdbcTemplate {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_002_JdbcTemplate.xml");
		UserDAO_002 userDAO = (UserDAO_002)ctx.getBean("userDAO_002");
		userDAO.createUser();
		System.out.println(userDAO.getUser());
		System.out.println(userDAO.getAllUsers());
		userDAO.updateUser(9, "bharat");
	}
}

@Component
class UserDAO_002{	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate=new JdbcTemplate(dataSource); //we don't use dataSource directly anymore
	}
	
	public void createUser(){
		//check the difference. just one line of code.
		jdbcTemplate.update("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)", new Object[]{"meghna","rajo"});
	}
	
	public User_002 getUser(){
		User_002 user = jdbcTemplate.queryForObject("SELECT * FROM USER_DETAILS WHERE ID=?", new Object[]{9}, 
				new RowMapper<User_002>(){
				public User_002 mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_002 user = new User_002(rs.getInt(1),rs.getString(2),rs.getString(3));
					return user;
				}			
			});
		return user;
	}	
	
	public List<User_002> getAllUsers(){
		List<User_002> users = jdbcTemplate.query("SELECT * FROM USER_DETAILS",
				new RowMapper<User_002>(){
				public User_002 mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_002 user = new User_002(rs.getInt(1),rs.getString(2),rs.getString(3));
					return user;
				}			
			});
		return users;
	}
	
	public void updateUser(int id, String userId){
		int i = jdbcTemplate.update("UPDATE USER_DETAILS SET USER_ID=? WHERE ID=?", new Object[]{userId,id});	
		i= jdbcTemplate.update("DELETE FROM USER_DETAILS WHERE ID=?", new Object[]{10});
	}

}

class User_002{
	private int id;
	private String name;
	private String password;
	
	public User_002(int id, String name, String password) {
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

