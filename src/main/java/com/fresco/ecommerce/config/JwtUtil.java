package com.fresco.ecommerce.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fresco.ecommerce.models.User;
import com.fresco.ecommerce.servcie.UserAuthService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    private static final long EXPIRE_DURATION = 30 * 60 * 1000; // 30 minutes
	
	@Value("${jwt.secret}")
	String SECRET_KEY;
	
	@Autowired
	UserAuthService userAuthService;
	

	public User getUser(final String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//		System.out.println("getUser called");
		String username = claims.getSubject();
		return userAuthService.loadUserByUsername(username);
	}

	public String generateToken(String username) {
		User user = userAuthService.loadUserByUsername(username);
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", user.getAuthorities());
		user.getAuthorities();
		String token = Jwts.builder()
				.setClaims(claims)              
				.setSubject(String.format("%s", username))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
		System.out.println("Token: "+ token);
		return token;
	}

	public void validateToken(final String token) {
		System.out.println("vlidateToken called");
	}
}
