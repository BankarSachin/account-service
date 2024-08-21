/**
 * 
 */
package com.smartbank.accountservice.service;

import static com.smartbank.accountservice.mapper.ReponseMappers.*;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartbank.accountservice.dto.BalanceReponse;
import com.smartbank.accountservice.dto.CustomerAccountDTO;
import com.smartbank.accountservice.dto.DepositResponse;
import com.smartbank.accountservice.dto.TransactionRequest;
import com.smartbank.accountservice.dto.TransactionResponse;
import com.smartbank.accountservice.dto.WithdrawalResponse;
import com.smartbank.accountservice.entity.Account;
import com.smartbank.accountservice.entity.Customer;
import com.smartbank.accountservice.enums.AccountStatus;
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
	
	@Autowired
	private TransactionServiceClient transactionServiceClient;
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
	@Transactional
	public DepositResponse deposit(Map<String, String> headers,String accounNumber, TransactionRequest transactionRequest) throws AccsException {
		final String methodName = "deposit";
		try {
			
			Account account = accountRepository.findByAccountNumber(accounNumber).orElseThrow(()-> new AccsException(ExceptionCode.ACC_ACCOUNT_NON_EXIST));
			
			if (account.getAccountStatus()!=AccountStatus.ACTIVE) {
				throw new AccsException(ExceptionCode.ACC_ACCOUNT_STATUS_INVALID);
			}
			final BigDecimal newBalance = account.getCurrentBalance().add(transactionRequest.getTransactionAmount());
			account.setCurrentBalance(newBalance);
			account = accountRepository.save(account);
			
			transactionRequest.setClosingBalance(account.getCurrentBalance());
			TransactionResponse transactionResponse =  transactionServiceClient.crateTxnEntry(headers, accounNumber, transactionRequest);
			log.info("{} - Deposit successful for {}. UTR number {}",methodName,accounNumber, transactionResponse.getUtrNumber());
			return txnToDepositRespMapper.apply(transactionResponse);
			
		} catch (AccsException e) {
			log.error("{} - Error occured while deposit flow {}", methodName,e.getMessage());
			throw e;
			//In Real banking application there would be revoke transaction call here
		} catch (Exception e) {
			log.error("{} - Error occured while deposit flow {}", methodName,e.getMessage(),e);
			throw new AccsException(ExceptionCode.ACC_ACCOUNT_DEPOSIT_UNKNOWN_EXCEPTION, e);
		}
	}


	@Override
	public WithdrawalResponse withdrawal(Map<String, String> headers,String accounNumber, TransactionRequest transactionRequest) throws AccsException {
		final String methodName = "withdrawal";
		try {
			
			Account account = accountRepository.findByAccountNumber(accounNumber).orElseThrow(()-> new AccsException(ExceptionCode.ACC_ACCOUNT_NON_EXIST));
			
			if (account.getAccountStatus()!=AccountStatus.ACTIVE) {
				throw new AccsException(ExceptionCode.ACC_ACCOUNT_STATUS_INVALID);
			}
			
			if(account.getCurrentBalance() < account.getCurrentBalance().subtract(transactionRequest.getTransactionAmount()) {
				throw new AccsException(ExceptionCode.ACC_ACCOUNT_STATUS_INVALID);
			}

			final BigDecimal newBalance = account.getCurrentBalance().add(transactionRequest.getTransactionAmount());
			account.setCurrentBalance(newBalance);
			account = accountRepository.save(account);
			
			transactionRequest.setClosingBalance(account.getCurrentBalance());
			TransactionResponse transactionResponse =  transactionServiceClient.crateTxnEntry(headers, accounNumber, transactionRequest);
			log.info("{} - Deposit successful for {}. UTR number {}",methodName,accounNumber, transactionResponse.getUtrNumber());
			return txnToDepositRespMapper.apply(transactionResponse);
			
		} catch (AccsException e) {
			log.error("{} - Error occured while deposit flow {}", methodName,e.getMessage());
			throw e;
			//In Real banking application there would be revoke transaction call here
		} catch (Exception e) {
			log.error("{} - Error occured while deposit flow {}", methodName,e.getMessage(),e);
			throw new AccsException(ExceptionCode.ACC_ACCOUNT_DEPOSIT_UNKNOWN_EXCEPTION, e);
		}
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
