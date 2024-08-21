package com.smartbank.accountservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.smartbank.accountservice.enums.TransactionStatus;
import com.smartbank.accountservice.enums.TransactionType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionRequest {
	
	private UUID utrNumber;
	
	private TransactionStatus transactionStatus;
	
	@NotNull(message = "Account number required for transaction entry")
	private String accountNumber;
	
	@NotNull(message = "Transaction amount required for transaction entry")
	private BigDecimal transactionAmount;
	
	@NotNull(message = "Closing balance required for transaction entry")
	private BigDecimal closingBalance;
	
	private String transactionSummary;
	
	@NotNull(message = "Transaction type is required")
	private TransactionType transactionType;
	
	@NotNull(message = "Transacction Timestamp Needed")
	private LocalDateTime transactionDate;
}
