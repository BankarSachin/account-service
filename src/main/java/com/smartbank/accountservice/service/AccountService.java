package com.smartbank.accountservice.service;

import com.smartbank.accountservice.dto.CustomerAccountDTO;
import com.smartbank.accountservice.entity.Account;
import com.smartbank.accountservice.entity.Customer;
import com.smartbank.accountservice.exception.AccsException;

public interface AccountService {

	/**
	 * creates customer in DB and creates 
	 * @param customerDto
	 * @throws {@link AccsException} if user already exists
	 * @return
	 */
	public Account createAccount(Customer customer,CustomerAccountDTO customerDto) throws AccsException;
}
