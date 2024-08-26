package com.smartbank.accountservice.interceptor;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.smartbank.accountservice.constant.SysConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor{

	 @Override
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
		 	MDC.put(SysConstant.SYS_REQ_CORR_ID_HEADER, request.getHeader(SysConstant.SYS_REQ_CORR_ID_HEADER));
			return true;
		}
}
