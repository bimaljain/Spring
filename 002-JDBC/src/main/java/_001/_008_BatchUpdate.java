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

import _002_helper.User;

public class _008_BatchUpdate {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_Jdbc.xml");
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
		this.jdbcTemplate=new JdbcTemplate(dataSource);
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
	
	//batchUpdate(String sql, BatchPreparedStatementSetter pss) 
	public void getUser4(){
		final List<User> user = Arrays.asList(new User(1,"Alex","alex"), new User(2,"Adam","adam"));
		
		jdbcTemplate.batchUpdate("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)",
				new BatchPreparedStatementSetter() {			
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, user.get(i).getUserId()); //i starts from 0 until getBatchSize()
						ps.setString(2, user.get(i).getPassword());						
					}					
					public int getBatchSize() {
						return user.size();
					}
				});
	}
	
	//batchUpdate(String sql, Collection<T> batchArgs, int batchSize, ParameterizedPreparedStatementSetter<T> pss)
	//This methods breaks the batch updates into several smaller batches specified by batchSize. 
	public void getUser5(){
		final List<User> users = Arrays.asList(new User(1,"Alex2","alex"), new User(2,"Adam2","adam"));
		
		jdbcTemplate.batchUpdate("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)", users, 1, //1 is batch size
				new ParameterizedPreparedStatementSetter<User>() {					
					public void setValues(PreparedStatement ps, User user) throws SQLException {
						ps.setString(1, user.getUserId());
						ps.setString(2, user.getPassword());						
					}
				});
	}
}
