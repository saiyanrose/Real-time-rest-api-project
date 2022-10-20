package com.expense.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private jwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private CustomerUserDetailService customerUserDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//token from the header
		//extract token
		//extratc username
		String jwtToken=null;
		String username=null;
		final String reqTokenHeader=request.getHeader("Authorization");
		System.out.println("reqTokenHeader: "+reqTokenHeader);
		if(reqTokenHeader!=null && reqTokenHeader.startsWith("Bearer ")) {
			//remove Bearer
			jwtToken=reqTokenHeader.substring(7);
			
			try {
				username=jwtTokenUtil.getUsername(jwtToken);				
			}catch (IllegalArgumentException e) {
				throw new RuntimeException("Unable to get jwt token");
			}catch (ExpiredJwtException e) {
				throw new RuntimeException("jwt token has expired");
			}
		}
		
		//validate token
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			//validate token
			UserDetails userDetails= customerUserDetailService.loadUserByUsername(username);
			
			if(jwtTokenUtil.validateToken(jwtToken,userDetails)) {
				UsernamePasswordAuthenticationToken authToken=new
						UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);			
			}
		}
		filterChain.doFilter(request, response);
	}

}
