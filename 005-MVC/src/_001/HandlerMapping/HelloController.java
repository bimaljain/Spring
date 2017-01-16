/*
-------------------------
Spring Web MVC Framework:
-------------------------
The Spring web MVC framework provides MVC architecture and ready components that can be used to develop flexible and loosely coupled web applications. 
1. The Model encapsulates the application data and in general they will consist of POJO.
2. The View is responsible for rendering the model data and in general it generates HTML output that the client's browser can interpret.
3. Controller creates the model objects. The front Controller is responsible for controlling the flow of the application.

----------------
Spring MVC Flow:
----------------
The Spring Web MVC framework is designed around a DispatcherServlet (also called FRONT CONTROLLER) that handles all the HTTP requests and responses. 
Following is the sequence of events corresponding to an incoming HTTP request to front controller:	
1. After receiving an HTTP request, front controller consults the HandlerMapping to call the appropriate Controller. When no handler mapping is 
explicitly specified in configuration, BeanNameUrlHandlerMapping is used to map the request by default. This class search in all controller classes 
to map the particular request.
2. The front controller pass the request to the appropriate controller, which in turn sets the model data based on defined business logic and returns 
view name to the front controller.
3. The front controller will take help from ViewResolver to pickup the defined view for the request.
4. Once view is finalized, the DispatcherServlet passes the model data to the view which is finally rendered on the browser.

All the above mentioned components i.e. HandlerMapping, Controller and ViewResolver are parts of WebApplicationContext which is an extension of the 
plain ApplicationContext with some extra features necessary for web applications. 

[
web.xml means servletContext.
DispatcherServlet is (child)applicationContext.
applicationContext lives inside servletContext
]

--------------------------
BeanNameUrlHandlerMapping:
--------------------------
The BeanNameUrlHandlerMapping class maps URL requests to beans names. It is the default handler mapping class, so it is the one created by the 
DispatcherServlet when Spring cannot find any handler mapping class declared. An example of using the BeanNameUrlHandlerMapping class is shown below. 
There are two beans declared, the first one’s name is helloWorld.htm and its class is the HelloWorldController. So the BeanNameUrlHandlerMapping will 
map any helloWorld URL request to this Controller. The second bean’s name is the hello*.htm and its class is also the HelloWorldController. So, in this 
case, the BeanNameUrlHandlerMapping will map any URL request that starts with hello (such as helloWorld, helloAll) to the HelloWorldController.

--------
web.xml:
--------
You need to map requests that you want the DispatcherServlet to handle, by using a URL mapping in the web.xml file.
1. The web.xml file will be kept in WEB-INF directory. Upon initialization of DispatcherServlet, the framework will try to load the application context 
from a file named [servlet-name]-servlet.xml located in the application's WEB-INF directory. In this case our file will be HelloWeb-servlet.xml.
2. Next, <servlet-mapping> tag indicates what URLs will be handled by the DispatcherServlet. Here all the HTTP requests will be handled by the 
DispatcherServlet.
3. If you do not want to go with default filename as [servlet-name]-servlet.xml and default location as WEB-INF, you can customize this file name 
and location by adding contextConfigLocation in your web.xml.

----
URL:
----
Index page= http://localhost:8082/005-MVC/
Directly invoke: http://localhost:8082/005-MVC/001/hello2

 */

package _001.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class HelloController extends AbstractController {

	// Check the class-AbstractController to identify which method to override
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView("001-hello");
		modelAndView.addObject("message", "Hello Spring MVC Framework!");
		return modelAndView;		
	}
}
