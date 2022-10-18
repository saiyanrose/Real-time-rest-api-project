package com.expense;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.expense.entity.Users;
import com.expense.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Users> registerUser(@Valid @RequestBody Users users) {
		return new ResponseEntity<Users>(userService.saveUser(users), HttpStatus.CREATED);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Users> read(@PathVariable("id") Integer id) {
		return new ResponseEntity<Users>(userService.read(id), HttpStatus.OK);
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<Users> update(@RequestBody Users users, @PathVariable("id") Integer id) {
		return new ResponseEntity<Users>(userService.update(users, id), HttpStatus.OK);
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/user")
	public void delete(@RequestParam("id") Integer id) {
		userService.delete(id);
	}
	
	@PostMapping("/login")
	public ResponseEntity<HttpStatus> loginUser(@RequestBody Users users) {
		Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getEmail(),users.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
}
