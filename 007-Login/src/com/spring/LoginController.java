/*
 * http://localhost:8082/007-Login/
 */
package com.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
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
			return new ModelAndView("errorpage","message", "Incorrect user or password. Try again.");		
	}
}
