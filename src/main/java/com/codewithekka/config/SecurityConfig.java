package com.codewithekka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	@Autowired
	public AuthenticationSuccessHandler customSuccessHandler;
	
	@Bean
	public UserDetailsService getUserDetailsService() {
		return new EmployeeDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider getDaoAuthProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
		return daoAuthenticationProvider;
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(getDaoAuthProvider());
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
		
		http
		.authorizeHttpRequests()
		.requestMatchers("/admin/**").hasRole("ADMIN")
		.requestMatchers("/manager/**").hasRole("MANAGER")
		.requestMatchers("/employee/**").hasRole("EMPLOYEE")
		.requestMatchers("/**").permitAll()
		.and()
		.formLogin().loginPage("/").loginProcessingUrl("/login")
		.successHandler(customSuccessHandler).and().csrf().disable();
	
		
		return http.build();
	}
}
