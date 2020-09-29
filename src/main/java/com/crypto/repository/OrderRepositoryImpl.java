package com.crypto.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.crypto.model.*;
import com.google.common.annotations.VisibleForTesting;

public class OrderRepositoryImpl implements OrderRepository, OrderPublisher {

    private Set<CryptoOrder> orders = Collections.synchronizedSet(new HashSet<>());
    private List<OrderSubscriber> subscribers;

    public OrderRepositoryImpl() {
        this(new ArrayList<>());
    }

    public OrderRepositoryImpl(List<OrderSubscriber> subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public void save(CryptoOrder order) {
        this.orders.add(order);
        orderNotify(createNotification(NotificationType.ADDITION,order));
    }

    @Override
    public void delete(CryptoOrder order) {
        this.orders.remove(order);
        orderNotify(createNotification(NotificationType.DELETION,order));
    }

    @Override
    public List<CryptoOrder> getOrders(OrderType orderType) {
        return orders.stream()
                .filter(x -> x.getOrderType().equals(orderType))
                .collect(Collectors.toList());
    }

    private OrderNotification createNotification(NotificationType notificationType, CryptoOrder order){
        return new OrderNotification(notificationType,order);
    }

    @Override
    public void addSubscriber(OrderSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void orderNotify(OrderNotification orderNotification) {
        if (subscribers.size() > 0) {
            subscribers.stream()
                    .forEach(s -> s.apply(orderNotification));
        }
    }

    @VisibleForTesting
    public void clear(){
        orders.clear();
    }


}
