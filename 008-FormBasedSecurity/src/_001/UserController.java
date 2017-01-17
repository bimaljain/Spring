/*
----------------------------------------
ADD Spring Security Configuration Class:
----------------------------------------
The first and foremost step to add spring security in our application is to create Spring Security Java Configuration. This configuration creates a 
Servlet Filter known as the "springSecurityFilterChain" which is responsible for all the security (protecting the application URLs, validating 
submitted username and passwords, redirecting to the log in form, etc) within our application.
[@EnableWebSecurity //this annotation creates springSecurityFilterChain bean]

Method configure(AuthenticationManagerBuilder auth) in above class configures AuthenticationManagerBuilder with user credentials and allowed roles. 
This AuthenticationManagerBuilder creates AuthenticationManager which is responsible for processing any authentication request. Notice that in this 
example, we have used in-memory authentication while you are free to choose from JDBC, LDAP and other authentications.

The overridden Method configure(HttpSecurity http) configures HttpSecurity which allows configuring web based security for specific http requests. 
By default it will be applied to all requests, but can be restricted using requestMatcher(RequestMatcher)/antMathchers or other similar methods.
In above configuration, we say that URL’s ‘/’ is not secured, anyone can access them. URL ‘/admin’ can only be accessed by someone who have ADMIN role.
 URL ‘/db’ can only be accessed by someone who has DBA role.

Method formLogin provides support for form based authentication and will generate a default form asking for user credentials. You are allowed to 
configure your own login form.

We have also used exceptionHandling().accessDeniedPage() which in this case will catch all 403 [http access denied] exceptions and display our 
user defined page instead of showing default HTTP 403 page [ which is not so helpful anyway].

-------------------------------------------
Register the springSecurityFilter with war:
-------------------------------------------
SecurityConfigurationInitializer.java is a initializer class which registers the springSecurityFilterChain [created above] with application war.
AbstractSecurityWebApplicationInitializer creates the DelegatingFilterProxy which is used to look up a bean by the name of springSecurityFilterChain. 
The springSecurityFilterChain is created using @EnableWebSecurity. Along with this, you need to add @Configuration annotation. Without it the Root 
ApplicationContext is not even going to try to load the SecurityConfiguration.

-----------------------
Servlet 3.0+ Container:
-----------------------
Create a ServletInitializer class by extending AbstractAnnotationConfigDispatcherServletInitializer. This configures ServletContext programatically, 
for Servlet 3.0+ environments. Servlet 3.0+ container will pick up this class and run it automatically.  This is the replacement for web.xml.

One thing to keep in mind that the Spring java based configuration api’s like AbstractAnnotationConfigDispatcherServletInitializer depends on 
Servlet 3.0 containers. So make sure you don’t have any web.xml with servlet declaration less than 3.0. For our case, we have removed web.xml file 
from our application.

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
