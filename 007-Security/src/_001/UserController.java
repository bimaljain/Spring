/*
--------
web.xml:
--------
There are two types of contexts we are dealing with:
1: root context (parent context. Typically include all jdbc(ORM, Hibernate) initialisation and other spring security related configuration)
2: individual servlet context (child context.Typically Dispatcher Servlet Context and initialise all beans related to spring-mvc (controllers , URL Mapping etc)).


 */

package _001;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView welcomePage(){
		return new ModelAndView("welcome");
	}
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public ModelAndView adminPage(){
		return new ModelAndView("admin");
	}
	
	@RequestMapping(value="/db", method=RequestMethod.GET)
	public ModelAndView dbPage(){		
		return new ModelAndView("db"); 		
	}
	
	@RequestMapping(value="/access_denied", method=RequestMethod.GET)
	public ModelAndView accessDeniedPage(){		
		return new ModelAndView("accessdenied"); 		
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "welcome";
	}

	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userName = principal.toString();
		return userName;
	}

}
