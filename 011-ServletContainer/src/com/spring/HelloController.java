/*
-----------------------
Servlet 3.0+ Container:
-----------------------
Create a ServletInitializer class by extending AbstractAnnotationConfigDispatcherServletInitializer, the Servlet 3.0+ container will pick up this 
class and run it automatically. This is the replacement for web.xml.

One thing to keep in mind that the Spring java based configuration api’s like AbstractAnnotationConfigDispatcherServletInitializer depends on 
Servlet 3.0 containers. So make sure you don’t have any web.xml with servlet declaration less than 3.0. For our case, we have removed web.xml file 
from our application.

http://localhost:8082/011-ServletContainer/
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
