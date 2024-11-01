package com.codewithekka.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.codewithekka.model.EmployeeDtls;

public class CustomEmployeeDtls implements UserDetails{
	private EmployeeDtls employee;
	public CustomEmployeeDtls(EmployeeDtls employee) {
		super();
		this.employee = employee;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simpleGrantedAuthority=
				new SimpleGrantedAuthority(employee.getRole());
		return Arrays.asList(simpleGrantedAuthority);}
	@Override
	public String getPassword() {return employee.getPassword();}
	@Override
	public String getUsername() {return employee.getEmail();}
	@Override
	public boolean isAccountNonExpired() {return true;}
	@Override
	public boolean isAccountNonLocked() {return true;}
	@Override
	public boolean isCredentialsNonExpired() {return true;}
	@Override
	public boolean isEnabled() {return true;}
}
