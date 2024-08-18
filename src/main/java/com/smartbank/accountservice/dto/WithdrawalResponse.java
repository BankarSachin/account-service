package com.smartbank.accountservice.dto;

import java.math.BigDecimal;

import com.smartbank.accountservice.enums.TransactionStatus;

public record WithdrawalResponse(TransactionStatus transactionStatus,String transactionId,String accountNumber,BigDecimal amountWithdrawn,BigDecimal newBalance) {

}
