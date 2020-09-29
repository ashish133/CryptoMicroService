package com.crypto.service;

import com.crypto.model.CoinType;
import com.crypto.model.CryptoOrder;
import com.crypto.model.OrderType;
import com.crypto.model.SummaryOrder;
import com.crypto.repository.OrderRepositoryImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.annotation.DirtiesContext.MethodMode.AFTER_METHOD;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(methodMode = AFTER_METHOD)
class CryptoMicroserviceApplicationTests {

    @Autowired
    private OrderService orderService;

	@Autowired
	private TopOrderService topOrderService;

	@Autowired
	private SummaryServiceImpl summaryService;

    @Autowired
    private OrderRepositoryImpl orderRepository;

    CryptoMicroserviceApplicationTests() {
    }

    @BeforeEach
    public void before(){
        topOrderService.clear();
        summaryService.clear();
        orderRepository.clear();
    }

    @Test
    void contextLoads() {
    }


    @Test
    public void testPlacingNewOrdersAndTopBuyOrder() {
        CryptoOrder cryptoOrder1 = createNewOrder(OrderType.BUY, new BigDecimal(110), new BigDecimal(1.5));
        CryptoOrder cryptoOrder2 = createNewOrder(OrderType.BUY, new BigDecimal(60.5), new BigDecimal(1.5));
        CryptoOrder cryptoOrder3 = createNewOrder(OrderType.BUY, new BigDecimal(130.5), new BigDecimal(2.5));
        CryptoOrder cryptoOrder4 = createNewOrder(OrderType.BUY, new BigDecimal(70.5), new BigDecimal(2.6));
        CryptoOrder cryptoOrder5 = createNewOrder(OrderType.BUY, new BigDecimal(61.5), new BigDecimal(1.6));
        String orderID1 = orderService.placeOrder(cryptoOrder1);
        String orderID2 = orderService.placeOrder(cryptoOrder2);
        String orderID3 = orderService.placeOrder(cryptoOrder3);
        String orderID4 = orderService.placeOrder(cryptoOrder4);
        String orderID5 = orderService.placeOrder(cryptoOrder5);
        List<CryptoOrder> topBuyOrders = topOrderService.topOrders(OrderType.BUY,10);
        Assert.assertEquals(5, topBuyOrders.size());
        Assert.assertEquals(orderID3, topBuyOrders.get(0).getOrderId());
    }

    @Test
    public void testPlacingNewOrdersAndTopSellOrder() {
        CryptoOrder cryptoOrder1 = createNewOrder(OrderType.SELL, new BigDecimal(110), new BigDecimal(1.5));
        CryptoOrder cryptoOrder2 = createNewOrder(OrderType.SELL, new BigDecimal(60.5), new BigDecimal(1.5));
        CryptoOrder cryptoOrder3 = createNewOrder(OrderType.SELL, new BigDecimal(130.5), new BigDecimal(2.5));
        CryptoOrder cryptoOrder4 = createNewOrder(OrderType.SELL, new BigDecimal(70.5), new BigDecimal(2.6));
        CryptoOrder cryptoOrder5 = createNewOrder(OrderType.SELL, new BigDecimal(61.5), new BigDecimal(1.6));
        String orderID1 = orderService.placeOrder(cryptoOrder1);
        String orderID2 = orderService.placeOrder(cryptoOrder2);
        String orderID3 = orderService.placeOrder(cryptoOrder3);
        String orderID4 = orderService.placeOrder(cryptoOrder4);
        String orderID5 = orderService.placeOrder(cryptoOrder5);
        List<CryptoOrder> topBuyOrders = topOrderService.topOrders(OrderType.SELL,10);
        Assert.assertEquals(5, topBuyOrders.size());
        Assert.assertEquals(orderID2, topBuyOrders.get(0).getOrderId());

    }

    @Test
    public void testTopOrdersLimitCriteria() {
        CryptoOrder cryptoOrder1 = createNewOrder(OrderType.SELL, new BigDecimal(110), new BigDecimal(1.5));
        CryptoOrder cryptoOrder2 = createNewOrder(OrderType.SELL, new BigDecimal(60.5), new BigDecimal(1.5));
        CryptoOrder cryptoOrder3 = createNewOrder(OrderType.SELL, new BigDecimal(130.5), new BigDecimal(2.5));
        CryptoOrder cryptoOrder4 = createNewOrder(OrderType.SELL, new BigDecimal(70.5), new BigDecimal(2.6));
        CryptoOrder cryptoOrder5 = createNewOrder(OrderType.SELL, new BigDecimal(61.5), new BigDecimal(1.6));
        String orderID1 = orderService.placeOrder(cryptoOrder1);
        String orderID2 = orderService.placeOrder(cryptoOrder2);
        String orderID3 = orderService.placeOrder(cryptoOrder3);
        String orderID4 = orderService.placeOrder(cryptoOrder4);
        String orderID5 = orderService.placeOrder(cryptoOrder5);
        List<CryptoOrder> topBuyOrders = topOrderService.topOrders(OrderType.SELL,2);
        Assert.assertEquals(2, topBuyOrders.size());
        Assert.assertEquals(orderID2, topBuyOrders.get(0).getOrderId());
    }

    @Test
    public void testCancelOrder() {
        CryptoOrder cryptoOrder1 = createNewOrder(OrderType.SELL, new BigDecimal(110), new BigDecimal(1.5));
        CryptoOrder cryptoOrder2 = createNewOrder(OrderType.SELL, new BigDecimal(60.5), new BigDecimal(1.5));
        CryptoOrder cryptoOrder3 = createNewOrder(OrderType.SELL, new BigDecimal(130.5), new BigDecimal(2.5));
        CryptoOrder cryptoOrder4 = createNewOrder(OrderType.SELL, new BigDecimal(70.5), new BigDecimal(2.6));
        CryptoOrder cryptoOrder5 = createNewOrder(OrderType.SELL, new BigDecimal(61.5), new BigDecimal(1.6));
        String orderID1 = orderService.placeOrder(cryptoOrder1);
        String orderID2 = orderService.placeOrder(cryptoOrder2);
        String orderID3 = orderService.placeOrder(cryptoOrder3);
        String orderID4 = orderService.placeOrder(cryptoOrder4);
        String orderID5 = orderService.placeOrder(cryptoOrder5);
        List<CryptoOrder> orders = topOrderService.topOrders(OrderType.SELL,5);
        Assert.assertEquals(5, orders.size());
        Assert.assertEquals(orderID2, orders.get(0).getOrderId());

        orderService.cancel(cryptoOrder2);
        orders = topOrderService.topOrders(OrderType.SELL,5);
        Assert.assertEquals(4, orders.size());
        Assert.assertEquals(orderID5, orders.get(0).getOrderId());
    }

    @Test
    public void testSummaryAddition(){
        CryptoOrder cryptoOrder1 = createNewOrder(OrderType.BUY, new BigDecimal(110), new BigDecimal(1.5));
        CryptoOrder cryptoOrder2 = createNewOrder(OrderType.BUY, new BigDecimal(60.5), new BigDecimal(1.5));
        CryptoOrder cryptoOrder3 = createNewOrder(OrderType.BUY, new BigDecimal(130.5), new BigDecimal(2.5));
        CryptoOrder cryptoOrder4 = createNewOrder(OrderType.BUY, new BigDecimal(60.5), new BigDecimal(10.2));
        String orderID1 = orderService.placeOrder(cryptoOrder1);
        String orderID2 = orderService.placeOrder(cryptoOrder2);
        String orderID3 = orderService.placeOrder(cryptoOrder3);
        String orderID4 = orderService.placeOrder(cryptoOrder4);

        List<SummaryOrder>  summary =  summaryService.getSummary(OrderType.BUY);
        Assert.assertEquals(3, summary.size());
        Assert.assertEquals(11.7d , summary.get(2).getQuantity().doubleValue(),0d);
    }

    @Test
    public void testSummaryDeletion(){
        CryptoOrder cryptoOrder1 = createNewOrder(OrderType.BUY, new BigDecimal(110), new BigDecimal(1.5));
        CryptoOrder cryptoOrder2 = createNewOrder(OrderType.BUY, new BigDecimal(60.5), new BigDecimal(1.5));
        CryptoOrder cryptoOrder3 = createNewOrder(OrderType.BUY, new BigDecimal(130.5), new BigDecimal(2.5));
        CryptoOrder cryptoOrder4 = createNewOrder(OrderType.BUY, new BigDecimal(60.5), new BigDecimal(10.2));
        String orderID1 = orderService.placeOrder(cryptoOrder1);
        String orderID2 = orderService.placeOrder(cryptoOrder2);
        String orderID3 = orderService.placeOrder(cryptoOrder3);
        String orderID4 = orderService.placeOrder(cryptoOrder4);

        List<SummaryOrder>  summary =  summaryService.getSummary(OrderType.BUY);
        Assert.assertEquals(3, summary.size());
        Assert.assertEquals(11.7d , summary.get(2).getQuantity().doubleValue(),0d);

        orderService.cancel(cryptoOrder2);
        summary =  summaryService.getSummary(OrderType.BUY);
        Assert.assertEquals(3, summary.size());
        Assert.assertEquals(10.2d , summary.get(2).getQuantity().doubleValue(),0d);

        orderService.cancel(cryptoOrder1);
        summary =  summaryService.getSummary(OrderType.BUY);
        Assert.assertEquals(2, summary.size());
    }



    private CryptoOrder createNewOrder(OrderType orderType, BigDecimal price, BigDecimal quantity) {
        CryptoOrder cryptoOrder = new CryptoOrder();
        cryptoOrder.setCoinType(CoinType.ETHEREUM);
        cryptoOrder.setOrderType(orderType);
        cryptoOrder.setUserId("user1");
        cryptoOrder.setPricePerCoin(price);
        cryptoOrder.setQuantity(quantity);
        return cryptoOrder;
    }


}
