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

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

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
  
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
      .antMatchers(HttpMethod.GET, "/api/book/**")
      .antMatchers(HttpMethod.GET, "/api/bookgroup/**");
  }
  
  @Bean
  public UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    String encodedPassword = passwordEncoder().encode("admin");
    manager.createUser(User.withUsername("admin").password(encodedPassword)
      .roles("ADMIN").build());
    return manager;
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  
  
}
