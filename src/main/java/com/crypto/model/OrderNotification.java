package com.crypto.model;

public class OrderNotification {
	private NotificationType notificationType;
	private CryptoOrder order;

	public OrderNotification(){

	}

	public OrderNotification(NotificationType notificationType, CryptoOrder order){
		this.notificationType = notificationType;
		this.order = order;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}

	public CryptoOrder getOrder() {
		return order;
	}

	public void setOrder(CryptoOrder order) {
		this.order = order;
	}

}
