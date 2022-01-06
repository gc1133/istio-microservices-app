package com.javaminds.istio.interceptors;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class PassHeadersInterceptor implements RequestInterceptor {

	@Autowired
	private HttpServletRequest request;

	@Override
	public void apply(RequestTemplate template) {
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String header = (String) headerNames.nextElement();
			if (header.startsWith("x-")) {
				template.header(header, request.getHeader(header));
			}
		}
	}

}
