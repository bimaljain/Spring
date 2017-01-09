/*
@RequestBody and @ResponseBody annotations are used to bind the HTTP request/response body with a domain object in method parameter or return type. 
Behind the scenes, these annotation uses HTTP Message converters to convert the body of HTTP request/response to domain objects.

-------------
@RequestBody:
-------------
If a method parameter is annotated with @RequestBody, Spring will bind the incoming HTTP request body(for the URL mentioned in @RequestMapping for 
that method) to that parameter. While doing that, Spring will [behind the scenes] use HTTP Message converters to convert the HTTP request body into 
domain object [deserialize request body to domain object], based on Content-Type header present in request.

The Accept header is used by HTTP clients [browsers] to tell the server what content types they’ll accept.
The server sends back the response, which will include a Content-Type header telling the client what the content type of the returned content actually 
is. In case of POST or PUT request, browsers do send data in request, so they actually send content-type as well.

HTTP Post request body contains the detail of user to be created. When a client sends a request [/user/create] to create a user, it will be 
intercepted in this method. Method parameter user is marked with @RequestBody annotation. Thanks to this annotation, Spring will try to bind the 
request body [which can be JSON/XML/Other] to user object[ Means creating a new user object with the details found in the request body like user name,
age etc..], based on Content-Type header in Http request.

But Spring need help to convert the request body into user object. It needs a converter which can convert the data in HTTP request body [which can 
be JSON/XML/Other] into user object.

Spring provides out-of-box many default HttpMessageConverters, which will be used for conversion, depending on presence of certain library in project 
classpath.

For example, if the Content-Type in request Header was one of application/json or application/xml , that means the POST body contains json or 
XML[Popular formats], and if Jackson library is found in your classpath, Spring will delegate the conversion to MappingJackson2HttpMessageConverter 
[for json] or MappingJackson2XmlHttpMessageConverter [for xml].

To declare a dependency to Jackson library (jackson-databind) include following dependency in your pom.xml

<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>${jackson.version}</version>
</dependency>

-------------
@ResponseBody:
-------------
If a method is annotated with @ResponseBody, Spring will bind the return value to outgoing HTTP response body. While doing that, Spring will [behind 
the scenes] use HTTP Message converters to convert the return value to HTTP response body [serialize the object to response body], based on 
Content-Type present in request HTTP header.

----------------
@RestController:
----------------
As from Spring 4, @RestController is the preferred way to achieve the same functionality earlier provided by @ResponseBody. Under the hood, 
@RestController is @Controller+@ResponseBody, and it avoids the need of prefixing every method with @ResponseBody.

@RestController annotation, which marks this class as a controller where every method returns a domain object/pojo instead of a view. It means that 
we are no more using view-resolvers, we are no more directly sending the html in response but we are sending domain object converted into format 
understood by the consumers. In our case, due to jackson library included in class path, the Message object will be converted into JSON format[ or 
in XML if either the jackson-dataformat-xml.jar is present in classpath or Model class i annotated with JAXB annotations].

--------------
@PathVariable:
--------------
@PathVariable This annotation indicates that a method parameter should be bound to a URI template variable [the one in '{}'].

---------------
ResponseEntity:
---------------
ResponseEntity is a real deal. It represents the entire HTTP response. Good thing about it is that you can control anything that goes into it. You 
can specify status code, headers, and body. It comes with several constructors to carry the information you want to sent in HTTP Response.

----------
MediaType:
----------
With @RequestMapping annotation, you can additionally, specify the MediaType to be produced or consumed (using produces or consumes attributes) by 
that particular controller method, to further narrow down the mapping.

------------------------------
Default HttpMessageConverters:
------------------------------
Spring provides out of box following Http message converters which implements HttpMessageConverter interface

1. StringHttpMessageConverter: An HttpMessageConverter implementation that can read and write Strings from the HTTP request and response. By default, 
this converter supports all text media types ( text/*), and writes with a Content-Type of text/plain

2. FormHttpMessageConverter: An HttpMessageConverter implementation that can read and write form data from the HTTP request and response. By default, 
this converter reads and writes the media type application/x-www-form-urlencoded. Form data is read from and written into a MultiValueMap.

3. ByteArrayHttpMessageConverter: An HttpMessageConverter implementation that can read and write byte arrays from the HTTP request and response. By 
default, this converter supports all media types, and writes with a Content-Type of application/octet-stream. This can be overridden by setting the 
supportedMediaTypes property, and overriding getContentType(byte[])

4. MarshallingHttpMessageConverter: An HttpMessageConverter implementation that can read and write XML using Spring’s Marshaller and Unmarshaller 
abstractions from the org.springframework.oxm package. This converter requires a Marshaller and Unmarshaller before it can be used. These can be 
injected via constructor or bean properties. By default this converter supports ( text/xml) and ( application/xml).

5. MappingJackson2HttpMessageConverter: An HttpMessageConverter implementation that can read and write JSON using Jackson’s ObjectMapper. JSON mapping 
can be customized as needed through the use of Jackson’s provided annotations. When further control is needed, a custom ObjectMapper can be injected 
through the ObjectMapper property for cases where custom JSON serializers/deserializers need to be provided for specific types. By default this 
converter supports ( application/json).

6. MappingJackson2XmlHttpMessageConverter: An HttpMessageConverter implementation that can read and write XML using Jackson XML extension’s XmlMapper. 
XML mapping can be customized as needed through the use of JAXB or Jackson’s provided annotations. When further control is needed, a custom XmlMapper 
can be injected through the ObjectMapper property for cases where custom XML serializers/deserializers need to be provided for specific types. By 
default this converter supports (application/xml).

7. SourceHttpMessageConverter: An HttpMessageConverter implementation that can read and write javax.xml.transform.Source from the HTTP request and 
response. Only DOMSource, SAXSource, and StreamSource are supported. By default, this converter supports ( text/xml) and ( application/xml).

8. BufferedImageHttpMessageConverter: An HttpMessageConverter implementation that can read and write java.awt.image.BufferedImage from the HTTP 
request and response. This converter reads and writes the media type supported by the Java I/O API.

---------------------------
Quick introduction to REST:
---------------------------
REST stands for Representational State Transfer. It’s an is an architectural style which can be used to design web services, that can be consumed from
a variety of clients. The core idea is that, rather than using complex mechanisms such as CORBA, RPC or SOAP to connect between machines, simple HTTP 
is used to make calls among them.

In Rest based design, resources are being manipulated using a common set of verbs.

To Create a resource : HTTP POST should be used
To Retrieve a resource : HTTP GET should be used
To Update a resource : HTTP PUT should be used
To Delete a resource : HTTP DELETE should be used
That means, you as a REST service developer or Client, should comply to above criteria, in order to be REST complained.

Often Rest based Web services return JSON or XML as response, although it is not limited to these types only. Clients can specify (using HTTP Accept 
header) the resource type they are interested in, and server may return the resource , specifying Content-Type of the resource it is serving.

------------------
Conversions Again:
------------------
In order to serve JSON, we will be using Jackson library [jackson-databind.jar]. For XML, we will use Jackson XML extension 
[jackson-dataformat-xml.jar]. Mere presence of these libraries in classpath will trigger Spring to convert the output in required format. 
Additionally, We will go a step further by annotating the domain class with JAXB annotations to support XML in case Jackson’s XML extension library 
is not available for some reason.

Note: If you are sending the request by just typing the URL in browser, you may add the suffix [.xml/.json] which help spring to determine the type 
of content to be served.

Main dependencies to be noticed here are Jackson library (jackson-databind) which will be used to convert the response data into JSON string, and 
Jackson XML Extension library (jackson-dataformat-xml) which will help to provide XML converted response. Again, if the jackson-dataformat-xml is 
not included, only JSON response will be served, unless the domain object is annotated explicitly with JAXB annotations.
 */

package _005.Rest.Jackson;

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
