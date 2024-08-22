package com.smartbank.accountservice.service.external;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.smartbank.accountservice.constant.SysConstant;
import com.smartbank.accountservice.dto.TransactionRequest;
import com.smartbank.accountservice.dto.TransactionResponse;
import com.smartbank.accountservice.exception.AccsException;
import com.smartbank.accountservice.exception.ExceptionCode;
import com.smartbank.accountservice.exception.bean.ErrorInfo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionServiceClientImpl implements TransactionServiceClient {

	@Value("${transaction.service.txn.entry}")
	private String txnEntryPath;

	@Autowired
	@Qualifier(value = "txnServiceClient")
	private RestTemplate txnServiceClient;

	@Override
	public TransactionResponse crateTxnEntry(Map<String, String> headers, String accountNumber,
			TransactionRequest transactionRequest) throws AccsException {
		final String methodName = "crateTxnEntry";
		try {
			HttpHeaders txnHeaders = new HttpHeaders();
			txnHeaders.add(HttpHeaders.USER_AGENT, "account-service");
			txnHeaders.add(HttpHeaders.AUTHORIZATION, headers.get(HttpHeaders.AUTHORIZATION));
			txnHeaders.add(SysConstant.SYS_REQ_CORR_ID_HEADER, headers.get(SysConstant.SYS_REQ_CORR_ID_HEADER));

			HttpEntity<TransactionRequest> httpEntity = new HttpEntity<>(transactionRequest, txnHeaders);

			UriComponents uriComponents = UriComponentsBuilder.fromPath(txnEntryPath).buildAndExpand(accountNumber);

			ResponseEntity<TransactionResponse> transactionResponse = txnServiceClient.exchange(uriComponents.toUriString(), HttpMethod.POST, httpEntity, TransactionResponse.class);
			
			if (transactionResponse.hasBody()) {
				log.info("{} - Ledger entry created successfully {}", methodName, transactionResponse.getBody());
				return transactionResponse.getBody();
			}else {
				log.error("{} - Empty response received from transaction service", methodName);
				throw new AccsException(ExceptionCode.ACCS_TXN_SERVICE_EMPTY_RESPONSE);
			}
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			ErrorInfo errorInfo = new Gson().fromJson(ex.getResponseBodyAsString(), ErrorInfo.class);
			log.info("{} - Error received from transaction service {} : {}", methodName, errorInfo.getCode(),
					errorInfo.getCauses());
			throw new AccsException(ExceptionCode.ACCS_TXN_SERVICE_EXCEPTION,
					new String[] { errorInfo.getCode(), errorInfo.getCauses().get(0) });
		} catch (ResourceAccessException ex) {
			log.error("{} - Connection error occurred while calling Transaction Service {}", methodName,
					ex.getMessage());
			throw new AccsException(ExceptionCode.ACCS_UNKNOWN_EXCEPTION, ex);
		} catch (RestClientException ex) {
			log.error("{} - Client error occurred while calling Transaction Service {}", methodName, ex.getMessage());
			throw new AccsException(ExceptionCode.ACCS_UNKNOWN_EXCEPTION, ex);
		} catch (Exception ex) {
			log.error("{} - Unknown error occurred while calling Transaction Service {}", methodName, ex.getMessage());
			throw new AccsException(ExceptionCode.ACCS_UNKNOWN_EXCEPTION, ex);
		}
	}
}
