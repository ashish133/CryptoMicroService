package com.crypto.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.crypto.model.*;
import com.google.common.annotations.VisibleForTesting;

public class SummaryServiceImpl implements SummaryService, OrderSubscriber {

    /**
     * Design is to precalculate Summary information before hand so that when someone requests it, or it is pushed
     * via websockets the time is not spent on calculation. It wil be real time
     */
    private Set<SummaryOrder> buySummary = new TreeSet<>(new BuySummaryComparator());
    private Set<SummaryOrder> sellSummary = new TreeSet<>(new SellSummaryComparator());

    @Override
    public List<SummaryOrder> getSummary(OrderType orderType) {
        Set<SummaryOrder> summary = buySummary;
        if (OrderType.SELL.equals(orderType)) {
            summary = sellSummary;
        }
        return summary.stream()
                .collect(Collectors.toList());
    }

    @Override
    public void apply(OrderNotification orderNotification) {
        Set<SummaryOrder> summary = buySummary;
        if (OrderType.SELL.equals(orderNotification.getOrder().getOrderType())) {
            summary = sellSummary;
        }
        SummaryOrder summaryOrder = orderNotification.getOrder().toSummaryOrder();
        synchronized (summary) {
            if (NotificationType.ADDITION.equals(orderNotification.getNotificationType())) {
                if (summary.contains(summaryOrder)) {
                    SummaryOrder existingSummary = getExistingSummaryOrder(summary, summaryOrder);
                    existingSummary.setQuantity(summaryOrder.getQuantity().add(existingSummary.getQuantity()));
                    summaryOrder = existingSummary;
                }
                summary.add(summaryOrder);
            } else {
                if (summary.contains(summaryOrder)) {
                    SummaryOrder existingSummary = getExistingSummaryOrder(summary, summaryOrder);
                    existingSummary.setQuantity(existingSummary.getQuantity().subtract(summaryOrder.getQuantity()));
                    summaryOrder = existingSummary;
                    if(summaryOrder.getQuantity().doubleValue() == 0d){
                        summary.remove(summaryOrder);
                    }
                }

            }
        }

    }

    private SummaryOrder getExistingSummaryOrder(Set<SummaryOrder> summary,
												 SummaryOrder forComparision) {
        return summary.stream()
                .filter(x -> forComparision.equals(x) )
                .findFirst().orElse(null);
    }

    @VisibleForTesting
    void clear(){
        buySummary.clear();
        buySummary.clear();
    }


}
