package com.smartbank.accountservice.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login-service/v1")
public class LoginController {
	
	@PostMapping(value = "/authenticate",consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> authenticate(){
		return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(new String("Successful"));
	}

}
