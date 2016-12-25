/*
 * index page= http://localhost:8082/005-HelloWeb/
 * Directly invoke: http://localhost:8082/005-HelloWeb/hello2
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