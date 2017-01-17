/*
------
TASKS:
------
1. Basic authentication of Rest API using xml configuration.
2. Different ways to autheticate user credentials: in-memory, jdbc based, custom UserDetailsService
3. Different ways to create datasource: JDBC driver, JNDI (2 ways)

---------------------
Basic Authentication:
---------------------
Traditional authentication approaches like login pages or session identification are good for web based clients involving human interaction but does 
not really fit well when communicating with [REST] clients which may not even be a web application. Think of an API over a server which tries to 
communicate with another API on a totally different server, without any human intervention.

Basic Authentication provides a solution for this problem, although not very secure. With Basic Authentication, clients send it's Base64 encoded 
credentials with each request, using HTTP [Authorization] header . That means each request is independent of other request and server may/does not 
maintain any state information for the client, which is good for scalability point of view.

Authorization : Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0...

This header will be sent with each request. Since Credentials [Base 64 encoded, not even encrypted] are sent with each request, they can be compromised. 
One way to prevent this is using HTTPS in conjunction with Basic Authentication.

-----------
DATASOURCE:
-----------
There are two ways through which we can JNDI (Java Naming and Directory Interface) lookup and wire it to the Controller DataSource.
1. Using jee namespace tag to perform the JNDI lookup and configure it as a Spring Bean. We also need to include jee namespace and schema definition in 
this case.
2. Creating a bean of type org.springframework.jndi.JndiObjectFactoryBean by passing the JNDI context name. jndiName is a required parameter for this 
configuration.

-------------------------------------
Tomcat DataSource JNDI Configuration:
-------------------------------------
1. Add below configuration in the GlobalNamingResources section of the server.xml file:

	<Resource name="jdbc/TestDB" 
	      global="jdbc/TestDB" 
	      auth="Container" 
	      type="javax.sql.DataSource" 
	      driverClassName="com.mysql.jdbc.Driver" 
	      url="jdbc:mysql://localhost:3306/TestDB" 
	      username="pankaj" 
	      password="pankaj123" 
	      
	      maxActive="100" 
	      maxIdle="20" 
	      minIdle="5" 
	      maxWait="10000"/>

2. We also need to create the Resource Link to use the JNDI configuration in our application, best way to add it in the server context.xml file.

	<ResourceLink name="jdbc/MyLocalDB"
	                	global="jdbc/TestDB"
	                    auth="Container"
	                    type="javax.sql.DataSource" />
	Notice that ResourceLink name should be matching with the JNDI context name we are using in our application. 

3. Make sure MySQL jar is present in the tomcat lib directory, otherwise tomcat will not be able to create the MySQL database connection pool.

--------------
java:comp/env:
--------------
java:comp/env is the node in the JNDI tree where you can find properties for the current Java EE component (a webapp, or an EJB).

Context envContext = (Context)initContext.lookup("java:comp/env");
allows defining a variable pointing directly to this node. It allows doing

DataSource ds = (DataSource) envContext.lookup("jdbc/dataSource");
rather than

DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/dataSource");
Relative paths instead of absolute paths. That's what it's used for.

----
URL:
----
1. GET: http://localhost:8082/011-Rest-BasicAuth/001/user/
2. BasicAuth: user-admin, pass-admin
3. Accept: application/json

 */
package _001;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UserController {
	@Autowired
	UserDAO userDAO;

	@RequestMapping(value="/user", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})  
	public ResponseEntity<List<User>> getAllUsers(){
		return new ResponseEntity<List<User>>(userDAO.getUsers(), HttpStatus.OK);
	}

	@RequestMapping(value="/user/{id}", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})  
	public User viewEmp(@PathVariable("id") int id){
		return userDAO.getUserById(id);
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Void> createUser(@RequestBody User user,  UriComponentsBuilder ucBuilder) {
		userDAO.createUser(user); 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
		userDAO.updateUser(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
		userDAO.deleteUser(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
