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
import org.springframework.stereotype.Component;

import _002_helper.User;

public class _019_JdbcDaoSupport {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_Jdbc.xml");
		UserDAO_019 userDAO = (UserDAO_019)ctx.getBean("userDAO_019");
		System.out.println(userDAO.getUser());
	}
}

@Component
class UserDAO_019 extends JdbcDaoSupport{
	public User getUser(){
		User user = getJdbcTemplate().queryForObject("SELECT * FROM USER_DETAILS WHERE ID=?", new Object[]{9}, 
				new RowMapper<User>(){
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User user = new User(rs.getInt(1),rs.getString(2),rs.getString(3));
					return user;
				}			
			});
		return user;
	}
}

