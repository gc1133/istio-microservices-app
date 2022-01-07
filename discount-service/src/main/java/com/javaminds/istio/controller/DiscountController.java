package com.javaminds.istio.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discount")
public class DiscountController {
	
	private static Logger logger = LoggerFactory.getLogger(DiscountController.class);
	
	@GetMapping("/product/{id}")
	public String getDiscount(@PathVariable Long id, @RequestHeader Map<String, String> headers) {
		logger.info("Fetching discount for product {}", id);
		headers.forEach((key,value) ->{
			logger.info("Header Name: "+key+" Header Value: "+value);
        });
		return "20% discount";
	}

}
