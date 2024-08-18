package com.smartbank.accountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartbank.accountservice.dto.CustomerAccountDTO;
import com.smartbank.accountservice.dto.LoginRequest;
import com.smartbank.accountservice.dto.LoginResponse;
import com.smartbank.accountservice.entity.Customer;
import com.smartbank.accountservice.exception.AccsException;
import com.smartbank.accountservice.exception.ExceptionCode;
import com.smartbank.accountservice.mapper.CustomerMapper;
import com.smartbank.accountservice.repository.CustomerRepository;

import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles customer related operations like Register Customer,Update Customer Data
 * @author Sachin
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Override
	public Customer registerCustomer(CustomerAccountDTO customerDto) {
		final String methodName = "registerCustomer";
		try {
			final Customer registeredCustomer = customerRepository.save(customerMapper.toEntity(customerDto));
			log.info("{} - Customer registered successfully! {}", methodName,registeredCustomer);
			return registeredCustomer;
		} catch (Exception e) {
			return null;
		}
	}
	
	public Customer updateCustomer(Customer customer) throws AccsException {
		final String methodName = "updateCustomer";
		try {
			return customerRepository.save(customer);
		} catch (IllegalArgumentException | OptimisticLockException e) {
			log.error("{} - error {}",methodName,e.getMessage(),e);
			throw new AccsException(ExceptionCode.ACCS_DB_EXCEPTION, e);
		}
	}
	
	@Override
	public boolean doesCustomerExistsByEmail(String email) {
		return customerRepository.existsByEmail(email);
	}

	@Override
	public boolean doesCustomerExistsByPhoneNumber(String phoneNumber) {
		return customerRepository.existsByPhoneNumber(phoneNumber);
	}

	@Override
	public LoginResponse login(LoginRequest loginrequest) throws AccsException {
		return null;
	}
}