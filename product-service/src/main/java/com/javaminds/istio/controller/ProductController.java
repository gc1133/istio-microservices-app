package com.javaminds.istio.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaminds.istio.model.Product;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@GetMapping("/product/{id}")
	public Product getProduct(@PathVariable Long id) {
		logger.info("Fetching details of product %s", id);
		List<Product> productsList = Arrays.asList(new Product(1l, "Iphone"), new Product(2l, "Car"));
		return productsList.stream().filter(p -> p.getProductId() == id).findFirst()
				.orElse(new Product(null, "Product not found"));
	}

}
