/*
@ExceptionHandler: Annotation for handling exceptions in specific handler classes and/or handler methods.
 */
package com.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@RequestMapping(value="/hello", method=RequestMethod.POST)	
	public ModelAndView authenticateUser(HttpServletRequest request, HttpServletResponse response){
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		
		if (user.equals("admin") && password.equals("admin"))
			return new ModelAndView("hellopage","message","Welcome "+ user);
		else
			throw new SpringException("Incorrect user or password","E404");		
	}
	
	@ExceptionHandler(SpringException.class)
	public ModelAndView customExceptionHandler(SpringException ex){
		ModelAndView model = new ModelAndView();
		model.setViewName("ExceptionPage");
		model.addObject("errCode", ex.getExCode());
		model.addObject("errMsg", ex.getExceptionMsg());
		return model;
	}
}
