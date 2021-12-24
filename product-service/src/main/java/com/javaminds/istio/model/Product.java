package com.javaminds.istio.model;

public class Product {

	private Long productId;
	private String productName;
	private String discount;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
	public Product(Long productId, String productName) {
		super();
		this.productId = productId;
		this.productName = productName;
	}

}
