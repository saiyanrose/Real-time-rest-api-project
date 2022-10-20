package com.expense.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
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
		
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
			.signWith(SignatureAlgorithm.HS512,secret)
			.compact();		
	}
	
	public String getUsername(String token) {
		return getClaimsFromToken(token,Claims::getSubject);
	}
	
	private <T> T getClaimsFromToken(String token,Function<Claims,T> claimResolver) {
		final Claims claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claimResolver.apply(claims);		
	}

	public boolean validateToken(String jwtToken, UserDetails userDetails) {
		final String username=getUsername(jwtToken);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);		
	}

	private boolean isTokenExpired(String jwtToken) {
		final Date expiration=getExpiratonDateToken(jwtToken);
		return expiration.before(new Date());
	}

	private Date getExpiratonDateToken(String jwtToken) {
		return getClaimsFromToken(jwtToken,Claims::getExpiration);		
	}

}
