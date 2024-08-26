package com.smartbank.accountservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.smartbank.accountservice.enums.AccountStatus;
import com.smartbank.accountservice.enums.AccountType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import lombok.Data;

/**
 * Holds Account Information
 * <p>
 * One customer can have multiple accounts 
 * @author Sachin
 */
@Entity
@Data
@Table(name = "accounts")
public class Account {

	@Id
	@Column(name="account_number",nullable = false,length = 10)	
	private String accountNumber;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,targetEntity = Customer.class)
	@JoinColumn(name="customer_id",
	            nullable = false,
	            referencedColumnName = "customer_id",
	            updatable = false)
	private Customer customer;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "account_type",nullable = false)
	private AccountType accountType;
	
	@Column(name = "branch_code",nullable = false,length = 11)
	private String branchCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "account_status",nullable = false)
	private AccountStatus accountStatus;
	
	@Column(name = "current_balance",nullable = false)
	private BigDecimal currentBalance;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, updatable = false)
	private LocalDateTime createDate;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date", nullable = false)
	private LocalDateTime updateddDate;
	
	@Version
	@Column(name = "lock")
	private Integer version;
}
