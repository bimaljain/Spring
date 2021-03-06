package _001;

import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

public class _007_Update {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_Jdbc.xml");
		UserDAO_007 userDAO = (UserDAO_007)ctx.getBean("userDAO_007");
		userDAO.getUser1();
		userDAO.getUser2();
		userDAO.getUser3();
	}
}

@Component
class UserDAO_007{	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate=new JdbcTemplate(dataSource);
	}

	// update(String sql) 
	public void getUser1(){
		int count = jdbcTemplate.update("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(\"meghna\",\"rajo\")");
		jdbcTemplate.update("UPDATE USER_DETAILS SET USER_ID=\"motto\" WHERE ID=\"rajo\"");	
		jdbcTemplate.update("DELETE FROM USER_DETAILS WHERE ID=11");		
	}
	
	// update(String sql, Object... args)
	public void getUser2(){
		jdbcTemplate.update("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)", new Object[]{"meghna","rajo"});
		jdbcTemplate.update("UPDATE USER_DETAILS SET USER_ID=? WHERE ID=?", new Object[]{"motto","rajo"});	
		jdbcTemplate.update("DELETE FROM USER_DETAILS WHERE ID=?", new Object[]{10});
	}
	
	// update(String sql, Object[] args, int[] argTypes) 
	public void getUser3(){
		jdbcTemplate.update("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)", new Object[]{"meghna","rajo"}, new int[]{Types.VARCHAR,Types.VARCHAR});
		jdbcTemplate.update("UPDATE USER_DETAILS SET USER_ID=? WHERE ID=?", new Object[]{"motto","rajo"}, new int[]{Types.VARCHAR,Types.VARCHAR});
		jdbcTemplate.update("DELETE FROM USER_DETAILS WHERE ID=?", new Object[]{12}, new int[]{Types.INTEGER});
	}
}
