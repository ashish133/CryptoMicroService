package com.crypto.repository;

import java.util.List;

import com.crypto.model.CryptoOrder;
import com.crypto.model.OrderType;

public interface OrderRepository {
    public void save(CryptoOrder order);

    public void delete(CryptoOrder order);

    public List<CryptoOrder> getOrders(OrderType orderType);
}
