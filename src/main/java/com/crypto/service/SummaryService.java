package com.crypto.service;

import java.util.List;

import com.crypto.model.OrderType;
import com.crypto.model.SummaryOrder;

public interface SummaryService {

	public List<SummaryOrder> getSummary(OrderType orderType);
}
