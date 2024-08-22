package com.smartbank.accountservice.service.external;

import java.util.Map;

import com.smartbank.accountservice.dto.TransactionRequest;
import com.smartbank.accountservice.dto.TransactionResponse;
import com.smartbank.accountservice.exception.AccsException;

public interface TransactionServiceClient {

	/**
	 * Calls Tranaction service to create transaction entry
	 * @param transactionRequest
	 * @return
	 * @throws AccsException
	 */
	TransactionResponse crateTxnEntry( Map<String, String> headers,String accountNumber,TransactionRequest transactionRequest) throws AccsException;
}
