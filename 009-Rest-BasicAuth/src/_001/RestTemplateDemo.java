package _001;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.RestTemplate;


public class RestTemplateDemo {
	public static final String REST_SERVICE_URI = "http://localhost:8082/009-Rest-BasicAuth";

	private static HttpHeaders getHeaders(){
		String plainCredentials="admin:admin";
		String base64Credentials = new String(Base64.encode(plainCredentials.getBytes()));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Credentials);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	// we have to use restTemplate.exchange() instead of restTemplate.getForObject() since it cannot accept additional HttpEntity parameter
	// and this contains basic authentication
	private static void listAllUsers(){
		RestTemplate restTemplate = new RestTemplate(); 
		//HttpEntity: Represents an HTTP request or response entity, consisting of headers and body.  
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		//Extension of HttpEntity that adds a HttpStatus status code. 
		ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI+"/user/", HttpMethod.GET, request, List.class);
		List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>)response.getBody();
		for(LinkedHashMap<String, Object> map : usersMap){
			System.out.println("User : id="+map.get("id")+", userid="+map.get("userid")+", password="+map.get("password"));
		}
	}
	
	 /* GET */
    private static void getUser(){
    	RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/user/1", HttpMethod.GET, request, User.class);
        User user = response.getBody();
        System.out.println(user);
    }
     
    /* POST */
    private static void createUser() {
        RestTemplate restTemplate = new RestTemplate();
        User user = new User(0,"Sarah","pass");
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/", request, User.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
 
    /* PUT */
    private static void updateUser() {
        RestTemplate restTemplate = new RestTemplate();
        User user  = new User(250,"Tomy","Tomy");
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/user/1", HttpMethod.PUT, request, User.class);
        System.out.println(response.getBody());
    }
 
    /* DELETE */
    private static void deleteUser() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI+"/user/200", HttpMethod.DELETE, request, User.class);
    }
	
	public static void main(String args[]){
		listAllUsers();
		getUser();
		createUser();
		updateUser();
		deleteUser();
	}
}
