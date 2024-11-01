package com.codewithekka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithekka.model.EmployeeDtls;
import com.codewithekka.repository.EmployeeRepository;

@Service
public class EmployeeDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private EmployeeRepository empRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		EmployeeDtls emp=empRepo.findByEmail(email);
		
		if(emp!=null) {
			return new CustomEmployeeDtls(emp);
		}
		
		throw new UsernameNotFoundException("User not available");
	}

}
