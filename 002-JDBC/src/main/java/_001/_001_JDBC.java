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

import _002_helper.User;

public class _001_JDBC {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_Jdbc.xml");
		UserDAO_001 userDAO = (UserDAO_001)ctx.getBean("userDAO_001");
		System.out.println("# of users created: " + userDAO.createUser());
		System.out.println(userDAO.getUser());
	}
}

@Component
class UserDAO_001{
	@Autowired
	private DataSource dataSource;
	
	public int createUser() throws SQLException{
		Connection conn = dataSource.getConnection();
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)");
		stmt.setString(1, "bimal");
		stmt.setString(2, "password");
		int count = stmt.executeUpdate();
		stmt.close();
		conn.close();		
		return count;
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
