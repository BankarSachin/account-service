/**
 * 
 */
package com.smartbank.accountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartbank.accountservice.dto.BalanceReponse;
import com.smartbank.accountservice.dto.CustomerAccountDTO;
import com.smartbank.accountservice.dto.DepositResponse;
import com.smartbank.accountservice.dto.TransactionRequest;
import com.smartbank.accountservice.dto.WithdrawalResponse;
import com.smartbank.accountservice.entity.Account;
import com.smartbank.accountservice.entity.Customer;
import com.smartbank.accountservice.exception.AccsException;
import com.smartbank.accountservice.exception.ExceptionCode;
import com.smartbank.accountservice.mapper.AccountMapper;
import com.smartbank.accountservice.repository.AccountRepository;

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
	public Account createAccount(Customer customer,CustomerAccountDTO customerDto) throws AccsException {
		final String methodName = "createAccount";
		try {
			final String accountNumber = generateAccountNumber(customerDto.getBranchCode());
			return accountRepository.save(AccountMapper.toEntity(customer, accountNumber, customerDto));
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


	@Override
	public DepositResponse deposit(String accounNumber, TransactionRequest transactionRequest) throws AccsException {
		/**
		 * Call Transaction Service through restClient
		 * After successful transaction perfrom ammount addition here 
		 * 
		 * ****/
		
		return null;
	}


	@Override
	public WithdrawalResponse withdrawal(String accounNumber, TransactionRequest transactionRequest) throws AccsException {
		/**
		 * Perform Baisc Check of Balance
		 * Call Transaction Service through restClient
		 * After successful transaction perfrom amount subsctraction here 
		 * **/
		return null;
	}


	@Override
	public BalanceReponse balance(String accounNumber) throws AccsException {
		/**
		 * Get Current Balance
		 * 
		 * **/
		return null;
	}
}
