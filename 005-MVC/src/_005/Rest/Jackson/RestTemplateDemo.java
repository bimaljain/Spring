/*
Writing REST Client using RestTemplate

 */

package _005.Rest.Jackson;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

public class RestTemplateDemo {
	public static final String REST_SERVICE_URI = "http://localhost:8082/005-MVC/005";
    
    /* GET */
    private static void listAllUsers(){
        System.out.println("Testing listAllUsers API-----------");         
        RestTemplate restTemplate = new RestTemplate();
        List<Map<String, Object>> usersMap = restTemplate.getForObject(REST_SERVICE_URI+"/user", List.class);         
        if(usersMap!=null){
            for(Map<String, Object> map : usersMap){
                System.out.println("User : id="+map.get("id")+", UserId="+map.get("userid")+", Password="+map.get("password"));
            }
        }
    }
     
    /* GET */
    private static void getUser(){
        System.out.println("Testing getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user = restTemplate.getForObject(REST_SERVICE_URI+"/user/98", User.class);
        System.out.println(user);
    }
     
    /* POST */
    private static void createUser() {
        System.out.println("Testing create User API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user = new User(301,"user-301","pass-301");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/", user, User.class);
        System.out.println("Location : "+uri.toASCIIString());
    }
 
    /* PUT */
    private static void updateUser() {
        System.out.println("Testing update User API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user  = new User(282,"user-302","pass-302");
        restTemplate.put(REST_SERVICE_URI+"/user/1", user);
        System.out.println(user);
    }
 
    /* DELETE */
    private static void deleteUser() {
        System.out.println("Testing delete User API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/user/3");
    }
 
    public static void main(String args[]){
        listAllUsers();
        getUser();
        createUser();
        updateUser();
        deleteUser();
    }
}
