package _001;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import _001.config.AuthTokenInfo;

public class RestTemplateDemo {
	public static final String REST_SERVICE_URI = "http://localhost:8082/010-Rest-OAuth";
    public static final String AUTH_SERVER_URI = "http://localhost:8082/010-Rest-OAuth/oauth/token";
    public static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=admin&password=admin";
    public static final String QPM_ACCESS_TOKEN = "?access_token=";

    private static HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
    
	private static HttpHeaders getHeadersWithClientCredentials(){
		String plainClientCredentials="my-trusted-client:secret";
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
		HttpHeaders headers = getHeaders();
		headers.add("Authorization", "Basic " + base64ClientCredentials);
		return headers;
	}
	
    /*
     * Send a POST request [on /oauth/token] to get an access-token, which will then be send with each request.
     */
    private static AuthTokenInfo sendTokenRequest(){
        RestTemplate restTemplate = new RestTemplate();          
        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI+QPM_PASSWORD_GRANT, HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();
        AuthTokenInfo tokenInfo = null;
         
        if(map!=null){
            tokenInfo = new AuthTokenInfo();
            tokenInfo.setAccess_token((String)map.get("access_token"));
            tokenInfo.setToken_type((String)map.get("token_type"));
            tokenInfo.setRefresh_token((String)map.get("refresh_token"));
            tokenInfo.setExpires_in((int)map.get("expires_in"));
            tokenInfo.setScope((String)map.get("scope"));
            System.out.println(tokenInfo);
        }
        return tokenInfo;
    }

	// we have to use restTemplate.exchange() instead of restTemplate.getForObject() since it cannot accept additional HttpEntity parameter
	// and this contains basic authentication
	private static void listAllUsers(AuthTokenInfo tokenInfo){
		RestTemplate restTemplate = new RestTemplate(); 
		//HttpEntity: Represents an HTTP request or response entity, consisting of headers and body.  
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		//Extension of HttpEntity that adds a HttpStatus status code. 
		ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI+"/user/" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(), 
				HttpMethod.GET, request, List.class);
		List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>)response.getBody();
		for(LinkedHashMap<String, Object> map : usersMap){
			System.out.println("User : id="+map.get("id")+", userid="+map.get("userid")+", password="+map.get("password"));
		}
	}
	
	 /* GET */
    private static void getUser(AuthTokenInfo tokenInfo){
    	RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/user/1" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(), 
        		HttpMethod.GET, request, User.class);
        User user = response.getBody();
        System.out.println(user);
    }
     
    /* POST */
    private static void createUser(AuthTokenInfo tokenInfo) {
        RestTemplate restTemplate = new RestTemplate();
        User user = new User(0,"Sarah","pass");
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(), request, User.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
 
    /* PUT */
    private static void updateUser(AuthTokenInfo tokenInfo) {
        RestTemplate restTemplate = new RestTemplate();
        User user  = new User(250,"Tomy","Tomy");
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI+"/user/1" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(), 
        		HttpMethod.PUT, request, User.class);
        System.out.println(response.getBody());
    }
 
    /* DELETE */
    private static void deleteUser(AuthTokenInfo tokenInfo) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI+"/user/200" + QPM_ACCESS_TOKEN + tokenInfo.getAccess_token(), HttpMethod.DELETE, request, User.class);
    }
	
	public static void main(String args[]){
		AuthTokenInfo tokenInfo = sendTokenRequest();
		listAllUsers(tokenInfo);
		getUser(tokenInfo);
		createUser(tokenInfo);
		updateUser(tokenInfo);
		deleteUser(tokenInfo);
	}
}
