package com.javaminds.istio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discount")
public class DiscountController {
	
	private static Logger logger = LoggerFactory.getLogger(DiscountController.class);
	
	@GetMapping("/product/{id}")
	public String getDiscount(@PathVariable Long id) {
		logger.info("Fetching discount for product {}", id);
		return "20% discount";
	}

}
