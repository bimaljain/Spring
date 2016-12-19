/*
-------------------------
Spring - JDBC Operations:
-------------------------
Spring JDBC modules simplifies the use of underlying JDBC API. org.springframework.jdbc.core.JdbcOperations defines Spring way of handling database 
access using JDBC. This interface provides abstraction from the low level repetitive JDBC operations like opening the database connection, preparing 
the Jdbc statements, handling and processing exception, handling transactions, closing the connection etc.

This interface is not meant to be implemented by the application code. We already have a well known implementation class:
1.) JdbcTemplate
2.) SimpleJdbcTemplate
3.) NamedParameterJdbcTemplate

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

----------
CALLBACKS:
----------
query(String sql, ResultSetExtractor<T> rse) 
client has to iterate and extract result itself. Result object T typically is a map/collection etc. Not recommended. Used by Spring internally itself.

query(String sql, RowCallbackHandler rch) 
Client is called back on per row basis. Doesn't return anything so client has to capture result into some external object or use the extracted result 
at the spot e.g. send email or send JMS message etc. A new instance should be created every time we want to use it.

query(String sql, RowMapper<T> rowMapper)
for mapping rows to T on a per-row basis. If we need to map exactly one result object per row then this is a good choice.
 */

// query() will return multiple rows and multiple columns.

package _001;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import _002_helper.User;

public class _003_Query {
	public static void main(String[] args) throws SQLException{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("_001_Jdbc.xml");
		UserDAO_003 userDAO = (UserDAO_003)ctx.getBean("userDAO_003");
		System.out.println(userDAO.getAllUsers());
		System.out.println(userDAO.getUser());
		System.out.println(userDAO.getUser2());
		System.out.println(userDAO.getUser3());
		System.out.println(userDAO.getUser4());
		System.out.println(userDAO.getUser5());
	}
}

/*
This is at the entire resultset level
-------------------
ResultSetExtractor:
-------------------
ResultSetExtractor object is typically state-less and thus reusable. Implementations of the ResultSetExtractor typically creates one object out 
of several rows, that is subsequently returned. It is state-less because the implementing class does not preserve any state between method calls.
 */

class UserResultSetExtractor implements ResultSetExtractor<List<User>>{
	public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<User> list = new ArrayList<User>();
		while (rs.next()){
			list.add(new User(rs.getInt("ID"),rs.getString("USER_ID"),rs.getString("PASSWORD")));
		}
		return list;
	}	
}

/*
The next two callback handlers are at row level.
-------------------
RowCallbackHandler:
-------------------
Implementations of this interface perform the actual work of processing each row [...] In contrast to a ResultSetExtractor, a RowCallbackHandler 
object is typically stateful. It keeps the result state within the object, to be available for later inspection. The RowCallbackHandler is used for 
queries such as updating or deleting rows. Additionally, it is used when you need to track a state across the ResultSet, such as number of rows in 
the RowCountCallbackHandler.
 */

class UserRowCallbackHandler implements RowCallbackHandler {
    private List<User> list = new ArrayList<User>();
     
    public void processRow(ResultSet rs) throws SQLException { // void return type
        list.add(new User(rs.getInt("ID"),rs.getString("USER_ID"),rs.getString("PASSWORD")));
    }     
    public List<User> getList() {
        return list;
    }        
}

/*
----------
RowMapper:
----------
An interface used by JdbcTemplate for mapping rows of a ResultSet on a per-row basis. Implementations of this interface perform the actual work of 
mapping each row to a result object i.e. the RowMapper is commonly used to map objects when there is a one-to-one relationship between a row in the 
database and the resulting object.
 */

class UserRowMapper003 implements RowMapper<User>{
	public User mapRow(ResultSet rs, int rowNum) throws SQLException { // non-void return type
		User user = new User(rs.getInt(1),rs.getString(2),rs.getString(3));
		return user;
	}	
}

/*
---------------
Best Use cases:
---------------
ResultSetExtractor: When multiple rows of ResultSet map to a single Object. Like when doing complex joins in a query one may need to have access 
to entire ResultSet instead of single row of rs to build complex Object and you want to take full control of ResultSet. Like Mapping the rows 
returned from the join of TABLE1 and TABLE2 to an fully-reconstituted TABLE aggregate.

RowCallbackHandler: When no value is being returned from callback method for each row, e.g. writing row to a file, converting rows to a XML, 
Filtering rows before adding to collection. Very efficient as ResultSet to Object mapping is not done here.

Row Mapper: When each row of a ResultSet maps to a domain Object, can be implemented as private inner class.

 */

@Component
class UserDAO_003{	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		this.jdbcTemplate=new JdbcTemplate(dataSource); //we don't use dataSource directly anymore
	}

	public List<User> getAllUsers(){
		List<User> users = jdbcTemplate.query("SELECT * FROM USER_DETAILS", new UserRowMapper003());
		return users;
	}
	
	public List<User> getUser(){
		List<User> user = jdbcTemplate.query("SELECT * FROM USER_DETAILS WHERE ID=?", new Object[]{9}, new UserRowMapper003());
		return user;
	}	
	
	public List<User> getUser2(){
		List<User> user = jdbcTemplate.query("SELECT * FROM USER_DETAILS WHERE ID=?", new Object[]{9}, new int[]{Types.INTEGER}, 
				new UserRowMapper003());
		return user;
	}
	
	public List<User> getUser3(){
		List<User> user = jdbcTemplate.query("SELECT * FROM USER_DETAILS WHERE ID=? and USER_ID=? ", new UserRowMapper003(), 9, "bharat");
		return user;
	}
	
	public List<User> getUser4(){
		List<User> users = jdbcTemplate.query("SELECT * FROM USER_DETAILS", new UserResultSetExtractor());
		return users;
	}
	
	public List<User> getUser5(){
		UserRowCallbackHandler rowHandler = new UserRowCallbackHandler();
		jdbcTemplate.query("SELECT * FROM USER_DETAILS", rowHandler);
		return rowHandler.getList();
	}
}
