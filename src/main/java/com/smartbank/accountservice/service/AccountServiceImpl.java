/**
 * 
 */
package com.smartbank.accountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartbank.accountservice.dto.CustomerAccountDTO;
import com.smartbank.accountservice.entity.Account;
import com.smartbank.accountservice.entity.Customer;
import com.smartbank.accountservice.exception.AccsException;
import com.smartbank.accountservice.exception.ExceptionCode;
import com.smartbank.accountservice.mapper.AccountMapper;
import com.smartbank.accountservice.repository.AccountRepository;
import com.smartbank.accountservice.response.RegistrationResponse;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles Account Related Operations
 *  1> Account Creation
 *  2> Deposit Account
 *  3> Withdrawal
 *  4> Balance Inquiry
 * @author Sachin
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	
	/**
	 * Creates Customer
	 * Creates Corresponding Account
	 * It is not best practice to create zero balance account with registration. But for MVP this has been done
	 * Better flow would be
	 * 1> Register customer
	 * 2> User Log into system and sends request to create account
	 * @throws AccsException 
	 * 
	 * 
	 */
	@Override
	@Transactional
	public RegistrationResponse createAccount(CustomerAccountDTO customerDto) throws AccsException {
		final String methodName = "createAccount";
		try {
			if (customerService.doesCustomerExistsByEmail(customerDto.getEmail())) {
				throw new AccsException(ExceptionCode.ACCS_CUSTOMER_ALREADY_EXISTS);
			}
			
			if (customerService.doesCustomerExistsByPhoneNumber(customerDto.getPhoneNumber())) {
				throw new AccsException(ExceptionCode.ACCS_CUSTOMER_ALREADY_EXISTS);
			}
			final String accountNumber = generateAccountNumber(customerDto.getBranchCode());
			Customer customer =  customerService.registerCustomer(customerDto);
			Account account = accountRepository.save(AccountMapper.toEntity(customer, accountNumber, customerDto));
			customer.setAccount(account);
			customerService.updateCustomer(customer);
			return new RegistrationResponse(customer);
		} catch (AccsException e) {
			log.error("{} - error {}",methodName,e.getMessage(),e);
			throw e;
		} catch (Exception e) {
			log.error("{} - error {}",methodName,e.getMessage(),e);
			throw new AccsException(ExceptionCode.ACCS_UNKNOWN_EXCEPTION, e);
		}
	}

	
	/**
	 * Generates Account number which is Fixed Length
	 * @param branchCode
	 * @return
	 */
	private String generateAccountNumber(String branchCode) {
		Long accountSequenceNumber = accountRepository.getNextSequenceValue();
		final String branchCodepart = branchCode.substring(branchCode.length()-4, branchCode.length()); 
		return branchCodepart + String.format("%06d", accountSequenceNumber);
	}
}
