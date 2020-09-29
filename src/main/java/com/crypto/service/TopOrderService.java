package com.crypto.service;

import java.util.*;
import java.util.stream.Collectors;

import com.crypto.model.BuyOrderComparator;
import com.crypto.model.CryptoOrder;
import com.crypto.model.NotificationType;
import com.crypto.model.OrderNotification;
import com.crypto.model.OrderSubscriber;
import com.crypto.model.OrderType;
import com.crypto.model.SellOrderComparator;
import com.google.common.annotations.VisibleForTesting;

public class TopOrderService implements OrderSubscriber{


	private Set<CryptoOrder> buyOrders = new TreeSet<>(new BuyOrderComparator());
	private Set<CryptoOrder> sellOrders = new TreeSet<>(new SellOrderComparator());
	
	public List<CryptoOrder> topOrders(OrderType orderType, int limit) {
		Set<CryptoOrder> orders = buyOrders;
		if (OrderType.SELL.equals(orderType)) {
			orders = sellOrders;
		}
		return orders.stream().
				limit(limit)
				.collect(Collectors.toList());
	}

	@Override
	public void apply(OrderNotification orderNotification) {
		Set<CryptoOrder> orders = buyOrders;
		if(OrderType.SELL.equals(orderNotification.getOrder().getOrderType())){
			orders = sellOrders;
		}

		synchronized (orders){
			if(NotificationType.ADDITION.equals(orderNotification.getNotificationType())){
				orders.add(orderNotification.getOrder());
			}else{
				orders.remove(orderNotification.getOrder());
			}
		}

	}

	@VisibleForTesting
	void clear(){
		buyOrders.clear();
		sellOrders.clear();
	}
}
