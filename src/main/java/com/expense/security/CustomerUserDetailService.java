package com.expense.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.expense.entity.Users;
import com.expense.repository.UserRepository;
@Service
public class CustomerUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users users=userRepository.findByEmail(email);		
		if(users==null) {
			throw new UsernameNotFoundException("User not found with email: "+email);
		}		
		return new User(users.getEmail(),users.getPassword(),new ArrayList<>());
	}

}
