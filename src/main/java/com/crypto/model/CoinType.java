package com.crypto.model;

public enum CoinType {
	ETHEREUM("ETHEREUM"),
	LITECOIN("LITECOIN"),
	DASH("DASH"),
	BITCOIN("BITCOIN");

	private String name;

	private CoinType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
