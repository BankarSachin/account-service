package com.smartbank.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbank.accountservice.dto.LoginRequest;
import com.smartbank.accountservice.dto.LoginResponse;
import com.smartbank.accountservice.exception.AccsException;
import com.smartbank.accountservice.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/customer-service/v1/")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	
    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(HttpServletRequest request,@RequestBody LoginRequest loginRequest)
            throws AccsException {
    	final LoginResponse loginResponse = customerService.login(loginRequest);
    	return ResponseEntity.ok(loginResponse);
    }
}
