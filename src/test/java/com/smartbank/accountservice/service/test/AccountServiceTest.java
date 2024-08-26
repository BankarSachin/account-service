package com.smartbank.accountservice.service.test;
import com.smartbank.accountservice.dto.*;
import com.smartbank.accountservice.entity.Account;
import com.smartbank.accountservice.entity.Customer;
import com.smartbank.accountservice.enums.AccountStatus;
import com.smartbank.accountservice.enums.NotificationType;
import com.smartbank.accountservice.enums.TransactionType;
import com.smartbank.accountservice.exception.AccsException;
import com.smartbank.accountservice.exception.ExceptionCode;
import com.smartbank.accountservice.mapper.AccountMapper;
import com.smartbank.accountservice.repository.AccountRepository;
import com.smartbank.accountservice.service.AccountService;
import com.smartbank.accountservice.service.external.NotificationServiceClient;
import com.smartbank.accountservice.service.external.TransactionServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.smartbank.accountservice.mapper.AccountMapper.toTxnEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionServiceClient transactionServiceClient;

    @Mock
    private NotificationServiceClient notificationServiceClient;

    @InjectMocks
    private AccountService accountService;

    private Map<String, String> headers;
    private String accountNumber;
    private AccountTransaction accountTransaction;
    private Account account;
    private TransactionResponse transactionResponse;

    @BeforeEach
    void setUp() {
        headers = Map.of("Authorization", "Bearer token");
        accountNumber = "1234567890";
        accountTransaction = new AccountTransaction();
        accountTransaction.setTransactionAmount(BigDecimal.valueOf(1000));

        account = new Account();
        account.setAccountNumber(accountNumber);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setCurrentBalance(BigDecimal.valueOf(5000));

        transactionResponse = new TransactionResponse();
        transactionResponse.setUtrNumber(UUID.fromString(accountNumber));
        transactionResponse.setTransactionAmount(BigDecimal.valueOf(1000));
        transactionResponse.setClosingBalance(BigDecimal.valueOf(6000));
    }

    @Test
    void testDeposit() throws AccsException {
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
        when(transactionServiceClient.crateTxnEntry(eq(headers), eq(accountNumber), any())).thenReturn(transactionResponse);
        when(accountRepository.save(account)).thenReturn(account);

        DepositResponse response = accountService.deposit(headers, accountNumber, accountTransaction);

        assertEquals(UUID.fromString("1234567890"), response.utrNumber());
        assertEquals(BigDecimal.valueOf(6000), response.newBalance());

        verify(accountRepository).save(account);
        verify(transactionServiceClient).crateTxnEntry(eq(headers), eq(accountNumber), any());
        verify(notificationServiceClient).notifyTransfer(eq(headers), eq(accountNumber), any(NotificationRequest.class));
    }

//    @Test
//    void testDepositAccountNotFound() {
//        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());
//
//        assertThrows(AccsException.class, () -> accountService.deposit(headers, accountNumber, accountTransaction));
//
//        verify(accountRepository, never()).save(any());
//        verify(transactionServiceClient, never()).crateTxnEntry(any(), any(), any());
//        verify(notificationServiceClient, never()).notifyTransfer(any(), any(), any());
//    }
//
//    @Test
//    void testWithdrawal() throws AccsException {
//        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
//        when(transactionServiceClient.crateTxnEntry(eq(headers), eq(accountNumber), any())).thenReturn(transactionResponse);
//        when(accountRepository.save(account)).thenReturn(account);
//
//        WithdrawalResponse response = accountService.withdrawal(headers, accountNumber, accountTransaction);
//
//        assertEquals("UTR123", response.utrNumber());
//        assertEquals(BigDecimal.valueOf(6000), response.getClosingBalance());
//
//        verify(accountRepository).save(account);
//        verify(transactionServiceClient).crateTxnEntry(eq(headers), eq(accountNumber), any());
//        verify(notificationServiceClient).notifyTransfer(eq(headers), eq(accountNumber), any(NotificationRequest.class));
//    }
//
//    @Test
//    void testWithdrawalInsufficientBalance() {
//        account.setCurrentBalance(BigDecimal.valueOf(500));
//        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
//
//        assertThrows(AccsException.class, () -> accountService.withdrawal(headers, accountNumber, accountTransaction));
//
//        verify(accountRepository, never()).save(any());
//        verify(transactionServiceClient, never()).crateTxnEntry(any(), any(), any());
//        verify(notificationServiceClient, never()).notifyTransfer(any(), any(), any());
//    }
//    
//    @Test
//    void testBalance() throws AccsException {
//        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
//
//        BalanceReponse response = accountService.balance(accountNumber);
//
//        assertEquals(accountNumber, response.getAccountNumber());
//        assertEquals(BigDecimal.valueOf(5000), response.getCurrentBalance());
//
//        verify(accountRepository).findByAccountNumber(accountNumber);
//    }
//
//    @Test
//    void testBalanceAccountNotFound() {
//        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());
//
//        assertThrows(AccsException.class, () -> accountService.balance(accountNumber));
//
//        verify(accountRepository).findByAccountNumber(accountNumber);
//    }
}
