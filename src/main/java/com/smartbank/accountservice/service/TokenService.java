package com.smartbank.accountservice.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smartbank.accountservice.dto.TokenResponse;
import com.smartbank.accountservice.exception.AccsException;

import io.jsonwebtoken.Claims;

public interface TokenService {

	/**
	 * Generate Token for autheticated user
	 * @param userDetails
	 * @return
	 */
	TokenResponse generateToken(UserDetails userDetails);
	
	/**
	 * Validate Toke
	 * @param token
	 * @return
	 * @throws AccsException
	 */
	Claims validateToken(String token) throws AccsException;
	
	String getUsernameFromToken(String token);

	/**
	 * Generate Token for autheticated user
	 * @param userDetails
	 * @return
	 */
	TokenResponse generateToken(String name, Collection<? extends GrantedAuthority> authorities) throws AccsException;
	
	
}
