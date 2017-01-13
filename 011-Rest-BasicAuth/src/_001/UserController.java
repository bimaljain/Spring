/*
What is Basic Authentication?
Traditional authentication approaches like login pages or session identification are good for web based clients involving human interaction but does 
not really fit well when communicating with [REST] clients which may not even be a web application. Think of an API over a server which tries to 
communicate with another API on a totally different server, without any human intervention.

Basic Authentication provides a solution for this problem, although not very secure. With Basic Authentication, clients send it’s Base64 encoded 
credentials with each request, using HTTP [Authorization] header . That means each request is independent of other request and server may/does not 
maintain any state information for the client, which is good for scalability point of view.

Authorization : Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0...

This header will be sent with ech request. Since Credentials [Base 64 encoded, not even encrypted] are sent with each request, they can be compromised. 
One way to prevent this is using HTTPS in conjunction with Basic Authentication.

Basic Authentication & Spring Security
With two steps, you can enable the Basic Authentication in Spring Security Configuration.
1. Configure httpBasic : Configures HTTP Basic authentication. [http-basic in XML]
2. Configure authentication entry point with BasicAuthenticationEntryPoint : In case the Authentication fails [invalid/missing credentials], this 
entry point will get triggered. It is very important, because we don’t want [Spring Security default behavior] of redirecting to a login page on 
authentication failure [ We don't have a login page].

That’s all you need to configure basic security.

http://localhost:8082/009-Rest-BasicAuth/user/

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
