package com.javaminds.istio.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.javaminds.istio.model.Product;
import com.javaminds.istio.restclients.DiscountServiceClient;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Value("${DISCOUNT_SERVICE_URL}")
	private String discountServiceUrl;
	
//	@Autowired
//	private DiscountServiceClient discountServiceClient;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/product/{id}")
	public Product getProduct(@PathVariable Long id) {
		logger.info("Fetching details of product {}", id);
		
		List<Product> productsList = Arrays.asList(new Product(1l, "Iphone"), new Product(2l, "Car"));
		Product product = productsList.stream().filter(p -> p.getProductId() == id).findFirst()
				.orElse(new Product(0l, "Product not found"));
		
		DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(discountServiceUrl);
		restTemplate.setUriTemplateHandler(uriBuilderFactory);
		
	//	String discount = discountServiceClient.getDiscount(product.getProductId());
		String discount = restTemplate.getForObject("/discount/product/{id}", String.class,product.getProductId());
		
		product.setDiscount(discount);
		return product;
	}

}
