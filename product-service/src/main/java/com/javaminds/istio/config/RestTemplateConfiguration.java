package com.javaminds.istio.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class RestTemplateConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder().interceptors(new ClientHttpRequestInterceptor() {

			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {

				HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder
						.getRequestAttributes()).getRequest();
				Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
				while (headerNames.hasMoreElements()) {
					String header = (String) headerNames.nextElement();
					if (header.startsWith("x-")) {
						request.getHeaders().add(header, httpServletRequest.getHeader(header));
					}
				}

				return execution.execute(request, body);
			}
		}).build();
	}

}
