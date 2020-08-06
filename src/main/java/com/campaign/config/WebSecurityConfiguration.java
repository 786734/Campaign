package com.campaign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
 

@Configuration
public class WebSecurityConfiguration  extends WebSecurityConfigurerAdapter {
 
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }
 
    // Security based on role.
    // Here the user role will be shown the Http 403 - Access Denied Error. But the admin role will be shown the successful page.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.csrf().disable();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/**").hasAnyRole("ADMIN","USER")
        .antMatchers(HttpMethod.POST,"/**").hasAnyRole("ADMIN")
        .antMatchers(HttpMethod.PUT,"/**").hasAnyRole("ADMIN").anyRequest().fullyAuthenticated().and().httpBasic();
    }
 
    @Bean
   public static NoOpPasswordEncoder passwordEncoder() {
      return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
  }

}
