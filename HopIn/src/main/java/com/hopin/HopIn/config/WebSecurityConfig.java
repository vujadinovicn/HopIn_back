package com.hopin.HopIn.config;

import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.hopin.HopIn.security.auth.RestAuthenticationEntryPoint;
import com.hopin.HopIn.security.auth.TokenAuthenticationFilter;
import com.hopin.HopIn.services.UserServiceImpl;
import com.hopin.HopIn.util.TokenUtils;

@Configuration
@EnableWebSecurity

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig {

	@Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }
	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
  	
  
 	@Bean
 	public DaoAuthenticationProvider authenticationProvider() {
 	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
 	  
 	    authProvider.setUserDetailsService(userDetailsService());
 	    authProvider.setPasswordEncoder(passwordEncoder());
 	 
 	    return authProvider;
 	}
 	
 	
 	
 	@Autowired
 	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
 
 	
 	@Bean
 	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
 	    return authConfig.getAuthenticationManager();
 	}
 	
	@Autowired
	private TokenUtils tokenUtils;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);

    	http.authorizeRequests()
			.requestMatchers("/h2-console/**").permitAll()	
			.and().authorizeRequests()
			.requestMatchers("/api/user/login").permitAll()
			.and().authorizeRequests()
			.requestMatchers("/api/passenger").permitAll()
			.anyRequest().authenticated().and()
			.cors().and()
			.addFilterBefore(new TokenAuthenticationFilter(tokenUtils,  userDetailsService()), BasicAuthenticationFilter.class);
		
		http.csrf().disable(); 

//		SA NEMANJINE GRANE
//			.and()
//    		.authorizeRequests()
//			.requestMatchers("/api/user/login").permitAll()
//			.and()
//			.cors().and() 
//			.addFilterBefore(new TokenAuthenticationFilter(tokenUtils,  userDetailsService()), BasicAuthenticationFilter.class);
//
//    	http.csrf().disable();

		http.headers().frameOptions().disable();
        http.authenticationProvider(authenticationProvider());
            
        return http.build();
    }
           
    // metoda u kojoj se definisu putanje za igorisanje autentifikacije
    @Bean           
    public WebSecurityCustomizer webSecurityCustomizer() {     
    	// Dozvoljena POST metoda na ruti /auth/login, za svaki drugi tip HTTP metode greska je 401 Unauthorized
    	return (web) -> web.ignoring().requestMatchers(HttpMethod.POST, "/api/user/login").requestMatchers(HttpMethod.GET, "/api/user/{id}").requestMatchers(HttpMethod.POST, "/api/passenger").requestMatchers(HttpMethod.GET, "/api/passenger/activate/{activationId}").requestMatchers(HttpMethod.GET, "/api/passenger/activate/resend")
    			.requestMatchers(HttpMethod.GET, "/api/user/{id}/resetPassword").requestMatchers(HttpMethod.GET, "/api/user/{email}/resetPasswordEmail").requestMatchers(HttpMethod.PUT, "/api/user/{id}/resetPassword").requestMatchers(HttpMethod.POST, "/api/unregisteredUser").requestMatchers(HttpMethod.GET, "/api/driver/active-vehicles").requestMatchers("/api/socket/**")
    			.requestMatchers(HttpMethod.POST, "/api/unregisteredUser").requestMatchers(HttpMethod.GET, "/api/driver/{id}/vehicle");	
    }
}
