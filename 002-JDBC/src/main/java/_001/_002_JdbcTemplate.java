/*
In the last example, you have to create many redundant codes (create connection , close connection , handle exception) in all the DAO database 
operation methods � insert, update and delete. It just not efficient, ugly, error prone and tedious.

-------------------------
Spring - JDBC Operations:
-------------------------
Spring JDBC modules simplifies the use of underlying JDBC API. org.springframework.jdbc.core.JdbcOperations defines Spring way of handling database 
access using JDBC. This interface provides abstraction from the low level repetitive JDBC operations like opening the database connection, preparing 
the Jdbc statements, handling and processing exception, handling transactions, closing the connection etc.

This interface is not meant to be implemented by the application code. We already have a well known implementation class, JdbcTemplate

Here we are going to give a quick overview of the methods defined in JdbcOperations interface.

1.) query(String sql, RowMapper<T> rowMapper): 
	RowMapper<T>{T mapRow(ResultSet rs, int rowNum)} for mapping rows to T on a per-row basis. If we need to map exactly one result object per row 
	then this is a good choice.

2.) query(String sql, Object[] args, RowMapper<T> rowMapper):
	Where args are value binding for '?' placeholder.
	
3.) query(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper):
	Where argTypes are sql type (java.sql.Types). The methods without this parameter, figure out the corresponding SQL type themselves.

4.) query(String sql, RowMapper<T> rowMapper, Object... args):
	Where args are same as above i.e. values for '?' placeholders. The difference is, these overloaded methods have Java Varargs as parameter.

5.) queryForABC(....):
	Where ABC can be one of the followings:
	List : We expect the query result will be of multiple rows. It has many overloaded versions. If there are multiple column fields with each 
	fetched rows, then we should use the ones return List of Maps. If there's only one field with fetched rows, then we should use the ones return 
	List<T>, where T is the corresponding java type for the column type.
	
	Map : If query is setup to return only one row with multiple columns, then we should use this one.
	
	Object : If query is going to return only one row and one field, then we can use this. The object type should be compatible with database 
	column type. The overloaded methods with parameter RowObject is used if there are multiple columns but we want to map them to our custom 
	java object T.
	
	RowSet : return the disconnected Spring's SqlRowSet. It's a mirror interface for javax.sql.RowSet

6.) update(....):
	All overloaded update methods are used to perform 'insert' or 'update' or 'delete' statements. The use of different combination of parameters are 
	same as above methods, expect for the one with KeyHolder. It is used to capture auto-generated keys, typically resulted in an 'insert' statement.

7.) batchUpdate(....):
	Issue multiple SQL updates on a single JDBC Statement using batching. Followings are two new parameters which belong to different overloaded 
	batchUpdate methods:
	BatchPreparedStatementSetter{
	     void setValues(PreparedStatement ps, int i);
	     int getBatchSize();
	            }
	It's similar to PreparedStatementSetter described up, except it is called multiple times for each number of updates in the batch. It has extra 
	int i parameter too, which is the 0 based index of the update iteration. Useful for setting params from user collection to the statement.
	
	ParameterizedPreparedStatementSetter<T>{
	     void setValues(PreparedStatement ps, T argument);
	                    }
	Better than the last one as it passes the real user object to set the parameter to the statement. The corresponding batchUpdate accepts the 
	collection of user object.
	
8.) execute(....):
	It can be used to execute any arbitrary SQL but often used for DDL statements like creating/alerting/dropping tables etc.
	
9.) call(....):
	Calls database stored procedures or functions.

In Spring JDBC development, you can use JdbcTemplate and JdbcDaoSupport classes to simplify the overall database operation processes.
 */

package _001;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

public class _002_JdbcTemplate {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_002_JdbcTemplate.xml");
		UserDAO_002 userDAO = (UserDAO_002)ctx.getBean("userDAO_002");
		userDAO.createUser();
		System.out.println(userDAO.getUser());
		System.out.println(userDAO.getAllUsers());
		userDAO.updateUser(9, "bharat");
	}
}

@Component
class UserDAO_002{	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate=new JdbcTemplate(dataSource); //we don't use dataSource directly anymore
	}
	
	public void createUser(){
		//check the difference. just one line of code.
		jdbcTemplate.update("INSERT INTO USER_DETAILS (USER_ID,PASSWORD) VALUES(?,?)", new Object[]{"meghna","rajo"});
	}
	
	public User_002 getUser(){
		User_002 user = jdbcTemplate.queryForObject("SELECT * FROM USER_DETAILS WHERE ID=?", new Object[]{9}, 
				new RowMapper<User_002>(){
				public User_002 mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_002 user = new User_002(rs.getInt(1),rs.getString(2),rs.getString(3));
					return user;
				}			
			});
		return user;
	}	
	
	public List<User_002> getAllUsers(){
		List<User_002> users = jdbcTemplate.query("SELECT * FROM USER_DETAILS",
				new RowMapper<User_002>(){
				public User_002 mapRow(ResultSet rs, int rowNum) throws SQLException {
					User_002 user = new User_002(rs.getInt(1),rs.getString(2),rs.getString(3));
					return user;
				}			
			});
		return users;
	}
	
	public void updateUser(int id, String userId){
		int i = jdbcTemplate.update("UPDATE USER_DETAILS SET USER_ID=? WHERE ID=?", new Object[]{userId,id});	
		i= jdbcTemplate.update("DELETE FROM USER_DETAILS WHERE ID=?", new Object[]{10});
	}

}

class User_002{
	private int id;
	private String name;
	private String password;
	
	public User_002(int id, String name, String password) {
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

