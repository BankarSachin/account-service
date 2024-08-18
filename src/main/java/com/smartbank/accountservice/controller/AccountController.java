package com.smartbank.accountservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbank.accountservice.dto.CustomerAccountDTO;
import com.smartbank.accountservice.exception.AccsException;
import com.smartbank.accountservice.response.RegistrationResponse;
import com.smartbank.accountservice.service.AccountService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/account-service/v1/")
@Slf4j
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping(value = "/accounts/createAccount",consumes = { MediaType.APPLICATION_JSON_VALUE },produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RegistrationResponse> createAccount(@RequestHeader Map<String, String> headers,
		@Valid @RequestBody CustomerAccountDTO customerAccountDto) throws AccsException {
		final String methodName = "createAccount";
			log.info("{} - Request received for Account Creation", methodName);
			final RegistrationResponse registrationResponse = accountService.createAccount(customerAccountDto);
			log.info("{} - Account created successfully for {}", methodName,registrationResponse.getEmail());
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(registrationResponse);
	}
	
	@PostMapping(value = "/accounts/{accountnumber}/deposit",consumes = { MediaType.APPLICATION_JSON_VALUE },produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RegistrationResponse> deposit(@RequestHeader Map<String, String> headers,
		@Valid @RequestBody CustomerAccountDTO customerAccountDto) throws AccsException {
		final String methodName = "createAccount";
			log.info("{} - Request received for Account Creation", methodName);
			final RegistrationResponse registrationResponse = accountService.createAccount(customerAccountDto);
			log.info("{} - Account created successfully for {}", methodName,registrationResponse.getEmail());
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(registrationResponse);
	}
	
	
	@PostMapping(value = "/accounts/{accountnumber}/withdraw",consumes = { MediaType.APPLICATION_JSON_VALUE },produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RegistrationResponse> withdraw(@RequestHeader Map<String, String> headers,
		@Valid @RequestBody CustomerAccountDTO customerAccountDto) throws AccsException {
		final String methodName = "createAccount";
			log.info("{} - Request received for Account Creation", methodName);
			final RegistrationResponse registrationResponse = accountService.createAccount(customerAccountDto);
			log.info("{} - Account created successfully for {}", methodName,registrationResponse.getEmail());
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(registrationResponse);
	}
	
	
	@GetMapping(value = "/accounts/{accountnumber}/balance",produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<RegistrationResponse> balance(@RequestHeader Map<String, String> headers,
		@Valid @RequestBody CustomerAccountDTO customerAccountDto) throws AccsException {
		final String methodName = "createAccount";
			log.info("{} - Request received for Account Creation", methodName);
			final RegistrationResponse registrationResponse = accountService.createAccount(customerAccountDto);
			log.info("{} - Account created successfully for {}", methodName,registrationResponse.getEmail());
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(registrationResponse);
	}


}
