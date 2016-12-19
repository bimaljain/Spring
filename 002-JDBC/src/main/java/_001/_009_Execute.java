package _001;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

public class _009_Execute {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_Jdbc.xml");
		UserDAO_009 userDAO = (UserDAO_009)ctx.getBean("userDAO_009");
		userDAO.getUser1();
		System.out.println(userDAO.getUser2());
		System.out.println(userDAO.getUser3());
	}
}

@Component
class UserDAO_009{	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}

	public void getUser1(){
		jdbcTemplate.execute("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(\"bimal\",\"pandya\")");
	}
	
	/*
	 PreparedStatementCallback interface: It processes the input parameters and output results. 
	 */	
	public boolean getUser2(){
		return jdbcTemplate.execute("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)", 
				new PreparedStatementCallback<Boolean>(){  
			public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1,"bimal");  
				ps.setString(2,"bakliwal");  		              
				return ps.execute(); 
			}});
	}
	
	public Integer getUser3(){
		return jdbcTemplate.execute("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)", 
				new PreparedStatementCallback<Integer>(){  
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ps.setString(1,"bimal");  
				ps.setString(2,"bakliwal");  		              
				return ps.executeUpdate(); 
			}});
	}
}
