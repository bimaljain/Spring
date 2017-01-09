package _001.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
	//like authentication
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("bimal").password("bimal").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("db").password("db").roles("DBA");
    }
     
    //like authorization    
    @Override
    protected void configure(HttpSecurity http) throws Exception {  
      http.authorizeRequests()
        .antMatchers("/", "/").permitAll() 
        .antMatchers("/001/admin").access("hasRole('ADMIN')")
        .antMatchers("/001/db").access("hasRole('DBA')")
        //Spring login page
        //if user/password don't match, Spring MVC will keep redirecting to the login page with the error message
        .and().formLogin()
        //if user/password matched but the role is not matching, you will be redirected to the access_denied custom page, configured below
        .and().exceptionHandling().accessDeniedPage("/001/access_denied"); 
    }
}