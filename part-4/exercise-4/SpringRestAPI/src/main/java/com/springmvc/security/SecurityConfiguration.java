package com.springmvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @Configuration -class which enables Spring's Security -library.
 * It extends WebSecurityConfigurerAdapter -class. This class is 
 * base class that allows you to customize your security by @Overriding
 * functions.
 * 
 * This security-class secures all endpoints except GET-requests 
 * which are available for all users.
 * 
 * Using Postman; open "Authorization" -tab, change type to "Basic auth"
 * and for the credentials give:
 *  @Username = "admin"
 *  @Password = "admin"
 * With these credentials you can use all endpoints.
 * 
 * @author samuli
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  /**
   * @Overrided configuration-function in which we tell the security
   * that all the api-endpoints will be secured and only a role "ADMIN" 
   * has rights to HTTP-query them. Only GET-requests are available for 
   * everybody.
   * 
   * Lastly we add cross-origin support so these endpoints can be called
   * from different domain.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().and()
      .authorizeRequests()
      .antMatchers(HttpMethod.POST, "/api/book/**").hasRole("ADMIN")
      .antMatchers(HttpMethod.DELETE, "/api/book/**").hasRole("ADMIN")
      .antMatchers(HttpMethod.PUT, "/api/book/**").hasRole("ADMIN")
      .antMatchers(HttpMethod.POST, "/api/bookgroup/**").hasRole("ADMIN")
      .antMatchers(HttpMethod.DELETE, "/api/bookgroup/**").hasRole("ADMIN")
      .antMatchers(HttpMethod.PUT, "/api/bookgroup/**").hasRole("ADMIN")
      .and().csrf().disable();
  }
  
  /**
   * Another @Overrided configuration-function where it tells 
   * the application to ignore authorization for all
   * the GET-methods.
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
      .antMatchers(HttpMethod.GET, "/api/book/**")
      .antMatchers(HttpMethod.GET, "/api/bookgroup/**");
  }
  
  /**
   * Function which loads the user-specific data. It'll checks
   * that given data includes static values for username and password
   * ("admin"). If the values are correct, user will be given admin-rights.
   * 
   * By adding it as @Bean, Spring security uses it to obtain 
   * the user to authenticate. 
   */
  @Bean
  public UserDetailsService userDetailsService() {
    // Memory persistent class useful for testing and demonstration of authorization
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    String encodedPassword = passwordEncoder().encode("admin");
    manager.createUser(User.withUsername("admin").password(encodedPassword)
      .roles("ADMIN").build());
    return manager;
  }
  
  /**
   * Function which returns an object, that can be used to decrypt
   * password. Not useful in testing situation, but merely an
   * implementation.
   * 
   * @return BCryptPasswordEncoder -object
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  
  
}
