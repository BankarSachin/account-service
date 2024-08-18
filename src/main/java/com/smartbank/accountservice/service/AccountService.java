package com.smartbank.accountservice.service;

import com.smartbank.accountservice.dto.CustomerAccountDTO;
import com.smartbank.accountservice.exception.AccsException;
import com.smartbank.accountservice.response.RegistrationResponse;

public interface AccountService {

	/**
	 * creates customer in DB and creates 
	 * @param customerDto
	 * @throws {@link AccsException} if user already exists
	 * @return
	 */
	public RegistrationResponse createAccount(CustomerAccountDTO customerDto) throws AccsException;
}
