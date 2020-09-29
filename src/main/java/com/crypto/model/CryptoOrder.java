package com.crypto.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CryptoOrder implements Serializable{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	@EqualsAndHashCode.Include
	private String orderId;
	private String userId;
	private OrderType orderType;
	private CoinType coinType;
	private BigDecimal quantity;
	private BigDecimal pricePerCoin;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	public CoinType getCoinType() {
		return coinType;
	}
	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPricePerCoin() {
		return pricePerCoin;
	}
	public void setPricePerCoin(BigDecimal pricePerCoin) {
		this.pricePerCoin = pricePerCoin;
	}
	
	public SummaryOrder toSummaryOrder(){
		SummaryOrder order = new SummaryOrder();
		order.setCoinType(this.getCoinType());
		order.setType(this.getOrderType());
		order.setPrice(this.getPricePerCoin());
		order.setQuantity(this.getQuantity());
		return order;
	}
}
