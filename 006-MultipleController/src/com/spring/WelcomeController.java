/*
 * index page= http://localhost:8082/005-HelloWeb/
 * Directly invoke: http://localhost:8082/005-HelloWeb/hello2
 */

package com.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;

@Controller
public class WelcomeController{
 
   @RequestMapping(value = "/welcome", method = RequestMethod.GET)
   public ModelAndView printHello(ModelMap model) {
	   return new ModelAndView("hello", "message","WELCOME SPRING MVC");
   }
}