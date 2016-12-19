// queryForObject() will always return one row. If required type is supplied, then you get only one column; otherwise all the columns. 

package _001;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import _002_helper.User;

public class _004_QueryForObject {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_Jdbc.xml");
		UserDAO_004 userDAO = (UserDAO_004)ctx.getBean("userDAO_004");
		System.out.println(userDAO.getUser1());
		System.out.println(userDAO.getUser2());
		System.out.println(userDAO.getUser3());
		System.out.println(userDAO.getUser4());
		System.out.println(userDAO.getUser5());
		System.out.println(userDAO.getUser6());
		System.out.println(userDAO.getUser7());
		System.out.println(userDAO.getUser8());
	}
}

class UserRowMapper implements RowMapper<User>{
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User(rs.getInt(1),rs.getString(2),rs.getString(3));
		return user;
	}	
}

@Component
class UserDAO_004{	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}
	
	public String getUser1(){
		String userName = jdbcTemplate.queryForObject("SELECT USER_ID FROM USER_DETAILS WHERE ID=9", String.class);
		return userName;
	}
	
	public String getUser2(){
		String userName = jdbcTemplate.queryForObject("SELECT USER_ID FROM USER_DETAILS WHERE ID=?", String.class, 9);
		return userName;
	}
	
	public String getUser3(){
		String userName = jdbcTemplate.queryForObject("SELECT USER_ID FROM USER_DETAILS WHERE ID=?", new Object[]{9}, String.class);
		return userName;
	}
	
	public String getUser4(){
		String userName = jdbcTemplate.queryForObject("SELECT USER_ID FROM USER_DETAILS WHERE ID=?", new Object[]{9}, new int[]{Types.INTEGER}, 
				String.class);
		return userName;
	}
	
	public User getUser5(){
		User user = jdbcTemplate.queryForObject("SELECT * FROM USER_DETAILS WHERE ID=9", new UserRowMapper());
		return user;
	}
	
	public User getUser6(){
		User user = jdbcTemplate.queryForObject("SELECT * FROM USER_DETAILS WHERE ID=?", new Object[]{9}, new UserRowMapper());
		return user;
	}
	
	public User getUser7(){
		User user = jdbcTemplate.queryForObject("SELECT * FROM USER_DETAILS WHERE ID=? and USER_ID=?", new UserRowMapper(), 101, "bimal");
		return user;
	}
	
	public User getUser8(){
		User user = jdbcTemplate.queryForObject("SELECT * FROM USER_DETAILS WHERE ID=?", new Object[]{9}, new int[]{Types.INTEGER}, 
				new UserRowMapper());
		return user;
	}	
}
