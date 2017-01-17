package _001.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private static String REALM="MY_TEST_REALM";

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("bimal").password("bimal").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {  
		http.authorizeRequests()
		.antMatchers("/user/*").hasRole("ADMIN")
		.and().httpBasic().authenticationEntryPoint(getBasicAuthEntryPoint()).realmName(REALM)
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//We don't need sessions to be created.
		.and().csrf().disable(); //Cross-Site Request Forgery (CSRF): We should not disable it. Without disabling this, we cannot perform any edit 
								//operations. Only GET requests works. client sent the csrf token with the request, which we must sent back with the response
	}

	@Bean
	public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
		return new CustomBasicAuthenticationEntryPoint();
	}

}