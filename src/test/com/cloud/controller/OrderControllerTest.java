package com.cloud.controller;

import com.cloud.orders.domain.Order;
import com.cloud.orders.domain.Product;
import com.cloud.orders.dto.OrderQueryVO;
import com.cloud.orders.exception.CustomException;
import com.cloud.orders.resource.OrderResource;
import com.cloud.orders.service.OrderService;
import com.cloud.orders.utils.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    @Mock
    private OrderService userService;

    @InjectMocks
    private OrderResource userController = new OrderResource();

    private List<Order> orderList;

    private Order order1;

    @Before
    public void setUp() {
        order1 = new Order("RTL_1002", new BigDecimal(15.55), new BigDecimal(10), new BigDecimal(19.99), new BigDecimal(1.99), new BigDecimal(6.43), "FULFILLED");

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("1257833283","9394550220002","Diva Jeans",new BigDecimal(39.99)));
        productList.add(new Product("1358743283","7394650110003","Polo Shirt",new BigDecimal(19.99)));
        order1.setProducts(productList);

        orderList = new ArrayList<>();
        orderList.add(order1);

        Order order2 = new Order("RTL_1005", null, new BigDecimal(9.5), new BigDecimal(49.99), new BigDecimal(4.74), new BigDecimal(54.73), "FULFILLED");
        orderList.add(order2);
    }

    @Test
    public void testOrderService_listOrders_returnSuccess() {
        when(userService.listOrders()).thenReturn(orderList);

        ResponseEntity<List<Order>> response = userController.listOrders();

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), orderList);
        Assert.assertEquals(((List)response.getBody()).size(), 2);
    }

    @Test
    public void testOrderService_getOrder_returnSuccess() {
        when(userService.getOrder("1")).thenReturn(order1);

        ResponseEntity<Order> response = userController.getOrder("1");

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), order1);
        Order order = (Order) response.getBody();
        Assert.assertEquals(order.getGrandTotal().abs(), new BigDecimal("50.41").abs());
        Assert.assertEquals(order.getOrderId(), null);
    }

    @Test
    public void testOrderService_getOrder_returnFailure() {
        when(userService.getOrder(null)).thenReturn(null);

        ResponseEntity<Order> response = userController.getOrder(null);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertEquals(response.getBody(), null);
    }

    @Test
    public void testOrderService_filterOrders_returnSuccess() throws CustomException {
        when(userService.filterOrders(any(OrderQueryVO.class))).thenReturn(orderList);

        ResponseEntity<List<Order>> response = (ResponseEntity<List<Order>>) userController.searchOrders(new OrderQueryVO());

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody(), orderList);
        Assert.assertEquals(((List)response.getBody()).size(), 2);
    }

    @Test
    public void testOrderService_filterOrders_returnFailure() throws CustomException {
        when(userService.filterOrders(any(OrderQueryVO.class))).thenThrow(new CustomException(Constants.RETRIEVE_ORDER_INFORMATION_ERROR, new Exception()));

        ResponseEntity<Error> response = (ResponseEntity<Error>) userController.searchOrders(new OrderQueryVO());

        Assert.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        Assert.assertTrue(response.getBody() instanceof Error);
    }

}
