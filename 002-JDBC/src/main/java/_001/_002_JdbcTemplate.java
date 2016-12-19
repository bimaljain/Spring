/*
In the last example, you have to create many redundant codes (create connection , close connection , handle exception) in all the DAO database 
operation methods, insert, update and delete. It just not efficient, ugly, error prone and tedious.
In Spring JDBC development, you can use JdbcTemplate class to simplify the overall database operation processes.
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

import _002_helper.User;

public class _002_JdbcTemplate {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_Jdbc.xml");
		UserDAO_002 userDAO = (UserDAO_002)ctx.getBean("userDAO_002");
		System.out.println("# of users created: " + userDAO.createUser());
		System.out.println(userDAO.getUser());
		System.out.println("# of users updated: " + userDAO.updateUser(9, "bharat"));
	}
}

@Component
class UserDAO_002{	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate=new JdbcTemplate(dataSource); //we don't use dataSource directly anymore
	}
	
	public int createUser(){
		//check the difference. just one line of code.
		int count =  jdbcTemplate.update("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)", new Object[]{"meghna","rajo"});
		return count;
	}
	
	public List<User> getUser(){
		List<User> users = jdbcTemplate.query("SELECT * FROM USER_DETAILS",
				new RowMapper<User>(){
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User user = new User(rs.getInt(1),rs.getString(2),rs.getString(3));
					return user;
				}			
			});
		return users;
	}
	
	public int updateUser(int id, String userId){
		int count = jdbcTemplate.update("UPDATE USER_DETAILS SET USER_ID=? WHERE ID=?", new Object[]{userId,id});
		jdbcTemplate.update("DELETE FROM USER_DETAILS WHERE ID=?", new Object[]{10});
		return count;		
	}
}

