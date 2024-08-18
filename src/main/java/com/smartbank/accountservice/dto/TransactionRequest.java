package com.smartbank.accountservice.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransactionRequest {
	private BigDecimal transactionAmount;
	private String transactionSummary;
}
