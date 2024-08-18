package com.smartbank.accountservice.constant;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;

public class TestImpl implements Customizer<SessionManagementConfigurer<HttpSecurity>> {

	@Override
	public void customize(SessionManagementConfigurer<HttpSecurity> t) {
		// TODO Auto-generated method stub
		
	}

}
