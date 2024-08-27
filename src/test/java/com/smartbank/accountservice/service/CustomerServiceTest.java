package com.smartbank.accountservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.smartbank.accountservice.dto.CustomerAccountDTO;
import com.smartbank.accountservice.enums.AccountType;
import com.smartbank.accountservice.mapper.CustomerMapper;
import com.smartbank.accountservice.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Autowired
	private AccountService accountService;
	
	@InjectMocks
	private CustomerServiceImpl customerService;
	
	private CustomerAccountDTO customerAccountDTO;
	
	@BeforeEach
	public void setup() {
		customerAccountDTO = new CustomerAccountDTO();
		customerAccountDTO.setAccountType(AccountType.SAVINGS);
		customerAccountDTO.setAmount(BigDecimal.ZERO);
		customerAccountDTO.setBranchCode("ABCD0000988");
		customerAccountDTO.setEmail("SachinBankar1512@gmail.com");
		customerAccountDTO.setPassword("SecurePassword123");
		customerAccountDTO.setPhoneNumber("9987823428");
	}
	
	@Test
	void testRegisterNewCustomer() throws Exception {
		when(customerRepository.existsByEmail(customerAccountDTO.getEmail())).thenReturn(Boolean.FALSE);
		when(customerRepository.existsByPhoneNumber(customerAccountDTO.getPhoneNumber())).thenReturn(Boolean.FALSE);
	}
	
}
