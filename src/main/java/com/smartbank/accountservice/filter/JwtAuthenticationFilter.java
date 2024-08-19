package com.smartbank.accountservice.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smartbank.accountservice.service.AuthzService;
import com.smartbank.accountservice.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


// Validate JWT Toke else return
// Get Customer id from JWT toke
// Get Account Number from request
// Validate customer had this accout number else return
// Get all permission for that account number 
// Put into authority
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	private final TokenService tokenService;
	
	private final AuthzService authzService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("In JWTAutentication Filter");
		try {
			log.info("In JWTAutentication Filter {} ",authzService.validateAccess("15", "4567000022"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		filterChain.doFilter(request, response);
	}

}
