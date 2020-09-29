package com.crypto.service;


import com.crypto.model.CryptoOrder;

public interface OrderService {
	
	public String placeOrder(final CryptoOrder order);
	public void cancel(final CryptoOrder orderId);

}
