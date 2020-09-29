package com.crypto.model;

public enum OrderType {
	BUY("BUY"), SELL("SELL");
	
	private String type;

	private OrderType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
