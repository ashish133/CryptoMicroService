package com.crypto.model;

public interface OrderSubscriber {

	public void apply(OrderNotification orderNotification);
}
