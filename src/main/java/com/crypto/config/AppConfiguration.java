package com.crypto.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.crypto.model.OrderSubscriber;
import com.crypto.repository.OrderRepository;
import com.crypto.repository.OrderRepositoryImpl;
import com.crypto.service.IDGenerationService;
import com.crypto.service.OrderService;
import com.crypto.service.OrderServiceImpl;
import com.crypto.service.SummaryService;
import com.crypto.service.SummaryServiceImpl;
import com.crypto.service.TopOrderService;

@Configuration
public class AppConfiguration {
	
	@Bean
	public SummaryService summaryService(){
		return new SummaryServiceImpl();
	}
	
	@Bean
	public TopOrderService topOrderService(){
		return new TopOrderService();
	}
	
	@Bean
	public IDGenerationService idGenerationService(){
		return new IDGenerationService();
	}
	
	@Bean("orderRepository")
	public OrderRepository orderPublisher(SummaryService summaryService, TopOrderService topOrderService){
		List<OrderSubscriber> subcribers = new ArrayList<>();
		subcribers.add((OrderSubscriber)summaryService);
		subcribers.add((OrderSubscriber)topOrderService);
		return new OrderRepositoryImpl(subcribers);
	}
	
	@Bean
	public OrderService orderService(IDGenerationService idGenerationService, OrderRepository orderRepository){
		return new OrderServiceImpl(idGenerationService,orderRepository);
	}

}
