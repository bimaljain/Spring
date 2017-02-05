/*
=======================================
Apache Tomcat
Spring
Hibernate
Maven
Rest API
OAuth
	1. User details: is in-memory
	2. Client details: is in-memory
	3. Token Store: is in-memory
======================================
1. The fact that you have @Transactional on a method in a @Service/@Component annotated class along with a TransactionManager means the whole 
transaction lifecycle will be managed by Spring. When your save() method is called, Spring will open a Session, start a transaction, execute your code, 
commit the transaction and close the Session. The Session it starts is bound to the current thread and available through getCurrentSession().You should 
not open a new session, but get the current one, associated to the current transaction by Spring. If you instead use openSession(), you are opening a 
completely unrelated Session, not managed by Spring's TransactionManager. As such, the transaction won't be committed and the Session won't be closed 
unless you do it yourself.
 
2. This will only work if you use a transaction manager that knows how to associate a Hibernate session with the transaction (typically, a 
HibernateTransactionManager). The session factory should also be handled by Spring, and injected by Spring in your DAOs.

3. Methods are annotated with @Transactional so the Spring Hibernate transaction manager creates the required transactions and the respective sessions.
In Spring, there is a one-to-one correspondence between the business transaction demarcated by @Transactional, and the hibernate Session.
That is, when a business transaction is begun by invoking a @Transactional method, the hibernate session is created (a TransactionManager may delay 
the actual creation until the session is first used). Once that method completes, the business transaction is committed or rolled back, which closes 
the hibernate session.

4. Spring provide transaction advice to beans which are annotated with @Transactional. Spring transaction provides support for following six attributes 
which determines the behavior of the transaction:
1.isolation , 2.no-rollback-for, 3.propagation, 4.read-only, 5.rollback-for, 6.timeout.

@Transactional can start new transaction or can join existing transactional context based its propagation attribute value.

In @Transactionalcontext, getCurrentSession() method creates new Session object if it does not exist or returns Session which is attached to current 
transaction. OpenSession() method always creates new session. @Transactional helps you to extend scope of Session.

Session is open first time when getCurrentSession() is executed and it is closed when transaction ends and it is flushed before transaction commits.

In Spring, If we use getCurrentSession() in non-transactional context we get an exception.
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
		return userDAO.getUserById(id).get(0);
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
