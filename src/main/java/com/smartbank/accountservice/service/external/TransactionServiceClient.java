package com.smartbank.accountservice.service.external;

import java.util.Map;
import java.util.UUID;

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

	/**
	 * TODO Need to be implemented properly
	 * Compensetory Transaction rollback service
	 * @param headers
	 * @param accountNumber
	 * @param transactionRequestID
	 * @return
	 * @throws AccsException
	 */
	TransactionResponse rollbackTxnEntry(Map<String, String> headers, String accountNumber, UUID transactionRequestID)
			throws AccsException;
}
