//package com.alliance.discoveryServer.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//  @Value("${eureka.username}")
//  private String username;
//  @Value("${eureka.password}")
//  private String password;
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//
//  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//    authenticationManagerBuilder.inMemoryAuthentication()
//      .withUser(username).password(passwordEncoder().encode(password))
//      .authorities("USER");
//  }
//
//  public void configure(HttpSecurity httpSecurity) throws Exception {
//    httpSecurity
//      .csrf(AbstractHttpConfigurer::disable)
//      .authorizeHttpRequests(req -> req.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
//
//  }
//
//
//}
