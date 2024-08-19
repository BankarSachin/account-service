package com.smartbank.accountservice.service;

import com.smartbank.accountservice.dto.CustomerAccountDTO;
import com.smartbank.accountservice.dto.LoginRequest;
import com.smartbank.accountservice.dto.TokenResponse;
import com.smartbank.accountservice.exception.AccsException;
import com.smartbank.accountservice.response.RegistrationResponse;

/**
 * Handles Customer Related operations
 *  1> Create User
 * @author Sachin
 */
public interface CustomerService {

	/**
	 * This performs two tasks
	 * Create Customer
	 * Create zero balance account
	 * @param customerDto Input from front end to create/register customer
	 * @return Generated Customer details
	 */
	RegistrationResponse registerCustomer(CustomerAccountDTO customerDto) throws AccsException;
	
	TokenResponse authenticate(LoginRequest loginrequest) throws AccsException;
	
	boolean doesCustomerExistsByEmail(String email);
	
	boolean doesCustomerExistsByPhoneNumber(String phoneNumber);
}
