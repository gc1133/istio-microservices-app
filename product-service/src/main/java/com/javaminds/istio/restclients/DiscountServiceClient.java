package com.javaminds.istio.restclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="discount-service", url = "${DISCOUNT_SERVICE_URL:http://localhost:9091}")
public interface DiscountServiceClient {
	
	@GetMapping("/discount/product/{id}")
	String getDiscount(@PathVariable Long id);

}
