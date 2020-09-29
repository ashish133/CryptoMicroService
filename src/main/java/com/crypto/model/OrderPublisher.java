package com.crypto.model;

public interface OrderPublisher {
	
	public void addSubscriber(OrderSubscriber subscriber);
	public void orderNotify(OrderNotification orderNotification);
}
