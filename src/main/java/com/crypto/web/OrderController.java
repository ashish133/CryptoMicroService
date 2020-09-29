package com.crypto.web;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.crypto.model.CryptoOrder;
import com.crypto.model.OrderType;
import com.crypto.model.SummaryOrder;
import com.crypto.service.OrderService;
import com.crypto.service.SummaryService;
import com.crypto.service.TopOrderService;

@RestController
@RequestMapping("/crypto/order")
public class OrderController {
	
	@Autowired
	private OrderService orderservice;
	
	@Autowired
	private SummaryService summaryService;
	
	@Autowired
	private TopOrderService topOrderService;
	
	@PostMapping("/place")
	public String place(final CryptoOrder order){
		return orderservice.placeOrder(order);
	}
	
	@DeleteMapping(value = "/cancel")
	public void cancel(final CryptoOrder order){
	   orderservice.cancel(order);
	}
	
	@GetMapping("/summary/{orderType}")
	public DeferredResult<List<SummaryOrder>> summary(final @PathVariable("orderType") OrderType orderType){
		DeferredResult<List<SummaryOrder>> deferredResult = new DeferredResult<>(2000L,"Timed Out");
        CompletableFuture.runAsync(()->{
        try {
            deferredResult.setResult(summaryService.getSummary(orderType));
        }catch (Exception ex){
        }
    });
		return deferredResult;
	}
	
	
	/**
	 * This endpoint returns top N number of orders
	 * @param orderType
	 * @param numberOfOrders
	 * @return
	 */
	@GetMapping("/top/{orderType}/{numberOfOrders}")
	public List<CryptoOrder> topOrders(final @PathVariable("orderType") OrderType orderType,
			final @PathVariable(value = "numberOfOrders",required = false) Optional<Integer> numberOfOrders ){
		int limit = 10;
		if(numberOfOrders.isPresent()){
			limit = numberOfOrders.get();
		}
		return topOrderService.topOrders(orderType,limit);
	}

}
