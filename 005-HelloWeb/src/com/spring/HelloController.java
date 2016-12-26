/*
-------------------------
Spring Web MVC Framework:
-------------------------
The Spring web MVC framework provides MVC architecture and ready components that can be used to develop flexible and loosely coupled web applications. 
The MVC pattern results in separating the different aspects of the application, while providing a loose coupling between these elements.
1. The Model encapsulates the application data and in general they will consist of POJO.
2. The View is responsible for rendering the model data and in general it generates HTML output that the client's browser can interpret.
3. The Controller is responsible for processing user requests and building appropriate model and passes it to the view for rendering.

------------------
DispatcherServlet:
------------------
The Spring Web MVC framework is designed around a DispatcherServlet that handles all the HTTP requests and responses. Following is the sequence of 
events corresponding to an incoming HTTP request to DispatcherServlet:	
1. After receiving an HTTP request, DispatcherServlet consults the HandlerMapping to call the appropriate Controller. When no handler mapping is 
explicitly specified in configuration, BeanNameUrlHandlerMapping is used to map the request by default. This class search in all controller classes 
to map the particular request with the method.
2. The Controller takes the request and calls the appropriate service methods based on used GET or POST method. The service method will set model 
data based on defined business logic and returns view name to the DispatcherServlet.
3. The DispatcherServlet will take help from ViewResolver to pickup the defined view for the request.
4. Once view is finalized, the DispatcherServlet passes the model data to the view which is finally rendered on the browser.

All the above mentioned components i.e. HandlerMapping, Controller and ViewResolver are parts of WebApplicationContext which is an extension of the 
plain ApplicationContext with some extra features necessary for web applications.
DispatcherServlet is also termed as front controller.

--------
web.xml:
--------
1. You need to map requests that you want the DispatcherServlet to handle, by using a URL mapping in the web.xml file. The example shows 
declaration and mapping for HelloWebDispatcherServlet.
2. The web.xml file will be kept in WEB-INF directory. Upon initialization of HelloWebDispatcherServlet, the framework will try to load the 
application context from a file named [servlet-name]-servlet.xml located in the application's WEB-INF directory. In this case our file will be 
HelloWeb-servlet.xml.
3. Next, <servlet-mapping> tag indicates what URLs will be handled by which DispatcherServlet. Here all the HTTP requests ending with .jsp will be 
handled by the HelloWeb DispatcherServlet.
4. If you do not want to go with default filename as [servlet-name]-servlet.xml and default location as WEB-INF, you can customize this file name 
and location by adding the servlet listener ContextLoaderListener in your web.xml file as follows:

<web-app...>
<context-param>
<param-name>contextConfigLocation</param-name>
<param-value>/WEB-INF/HelloWeb-servlet.xml</param-value>
</context-param>
<listener>
<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
</web-app>

----------------------
WebApplicationContext:
----------------------
1. The [servlet-name]-servlet.xml file will be used to create the beans defined, overriding the definitions of any beans defined with the same name 
in the global scope.
2. The <context:component-scan...> tag will be use to activate Spring MVC annotation scanning capability which allows to make use of annotations 
like @Controller and @RequestMapping etc.
3. The InternalResourceViewResolver will have rules defined to resolve the view names. As per the above defined rule, a logical view named hello 
is delegated to a view implementation located at /WEB-INF/jsp/hello.jsp .

-----------
Controller:
-----------
1. The @Controller annotation indicates that a particular class serves the role of a Spring MVC controller. 
2. The @RequestMapping annotation is used to map a URL to either an entire class or a particular handler method.
3. There are following important points to be noted about the controller defined above:
· You will define required business logic inside a service method. You can call another method inside this method as per requirement.
· Based on the business logic defined, you will create a model within this method. You can set different model attributes and these attributes will 
be accessed by the view to present the final result. This example creates a model with its attribute "message".
· A defined service method can return a String which contains the name of the view to be used to render the model. This example returns "hello" as 
logical view name.

-----
View:
-----
index page= http://localhost:8082/005-HelloWeb/
Directly invoke: http://localhost:8082/005-HelloWeb/hello2

 */

package com.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.ModelMap;

@Controller
public class HelloController{
 
   @RequestMapping(value = "/hello2", method = RequestMethod.GET)
   public String printHello(ModelMap model) {
      model.addAttribute("message", "Hello Spring MVC Framework!");
      return "hello";
   }
}
