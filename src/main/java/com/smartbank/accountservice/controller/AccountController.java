package com.smartbank.accountservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbank.accountservice.dto.BalanceReponse;
import com.smartbank.accountservice.dto.DepositResponse;
import com.smartbank.accountservice.dto.TransactionRequest;
import com.smartbank.accountservice.dto.WithdrawalResponse;
import com.smartbank.accountservice.exception.AccsException;
import com.smartbank.accountservice.service.AccountService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/")
@Slf4j
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping(value = "/accounts/{accountnumber}/deposit",consumes = { MediaType.APPLICATION_JSON_VALUE },produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<DepositResponse> deposit(@RequestHeader Map<String, String> headers, 
												   @PathVariable(name = "accountnumber",required = true) String accountNumber,
												   @Valid @RequestBody TransactionRequest transactionRequest) throws AccsException {
			final String methodName = "deposit";
			log.info("{} - Deposit request received for {} account", methodName,accountNumber);
			final DepositResponse depositResponse = accountService.deposit(accountNumber, transactionRequest);
			log.info("{} - Amount {} deposited to {} account", methodName,transactionRequest.getTransactionAmount(),accountNumber);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(depositResponse);
	}
	
	
	@PostMapping(value = "/accounts/{accountnumber}/withdrawal",consumes = { MediaType.APPLICATION_JSON_VALUE },produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<WithdrawalResponse> withdrawal(@RequestHeader Map<String, String> headers, 
			   											@PathVariable(name = "accountnumber",required = true) String accountNumber,
			   											@Valid @RequestBody TransactionRequest transactionRequest) throws AccsException {
			final String methodName = "withdrawal";
			log.info("{} - Withdrawal request received for {} account", methodName,accountNumber);
			final WithdrawalResponse withdrawalResponse = accountService.withdrawal(accountNumber, transactionRequest);
			log.info("{} - Amount {} deposited to {} account", methodName,transactionRequest.getTransactionAmount(),accountNumber);
			return ResponseEntity
						.status(HttpStatus.OK)
						.body(withdrawalResponse);
}
	
	
	@GetMapping(value = "/accounts/{accountnumber}/balance",produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<BalanceReponse> balance(@RequestHeader Map<String, String> headers,
												  @PathVariable(name = "accountnumber",required = true) String accountNumber) throws AccsException {
		final String methodName = "createAccount";
			log.info("{} - Balance request received for {} account", methodName,accountNumber);
			final BalanceReponse balanceReponse = accountService.balance(accountNumber);
			log.info("{} - Successfully fetched balance amount for {} account", methodName,accountNumber);
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(balanceReponse);
	}


}
