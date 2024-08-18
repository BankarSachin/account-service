package com.smartbank.accountservice.dto;
import java.math.BigDecimal;

import com.smartbank.accountservice.enums.TransactionStatus;

public record DepositResponse(TransactionStatus transactionStatus,String transactionId,String accountNumber,BigDecimal amountDeposited,BigDecimal newBalance) {
}
