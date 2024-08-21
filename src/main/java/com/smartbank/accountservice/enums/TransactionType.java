package com.smartbank.accountservice.enums;

/**
 * Any account can have only two fundamental types of transactions: Credit (CR)
 * and Debit (DR).
 * 
 * @author Sachin
 */
public enum TransactionType {
	CREDIT("CR"), DEBIT("DR");

	private final String value;

	private TransactionType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
