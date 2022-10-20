package com.expense.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class jwtTokenUtil {
	
	private static final long JWT_TOKEN_VALIDITY=5*60*60;
	
	//from configuration
	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(UserDetails userDetails) {
		Map<String,Object>claims=new HashMap<>();
		System.out.println(secret);
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
			.signWith(SignatureAlgorithm.HS512,secret)
			.compact();
		
	}

}
