/*
------------------
USING ANNOTATIONS:
------------------
WE DONT NEED:
1. <bean name="/hello2" class="com.spring.HelloController" />
2. public class HelloController extends AbstractController

ADD:
1. @Controller: Class annotated with @Controller will act as Spring controller.
2. @RequestMapping (can be used either on an entire class or a particular handler method)
3. The <context:component-scan> tag will be use to activate Spring annotation scanning capability which allows to make use of annotations like 
@Controller. @Controller serves as a specialized @Component. It is typically used in combination with annotated handler methods based on the 
org.springframework.web.bind.annotation.RequestMapping annotation.

FLOW:
1. When a request reaches web.xml file from client browser, the incoming request is first mapped to the front controller or DispatcherServlet.
2. The front controller will check all the controllers and based on the URL pattern, dispatches the incoming HTTP request to the appropriate request 
handler method for further processing.
3. The method will send the model and view to the front controller.
4. The front controller will take help from ViewResolver to pickup the defined view for the request.
5. Once view is finalized, the DispatcherServlet passes the model data to the view which is finally rendered on the browser.

----
URL:
----
http://localhost:8082/005-MVC/002/hello

 */

package _002.Annotation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class _001_HelloController{

   @RequestMapping(value = "/hello", method = RequestMethod.GET)
   public ModelAndView printHello() {
		ModelAndView modelAndView = new ModelAndView("001-hello");
		modelAndView.addObject("message", "Hello Spring MVC Framework!");
		return modelAndView;
   }
}
