/*
queryForMap() returns one row with multiple columns
 */

package _001;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

public class _006_QueryForMap {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_Jdbc.xml");
		UserDAO_006 userDAO = (UserDAO_006)ctx.getBean("userDAO_006");
		System.out.println(userDAO.getUser1());
		System.out.println(userDAO.getUser2());
		System.out.println(userDAO.getUser3());
	}
}

@Component
class UserDAO_006{	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}
	
	public Map<String, Object> getUser1(){
		Map<String, Object> userName = jdbcTemplate.queryForMap("SELECT * FROM USER_DETAILS WHERE ID=9");
		return userName;
	}
	
	public Map<String, Object> getUser2(){
		Map<String, Object> userName = jdbcTemplate.queryForMap("SELECT * FROM USER_DETAILS where ID=?", 9);
		return userName;
	}
	
	public Map<String, Object> getUser3(){
		Map<String, Object> userName = jdbcTemplate.queryForMap("SELECT * FROM USER_DETAILS WHERE USER_ID=?", new Object[]{9}, 
				new int[Types.INTEGER]);
		return userName;
	}
}

