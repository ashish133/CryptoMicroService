package com.crypto.service;


import com.crypto.model.CryptoOrder;
import com.crypto.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderServiceImpl implements OrderService {

	/**
	 * This is a service to generate uniqueIDs for Orders. As of now it uses
	 * UUID but it can be changed to different implementation depending on the
	 * need
	 */
	private IDGenerationService idGeneratorService;
	private OrderRepository orderRepository;
	/**
	 * Two different queues for BUY and SELL orders are used as it will make
	 * sure that BUY and SELL orders are already sorted before a request to
	 * fetch top 10 BUY ORDERS or SELLS orders are requested
	 */

	public OrderServiceImpl(IDGenerationService idGeneration,OrderRepository orderRepository){
		this.idGeneratorService = idGeneration;
		this.orderRepository = orderRepository;
	}
	
	
	@Override
	public String placeOrder(final CryptoOrder order) {
		String orderId = idGeneratorService.getId();
		order.setOrderId(orderId);
		orderRepository.save(order);
		return orderId;
	}

	@Override
	public void cancel(final CryptoOrder order) {
		orderRepository.delete(order);
	}

}
