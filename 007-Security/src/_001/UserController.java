/*
-----------------------------------
Spring MVC's DelegatingFilterProxy:
-----------------------------------
It seems to suggest that this component is the "glue" between the servlets defined in web.xml and the components defined in the Spring 
applicationContext.xml.

DelegatingFilterProxy

When using servlet filters, you obviously need to declare them in your web.xml, or they will be ignored by the servlet container. In Spring Security, 
the filter classes are also Spring beans defined in the application context and thus able to take advantage of Spring's rich dependency-injection 
facilities and lifecycle interfaces. Spring's DelegatingFilterProxy provides the link between web.xml and the application context.

When using DelegatingFilterProxy, you will see something like this in the web.xml file:

<filter>
  <filter-name>myFilter</filter-name>
  <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
</filter>

<filter-mapping>
  <filter-name>myFilter</filter-name>
  <url-pattern>/*</url-pattern>
</filter-mapping>

Notice that the filter is actually a DelegatingFilterProxy, and not the class that will actually implement the logic of the filter. What 
DelegatingFilterProxy does is delegate the Filter's methods through to a bean which is obtained from the Spring application context. This enables 
the bean to benefit from the Spring web application context lifecycle support and configuration flexibility. The bean must implement 
javax.servlet.Filter and it must have the same name as that in the filter-name element. 

The DelegatingFilterProxy is a Filter as it was explained above, whose goal is "delegating to a Spring-managed bean that implements the Filter 
interface", that is, it finds a bean ("target bean" or "delegate") in your Spring application context and invokes it. How is it possible? Because 
this bean implements javax.servlet.Filter, its doFilter method is called.

Which bean is called? the DelegatingFilterProxy "Supports a "targetBeanName" [...], specifying the name of the target bean in the Spring application 
context." As you saw in your web.xml that the bean's name is "springSecurityFilterChain". So, in the context of a web application, a Filter 
instantiates a bean called "springSecurityFilterChain" in your application context and then delegate to it via the doFilter() method.

Remember, your application context is defined with ALL THE APPLICATION-CONTEXT (XML) files. For instance: applicationContext.xml AND 
applicationContext-security.xml.

So try to find a bean called "springSecurityFilterChain" in the latter......and probably you can't (for instance if you followed a tutorial or 
if you configured the security using Roo)

Here is the magic: there's a new element for configuring the security, something like

<http auto-config="true" use-expressions="true"> 
as it is allowed by http://www.springframework.org/schema/security/spring-security-3.0.xsd, will do the trick.

When Spring loads the application context using XML files, if it finds a element, it will try to set up the HTTP security, that is, a filter 
stack and protected URLs and to register the FilterChainProxy named "springSecurityFilterChain".


-----------------------------------------------
What is ContextLoaderListener role and purpose:
-----------------------------------------------
ContextLoaderListener bootstrap a WebApplicationContext in the ServletContext. That is, it creates a root application context for the web application 
and puts it in the ServletContext. Bootstrap here means its the first point where you can create an application context, and this will be the root 
context. In Spring, you can have multiple application contexts and they are organized in hierarchical structures, for example, each DispatcherServlet 
can have its own application context, these contexts will be child context of the root context, a child context can access beans in the parent context, 
but not vice versa. All the application contexts are optional, you can have only Servlet context (you are only using Spring MVC), or only root 
application context(you are using Spring but with Struts as MVC framework), or both(you are using Spring MVC and Spring Security). But you need at 
least one to use the Spring IoC , injecting and bean facility. This makes Spring very flexible, easy to integrate with other frameworks, it not force 
you to use one technology. When you are using Struts and Spring together, from the perspective of Struts , Spring is just a library with the root 
application context instance as entry point. Root Application context is also used by other components of Spring framework, for example the Spring 
Security, all the spring security related beans are stored in root context, its not a part of MVC, you need to configure ContextLoaderListener when 
you are using Spring Security, this makes the security component pluggable, you can use other implementation if you want, just like you can use other 
MVC implementation. They are loosely coupled and can be replaced easily.

Configure root application context:
In web.xml
 
	<listener>
	  <listener-class>
	    org.springframework.web.context.ContextLoaderListener
	  </listener-class>
	</listener>
	 
	<context-param>
	  <param-name>contextConfigLocation</param-name>
	  <param-value>/WEB-INF/spring/*.xml</param-value>
	</context-param>
 
In annotation, take the Spring Security as example:
 
    AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
    rootAppContext.register(configurationClasses);
    servletContext.addListener(new ContextLoaderListener(rootAppContext));
 
Fortunately, you don't have to this, there is an implementation provided by Spring, you can simply extends it:
 
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
    public SecurityWebApplicationInitializer() {
        super(ExampleSecurityConfiguration.class);
    }
}
 
In the onStartup method, the root context will be created and listener will be added.
It also registers the DelegatingFilterProxy to use the springSecurityFilterChain 


----------------------------------------
A Bit on ApplicationContext Hierarchies:
----------------------------------------
Spring's ApplicationContext provides the capability of loading multiple (hierarchical) contexts, allowing each to be focused on one particular layer, 
such as the web layer of an application or middle-tier services. One of the canonical examples of using hierarchical ApplicationContext is when we 
have multiple DispatcherServlets in a web application and we're gonna share some of the common beans such as datasources between them. This way, we 
can define a root ApplicationContext that contain all the common beans and multiple WebApplicationContexts that inherit the common beans from the 
root context. In the Web MVC framework, each DispatcherServlet has its own WebApplicationContext, which inherits all the beans already defined in the 
root WebApplicationContext. These inherited beans can be overridden in the servlet-specific scope, and you can define new scope-specific beans local 
to a given Servlet instance.

Suppose we're gonna develop a web application and we're going to use Spring MVC, Spring Security and Spring Data JPA. For this simple scenario, 
we would have at least three different config files. A WebConfig that contains all our web related configurations, such as ViewResolvers, 
Controllers, ArgumentResolvers, etc. Something like following:

	@EnableWebMvc
	@Configuration
	@ComponentScan(basePackages = "com.so.web")
	public class WebConfig extends WebMvcConfigurerAdapter {
	    @Bean
	    public InternalResourceViewResolver viewResolver() {
	        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	        viewResolver.setPrefix("/WEB-INF/views/");
	        viewResolver.setSuffix(".jsp");
	return viewResolver;
	    }
	@Override
	    public void configurePathMatch(PathMatchConfigurer configurer) {
	        final boolean DO_NOT_USE_SUFFIX_PATTERN_MATCHING = false;
	        configurer.setUseSuffixPatternMatch(DO_NOT_USE_SUFFIX_PATTERN_MATCHING);
	    }
	}

Here i'm defining a ViewResolver to resolve my plain old jsps, poor life decisions, basically. We would need a RepositoryConfig, which contains all 
the data access facilities such as DataSource, EntityManagerFactory, TransactionManager, etc. It probably would be like following:

	@Configuration
	@EnableTransactionManagement
	@EnableJpaRepositories(basePackages = "com.so.repository")
	public class RepositoryConfig {
	    @Bean
	    public DataSource dataSource() { ... }
	@Bean
	    public LocalContainerEntityManagerFactoryBean entityManagerFactory() { ... }
	@Bean
	    public PlatformTransactionManager transactionManager() { ... }
	}

And a SecurityConfig which contains all the security related stuff!

	@Configuration
	@EnableWebSecurity
	public class SecurityConfig extends WebSecurityConfigurerAdapter {
	    @Override
	    @Autowired
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception { ... }
	@Override
	    protected void configure(HttpSecurity http) throws Exception { ... }
	}

For gluing all these together, we have two options. First, we can define a typical hierarchical ApplicationContext, by adding RepositoryConfig and 
SecurityConfig in root context and WebConfig in their child context:

	public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	    @Override
	    protected Class<?>[] getRootConfigClasses() {
	        return new Class<?>[] { RepositoryConfig.class, SecurityConfig.class };
	    }
	@Override
	    protected Class<?>[] getServletConfigClasses() {
	        return new Class<?>[] { WebConfig.class };
	    }
	@Override
	    protected String[] getServletMappings() {
	        return new String[] { "/" };
	    }
	}

Since we have a single DispatcherServlet here, we can add the WebConfig to the root context and make the servlet context empty:

	public class ServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	    @Override
	    protected Class<?>[] getRootConfigClasses() {
	        return new Class<?>[] { RepositoryConfig.class, SecurityConfig.class, WebConfig.class };
	    }
	@Override
	    protected Class<?>[] getServletConfigClasses() {
	        return null;
	    }
	@Override
	    protected String[] getServletMappings() {
	        return new String[] { "/" };
	    }
	}

-------
EXTRAS:
-------
ContextLoaderListener:
Listens during server start up/shutdown
Takes the Spring configuration files as input and creates the beans as per configuration and make it ready (destroys the bean during shutdown)
Configuration files can be provided like this in web.xml
<param-name>contextConfigLocation</param-name>  
<param-value>/WEB-INF/dispatcher-servlet.xml</param-value>  

Basically you can isolate your root application context and web application context using ContextLoaderListner. 
Root Config Classes will be loaded first and then Servlet Config Classes will be loaded.
The config file mapped with context param will behave as root application context configuration. And config file mapped with dispatcher servlet 
will behave like web application context. In any web application we may have multiple dispatcher servlets, so multiple web application contexts.
But in any web application we may have only one root application context that is shared with all web application contexts. We should define our 
common services, entities, aspects etc in root application context. And controllers, interceptors etc are in relevant web application context.

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
