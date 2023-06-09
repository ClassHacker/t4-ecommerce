package com.fresco.ecommerce.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fresco.ecommerce.models.User;
import com.fresco.ecommerce.servcie.UserAuthService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	UserAuthService authService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("JWT");
		System.out.println(header);
		if(request.getServletPath().contains("public") || request.getServletPath().contains("h2")){
			filterChain.doFilter(request, response);
			return;
		}
		if(header != null && !header.isEmpty() && header.startsWith("Bearer")){
			String token = header.substring(7);
			User user = jwtUtil.getUser(token);
			filterChain.doFilter(request, response);
			return;
		}
		System.out.println("Authorization header is missing or invalid");
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acces Denied"); 
	}
}
