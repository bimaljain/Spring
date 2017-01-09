/*
1. We dont need Jackson jars. Jaxb annotations are part of JDK jars.
2. Jaxb can only serve XMLs and not JSON response.
3. If you want to return a list of domain objects, you should create another object which has list of domain objects as property, and return this object 
from your request handler method. Remember that whatever you return from the request handler method must be properly annotated as well, for jaxb to work.
 
 */
package _001.Jaxb;

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

	//http://localhost:8082/006-Jaxb/001/user.xml
	@RequestMapping(value="/user", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})  
	public UserList getAllUsers(){
		UserList list = new UserList();
		list.setUsers(userDAO.getUsers());
		return list;
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
