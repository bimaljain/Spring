/*
query() will always return multiple rows and multiple columns.
 */

package _001;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Component;

public class _008_BatchUpdate {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_002_JdbcTemplate.xml");
		UserDAO_008 userDAO = (UserDAO_008)ctx.getBean("userDAO_008");
		userDAO.getUser1();
		userDAO.getUser2();
		userDAO.getUser3();
		userDAO.getUser4();
		userDAO.getUser5();
	}
}

@Component
class UserDAO_008{	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate=new JdbcTemplate(dataSource); //we don't use dataSource directly anymore
	}

	public void getUser1(){
		jdbcTemplate.batchUpdate("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(\"meghna\",\"pandya\")",
								 "INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(\"meghna\",\"bakliwal\")");
	}
	
	public void getUser2(){
		jdbcTemplate.batchUpdate("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)",
				Arrays.asList(new Object[]{"meghna","jain"}, new Object[]{"meghna","pandya"}));
	}
	
	public void getUser3(){
		jdbcTemplate.batchUpdate("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)",
				Arrays.asList(new Object[]{"meghna","jain"}, new Object[]{"meghna","pandya"}),
				new int[]{Types.VARCHAR, Types.VARCHAR});
	}
	
	public void getUser4(){
		final List<User_008> user = Arrays.asList(new User_008(1,"Alex","alex"), new User_008(2,"Adam","adam"));
		jdbcTemplate.batchUpdate("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)",
				new BatchPreparedStatementSetter() {					
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, user.get(i).getName());
						ps.setString(2, user.get(i).getPassword());						
					}
					
					public int getBatchSize() {
						return user.size();
					}
				});
	}
	
	public void getUser5(){
		final List<User_008> users = Arrays.asList(new User_008(1,"Alex2","alex"), new User_008(2,"Adam2","adam"));
		jdbcTemplate.batchUpdate("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)", users, 1,
				new ParameterizedPreparedStatementSetter<User_008>() {					
					public void setValues(PreparedStatement ps, User_008 user) throws SQLException {
						ps.setString(1, user.getName());
						ps.setString(2, user.getPassword());						
					}
				});
	}
}

class User_008{
	private int id;
	private String name;
	private String password;
	
	public User_008(int id, String name, String password) {
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


