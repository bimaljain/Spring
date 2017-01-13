package _001.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

//SecurityInitializer creates the DelegatingFilterProxy which is used to look up a bean by the name of springSecurityFilterChain. 
//The springSecurityFilterChain is created using @EnableWebSecurity. The problem is that you are missing the @Configuration annotation 
//(without it the Root ApplicationContext is not even going to try to load the SecurityConfiguration).
public class SecurityConfigurationInitializer extends AbstractSecurityWebApplicationInitializer {
 
}