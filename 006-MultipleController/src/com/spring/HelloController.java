/*
 * index page= http://localhost:8082/006-MultipleController/
 */

package com.spring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController{
 
   @RequestMapping(value = "/hello", method = RequestMethod.GET)
   public ModelAndView printHello(ModelMap model) {
	  return new ModelAndView("hello", "message","HELLO SPRING MVC");
   }
}