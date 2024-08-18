package com.smartbank.accountservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbank.accountservice.dto.CustomerAccountDTO;
import com.smartbank.accountservice.dto.LoginRequest;
import com.smartbank.accountservice.dto.LoginResponse;
import com.smartbank.accountservice.exception.AccsException;
import com.smartbank.accountservice.response.RegistrationResponse;
import com.smartbank.accountservice.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/customer-service/v1/")
@Slf4j
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping(value = "/register",consumes = { MediaType.APPLICATION_JSON_VALUE },produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RegistrationResponse> register(@RequestHeader Map<String, String> headers,
		@Valid @RequestBody CustomerAccountDTO customerAccountDto) throws AccsException {
		final String methodName = "createAccount";
			log.info("{} - Request received for Customer registration", methodName);
			final RegistrationResponse registrationResponse = customerService.registerCustomer(customerAccountDto);
			log.info("{} - Customer registered successfully with zero balance Account for {}", methodName,registrationResponse.getEmail());
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(registrationResponse);
	}
	
    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> authenticate(HttpServletRequest request,@RequestBody LoginRequest loginRequest)
            throws AccsException {
    	final LoginResponse loginResponse = customerService.login(loginRequest);
    	return ResponseEntity.ok(loginResponse);
    }
}
