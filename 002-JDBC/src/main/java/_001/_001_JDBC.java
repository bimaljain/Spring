package _001;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

public class _001_JDBC {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_JDBC.xml");
		UserDAO userDAO = (UserDAO)ctx.getBean("userDAO");
		userDAO.createUser();
		User user = userDAO.getUser();
		System.out.println(user);
	}
}


//@Component
class UserDAO{
	@Autowired
	private DataSource dataSource;
	
	public void createUser() throws SQLException{
		Connection conn = dataSource.getConnection();
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)");
		stmt.setString(1, "bimal");
		stmt.setString(2, "password");
		stmt.executeUpdate();
		stmt.close();
		conn.close();		
	}
	
	public User getUser() throws SQLException{
		User user=null;
		Connection conn = dataSource.getConnection();
		PreparedStatement stmt = conn.prepareStatement("SELECT * FROM USER_DETAILS WHERE ID=?");
		stmt.setInt(1, 1);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()){
			user = new User(rs.getInt(1),rs.getString(2),rs.getString(3));
		}
		stmt.close();
		conn.close();
		return user;
	}
	
}

class User{
	private int id;
	private String name;
	private String password;
	
	public User(int id, String name, String password) {
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


