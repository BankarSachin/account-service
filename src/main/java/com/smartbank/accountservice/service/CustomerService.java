package com.smartbank.accountservice.service;

import com.smartbank.accountservice.dto.CustomerAccountDTO;
import com.smartbank.accountservice.dto.LoginRequest;
import com.smartbank.accountservice.dto.LoginResponse;
import com.smartbank.accountservice.entity.Customer;
import com.smartbank.accountservice.exception.AccsException;

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
	Customer registerCustomer(CustomerAccountDTO customerDto);
	
	Customer updateCustomer(Customer customer) throws AccsException;
	
	LoginResponse login(LoginRequest loginrequest) throws AccsException;
	
	boolean doesCustomerExistsByEmail(String email);
	
	boolean doesCustomerExistsByPhoneNumber(String phoneNumber);
}
