/*
Spring MVC allow you to intercept web request through handler interceptors. The handler interceptor have to implement the HandlerInterceptor interface, 
which contains three methods :

preHandle() – Called before the handler execution, returns a boolean value, “true” : continue the handler execution chain; “false”, stop the 
execution chain and return it.

postHandle() – Called after the handler execution, allow manipulate the ModelAndView object before render it to view page.
This is called after requestHandlerMethod is executed but before calling the view file.

afterCompletion() – Called after the complete request has finished. Seldom use, cant find any use case.
This is called after view file produce the response object but before rendering the page to the user.

In this tutorial, you will create two handler interceptors to show the use of the HandlerInterceptor.
ExecuteTimeInterceptor – Intercept the web request, and log the controller execution time.
MaintenanceInterceptor – Intercept the web request, check if the current time is in between the maintenance time, if yes then redirect it to 
maintenance page.

http://localhost:8082/005-MVC/002/013-hello
 */
package _002.Annotation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class _013_InterceptorController{

   @RequestMapping(value = "/013-hello", method = RequestMethod.GET)
   public ModelAndView printHello() {
		ModelAndView modelAndView = new ModelAndView("013-hello");
		modelAndView.addObject("message", "Hello Spring MVC Framework!");
		return modelAndView;
   }
}