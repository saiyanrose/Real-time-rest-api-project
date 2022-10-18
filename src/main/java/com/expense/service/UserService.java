package com.expense.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.expense.entity.Users;
import com.expense.exceptions.ExisitngUserException;
import com.expense.exceptions.ResourceNotFoundException;
import com.expense.repository.UserRepository;
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Users saveUser(Users users) {		
		Users existingUserEmail=userRepository.findByEmail(users.getEmail());
		if(existingUserEmail!=null) {
			throw new ExisitngUserException("Email already exist: "+users.getEmail());
		}else {
			users.setPassword(passwordEncoder.encode(users.getPassword()));
			return userRepository.save(users);
		}		
	}	
	
	public Users read(Integer id) {
		Optional<Users>existingUser=userRepository.findById(id);		
		if(existingUser.isPresent()) {			
			return existingUser.get();
		}else {
			throw new ResourceNotFoundException("Could not found user with id: "+id);
		}
	}
	
	public Users update(Users users,Integer id) {
		Users existUser=userRepository.findById(id).get();
		existUser.setName(users.getName()!=null ? users.getName() :existUser.getName());
		existUser.setEmail(users.getEmail()!=null ? users.getEmail() : existUser.getEmail());
		existUser.setPassword(users.getPassword()!=null ? passwordEncoder.encode(users.getPassword()) : existUser.getPassword());
		existUser.setAge(users.getAge()!=null ? users.getAge() : existUser.getAge());
		return userRepository.save(existUser);
	}
	
	public void delete(Integer id) {
		Optional<Users>existingUser=userRepository.findById(id);
		if(existingUser.isPresent()) {
			userRepository.deleteById(id);
		}else {
			throw new ResourceNotFoundException("User not found with id: "+id);
		}
	}
}
