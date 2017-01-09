/*
Spring Security is a lightweight security framework that provides authentication and authorization support in order to Secure Spring-based 
applications. It integrates well with Spring MVC and comes bundled with popular security algorithm implementations.

This tutorial demonstrates Spring Security 4 usage to secure a Spring MVC web application, securing URL access with authentication. This post uses 
Spring Annotation based configuration for Servlet 3.0 containers [thus no web.xml]

-------------------------
ADD: spring-security jars
-------------------------

----------------------------------------
ADD Spring Security Configuration Class:
----------------------------------------
The first and foremost step to add spring security in our application is to create Spring Security Java Configuration. This configuration creates a 
Servlet Filter known as the "springSecurityFilterChain" which is responsible for all the security (protecting the application URLs, validating 
submitted username and passwords, redirecting to the log in form, etc) within our application.

Method configure(AuthenticationManagerBuilder auth) in above class configures AuthenticationManagerBuilder with user credentials and allowed roles. 
This AuthenticationManagerBuilder creates AuthenticationManager which is responsible for processing any authentication request. Notice that in this 
example, we have used in-memory authentication while you are free to choose from JDBC, LDAP and other authentications.

The overridden Method configure(HttpSecurity http) configures HttpSecurity which allows configuring web based security for specific http requests. 
By default it will be applied to all requests, but can be restricted using requestMatcher(RequestMatcher)/antMathchers or other similar methods.
In above configuration, we say that URL’s ‘/’ is not secured, anyone can access them. URL ‘/admin’ can only be accessed by someone who have ADMIN role.
 URL ‘/db’ can only be accessed by someone who have both DBA role.

Method formLogin provides support for form based authentication and will generate a default form asking for user credentials. You are allowed to 
configure your own login form.We will see examples for the same in subsequent posts.

We have also used exceptionHandling().accessDeniedPage() which in this case will catch all 403 [http access denied] exceptions and display our 
user defined page instead of showing default HTTP 403 page [ which is not so helpful anyway].

-------------------------------------------
Register the springSecurityFilter with war:
-------------------------------------------
SecurityConfigurationInitializer.java is a initializer class which registers the springSecurityFilterChain [created above] with application war.

This setup in XML configuration format would be:
<filter>
    <filter-name>springSecurityFilterChain</filter-name>
    <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
</filter>

<filter-mapping>
    <filter-name>springSecurityFilterChain</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>

-----------------
Controller class:
-----------------
Methods in controller class are trivial. Method getPrincipal is a generic function which returns the logged in user name from Spring SecurityContext.
Method logoutPage handles the logging out with a simple call to SecurityContextLogoutHandler().logout(request, response, auth);. It’s handy and saves 
you from putting cryptic logout logic in your JSP’s which is not really manageable.

Add Initializer class (equivalent of web.xml):
Notice that above initializer class extends AbstractAnnotationConfigDispatcherServletInitializer which is the base class for all 
WebApplicationInitializer implementations. Implementations of WebApplicationInitializer configures ServletContext programatically, for Servlet 3.0 
environments. It means we won’t be using web.xml and we will deploy the app on Servlet 3.0 container.
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
