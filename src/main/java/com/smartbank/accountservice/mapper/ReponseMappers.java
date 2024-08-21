package com.smartbank.accountservice.mapper;

import java.util.function.Function;

import com.smartbank.accountservice.dto.DepositResponse;
import com.smartbank.accountservice.dto.TransactionResponse;

public class ReponseMappers {
	
	private ReponseMappers() {
		throw new IllegalAccessError("Illegal Operation.Utility Class");
	}
	
    //TransactionStatus transactionStatus,String utrNumber,String accountNumber,BigDecimal amountDeposited,BigDecimal newBalance
	public static final Function<TransactionResponse, DepositResponse> txnToDepositRespMapper = txnResp -> new DepositResponse(
				txnResp.getTransactionStatus()
				,txnResp.getUtrNumber()
				,txnResp.getTransactionAccount()
				,txnResp.getTransactionAmount()
				,txnResp.getClosingBalance()
				,txnResp.getTransactionDescription()
		);
};
