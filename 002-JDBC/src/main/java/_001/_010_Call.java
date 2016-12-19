// Spring provides various ways of abstractions on JDBC to call database stored procedures.

package _001;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.object.GenericStoredProcedure;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

public class _010_Call {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_Jdbc.xml");
		UserDAO_010 userDAO = (UserDAO_010)ctx.getBean("userDAO_010");
		System.out.println(userDAO.getUser1());
		System.out.println(userDAO.getUser2());
		System.out.println(userDAO.getUser3());
		System.out.println(userDAO.getUser4());
	}
}

@Component
class UserDAO_010{	
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcCall simpleJdbcTemplate;
	private StoredProcedure procedure = new GenericStoredProcedure();

	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.simpleJdbcTemplate=new SimpleJdbcCall(dataSource);
		procedure.setDataSource(dataSource);
	}
	
	/*
	 CREATE PROCEDURE getUserByUserid (IN userid VARCHAR(20))
		BEGIN
		  SELECT * FROM USER_DETAILS where USER_ID=userid;
		END
	 */
	// Using JdbcTemplate#call(CallableStatementCreator csc, List<SqlParameter> inOutParams) 
	public Map<String, Object> getUser1(){		
		
		return jdbcTemplate.call(new CallableStatementCreator() {			
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
	            CallableStatement callableStatement = con.prepareCall("{call getUserByUserid (?)}");
	            callableStatement.setString(1, "bimal");
	            return callableStatement;
			}},
				Arrays.asList(
						new SqlParameter("userid",Types.VARCHAR)
			));
	}
	
	/*
	 CREATE PROCEDURE getUserCount (IN userid VARCHAR(20), OUT result INTEGER )
		BEGIN
		  SELECT count(*) into result FROM USER_DETAILS where USER_ID=userid;
		END
	 */
	// Using JdbcTemplate#call(CallableStatementCreator csc, List<SqlParameter> inOutParams) with output params
	public Map<String, Object> getUser2(){
		
		return jdbcTemplate.call(new CallableStatementCreator() {			
			public CallableStatement createCallableStatement(Connection con) throws SQLException {
	            CallableStatement callableStatement = con.prepareCall("{call getUserCount (?,?)}");
	            callableStatement.setString(1, "bimal");
	            callableStatement.registerOutParameter(2, Types.INTEGER);
	            return callableStatement;
			}}, 
				Arrays.asList(
			            new SqlParameter("userid",Types.VARCHAR),
			            new SqlOutParameter("result", Types.INTEGER)
			));
	}

	// Using SimpleJdbcCall (This class simplifies greatly the code needed to access stored procedures/functions) 
	public Map<String, Object> getUser3(){
		SimpleJdbcCall call = simpleJdbcTemplate
				.withProcedureName("getUserByUserid")
				.declareParameters(new SqlParameter("userid", Types.VARCHAR));
		return call.execute(new MapSqlParameterSource("userid", "bimal")); //mapping name/value pair
	}
	
	// Using StoredProcedure. This class is in package org.springframework.jdbc.object which allows us to access the database in a more 
	// object-oriented manner. SotredProcedure is abstract so we usually have to extend that. Here we are using a subclass, GenericStoredProcedure  
	public Map<String, Object> getUser4(){
		SqlParameter[] parameters = {
	            new SqlParameter("userid", Types.VARCHAR),
	            new SqlOutParameter("result", Types.INTEGER)
				};
		
		procedure.setSql("getUserCount");
		procedure.setParameters(parameters);
		
		Map<String, Object> result = procedure.execute("bimal");
		return result;
	}
}
