package com.fresco.ecommerce.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fresco.ecommerce.models.User;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    private static final long EXPIRE_DURATION = 10 * 60 * 1000; // 10 minutes
	
	@Value("${jwt.secret}")
	String SECRET_KEY;

	public User getUser(final String token) {
		return null;
	}

	public String generateToken(String username) {
		String token = Jwts.builder()
                .setSubject(String.format("%s", username))
                .setIssuer("CodeJava")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
		return token;
	}

	public void validateToken(final String token) {
	}
}
