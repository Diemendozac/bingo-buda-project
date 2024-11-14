package com.diemendozac.budabingo.security.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {
	private final String secretKey;
	private final String timeExpiration;

	public JwtUtils() {
		this.secretKey = System.getenv("JWT_SECRET_KEY");
		this.timeExpiration = System.getenv("JWT_TIME_EXPIRATION");
	}

	public Key getSignatureKey() {
		byte[] keyBytes = Base64.getMimeDecoder().decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
						.setSigningKey(getSignatureKey())
						.build()
						.parseClaimsJws(token)
						.getBody();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsFunction) {
		Claims claims = extractAllClaims(token);
		return claimsFunction.apply(claims);
	}


	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()
						.setClaims(extraClaims)
						.setSubject(userDetails.getUsername())
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
						.signWith(getSignatureKey(), SignatureAlgorithm.HS256)
						.compact();
	}

	public boolean isTokenValid(String token) {
		try {
			extractAllClaims(token);
			return true;
		} catch (Exception e) {
			log.error("Invalid token, error: ".concat(e.getMessage()));
			return false;
		}
	}

	public String getUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

}

