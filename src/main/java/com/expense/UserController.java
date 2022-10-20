package com.expense;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.expense.entity.Users;
import com.expense.security.CustomerUserDetailService;
import com.expense.security.JwtResponse;
import com.expense.security.jwtTokenUtil;
import com.expense.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerUserDetailService customerUserDetailService;

	@Autowired
	private jwtTokenUtil jwtTokenUtil;

	@PostMapping("/register")
	public ResponseEntity<Users> registerUser(@Valid @RequestBody Users users) {
		return new ResponseEntity<Users>(userService.saveUser(users), HttpStatus.CREATED);
	}

	@GetMapping("/user/profile")
	public ResponseEntity<Users> read() {
		return new ResponseEntity<Users>(userService.read(), HttpStatus.OK);
	}

	@PutMapping("/user/profile")
	public ResponseEntity<Users> update(@RequestBody Users users) {
		return new ResponseEntity<Users>(userService.update(users), HttpStatus.OK);
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/user/delete")
	public void delete() {
		userService.delete();
	}
	
	//ResponseEntity<HttpStatus>
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> loginUser(@RequestBody Users users) throws Exception {
		//Authentication authentication= new UsernamePasswordAuthenticationToken(users.getEmail(),users.getPassword());
		//SecurityContextHolder.getContext().setAuthentication(authentication);
		
		authenticate(users.getEmail(),users.getPassword());
		//generate jwt token
		//user details
		final UserDetails userDetails= customerUserDetailService.loadUserByUsername(users.getEmail());
		final String token=jwtTokenUtil.generateToken(userDetails);		
		
		return new ResponseEntity<JwtResponse>(new JwtResponse(token) ,HttpStatus.OK);
	}

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
		}catch (DisabledException e) {
			throw new Exception("User disabled");
		}catch (BadCredentialsException e) {
			throw new Exception("Bad credentials");
		}
		
	}
}
