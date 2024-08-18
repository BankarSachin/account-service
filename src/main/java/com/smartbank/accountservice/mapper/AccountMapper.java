package com.smartbank.accountservice.mapper;

import com.smartbank.accountservice.dto.CustomerAccountDTO;
import com.smartbank.accountservice.entity.Account;
import com.smartbank.accountservice.entity.Customer;
import com.smartbank.accountservice.enums.AccountStatus;

public class AccountMapper {
	
	/**
	 * Private Constructor
	 */
	private AccountMapper() {
		//Private Constructor
	}
	
	public static Account toEntity(Customer customer,String accountNumber,CustomerAccountDTO customerDto) {
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAccountStatus(AccountStatus.ACTIVE);
		account.setAccountType(customerDto.getAccountType());
		account.setBranchCode(customerDto.getBranchCode());
		account.setCustomer(customer);
		return account;
	}
}
