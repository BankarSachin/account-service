package com.smartbank.accountservice.factory;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;


@Configuration
public class RestClientFactory {

	@Value("${transaction.service.base.url}")
	private String txnServiceBaseUrl;
	
	@Value("${transaction.service.base.url}")
	private String notificationServiceBaseUrl;
	
	@Bean(name = "txnServiceClient")
    public RestTemplate txnServiceRestTemplate(RestTemplateBuilder builder) {

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(txnServiceBaseUrl);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(customizedPoolingHttpClientConnectionManager())
                .setDefaultRequestConfig(requestConfig())
                .build();

        return builder
        		.uriTemplateHandler(uriBuilderFactory)
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
                .build();
    }
	
	@Bean(name = "notificationServiceClient")
    public RestTemplate notificationServiceRestTemplate(RestTemplateBuilder builder) {

		DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(notificationServiceBaseUrl);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(customizedPoolingHttpClientConnectionManager())
                .setDefaultRequestConfig(requestConfig())
                .build();

        return builder
        		.uriTemplateHandler(uriBuilderFactory)
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
                .build();
    }

	@Bean
	public RequestConfig requestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofSeconds(10))       // Connection timeout
                .setResponseTimeout(Timeout.ofSeconds(30))      // Socket timeout (waiting for data)
                .setConnectionRequestTimeout(Timeout.ofSeconds(5)) // Timeout to get a connection from the pool
                .setRedirectsEnabled(true)                     // Allow redirects
                .build();
	}
	
	@Bean
	public PoolingHttpClientConnectionManager customizedPoolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(20); // Max total connections
        connectionManager.setDefaultMaxPerRoute(5); // Max connections per route
		return connectionManager;
	}
	
}