/*
JdbcTemplate is the classic Spring JDBC approach and the most popular. This "lowest level" approach and all others use a JdbcTemplate under the covers.
Note :all others use a JdbcTemplate under the covers

SimpleJdbcInsert optimize database metadata to limit the amount of necessary configuration. This approach simplifies coding so that you only need to 
provide the name of the table or procedure and provide a map of parameters matching the column names. This only works if the database provides 
adequate metadata. If the database doesn’t provide this metadata, you will have to provide explicit configuration of the parameters.
If you are going to use A SimpleJdbcInsert,then also the actual insert is being handled using JdbcTemplate. So definitely in terms of performance 
SimpleJdbcInsert can not be better that JdbcTemplate.

So performance wise you can not be beneficial using SimpleJdbcInsert.

But perform insert operations in to multiple tables by using SimpleJdbcInsert is having definitely better capability then JdbcTemplate class.There 
may be some situation in which you want to insert data in lot of tables and you may like to do less coding.In these situations, using of 
SimpleJdbcInsert can be a very good option.
 */

package _001;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

public class _011_SimpleJdbcInsert {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_Jdbc.xml");
		UserDAO_012 userDAO = (UserDAO_012)ctx.getBean("userDAO_012");
		userDAO.createUser1();
		userDAO.createUser2();
	}
}

@Component
class UserDAO_012{	
	private SimpleJdbcInsert jdbcInsert;
	private SimpleJdbcInsert jdbcInsert2;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcInsert = new SimpleJdbcInsert(dataSource);
		this.jdbcInsert2 = new SimpleJdbcInsert(dataSource);											
	}
	
	public void createUser1(){
		Map<String,String> params = new HashMap<String,String>(3);
		params.put("USER_ID", "bimal");
		params.put("PASSWORD", "bakliwal");
		
		jdbcInsert.withTableName("USER_DETAILS")
					.execute(params);
	}	
	
	public void createUser2(){
		Map<String,String> params = new HashMap<String,String>(2);
		params.put("USER_ID", "bimal");
		params.put("PASSWORD", "password");
		
		jdbcInsert2.withTableName("USER_DETAILS")
					.usingColumns("USER_ID","PASSWORD")
					.execute(params);
	}
}

