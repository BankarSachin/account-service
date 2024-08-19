package com.smartbank.accountservice.service;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.smartbank.accountservice.dto.TokenResponse;
import com.smartbank.accountservice.exception.AccsException;
import com.smartbank.accountservice.exception.ExceptionCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService{

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;
    
   	@Override
	public TokenResponse generateToken(UserDetails userDetails) {
		return null;
	}

	@Override
	public Claims validateToken(String token) throws AccsException {
		return null;
	}

	@Override
	public String getUsernameFromToken(String token) {
		return null;
	}

	@Override
	public TokenResponse generateToken(String name, Collection<? extends GrantedAuthority> authorities)
			throws AccsException {
		final String methodName = "generateToken";
		try {
	         final String token = Jwts.builder()
	        		 	.setHeaderParam("typ", "JWT")
	            		.setIssuer("SMBK")
	            		.setSubject(name)
	                    .claim("permissions", authorities)
	                    .setIssuedAt(new Date())
	                    .setExpiration(new Date((new Date()).getTime() + expiration))
	                    .signWith(SignatureAlgorithm.HS512, secret).compact();
	          return new TokenResponse(token);
		} catch (Exception e) {
			log.error("{} - Unknown error while generting token {}",methodName,e.getMessage());
			throw new AccsException(ExceptionCode.ACCS_UNKNOWN_EXCEPTION, e);
		}
	}
}
