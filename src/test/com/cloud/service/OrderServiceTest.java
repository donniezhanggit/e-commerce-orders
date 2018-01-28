package com.cloud.service;

import com.cloud.orders.domain.Order;
import com.cloud.orders.domain.Product;
import com.cloud.orders.dto.OrderQueryVO;
import com.cloud.orders.exception.CustomException;
import com.cloud.orders.repository.OrderRepository;
import com.cloud.orders.service.OrderService;
import com.cloud.orders.utils.SpecificationsUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService userService = new OrderService();

    @Mock
    private SpecificationsUtil specificationsUtil;

    private List<Order> orderList;

    private Order order1;

    @Before
    public void setUp() throws FileNotFoundException {
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
    public void testUserService_listOrders_returnSuccess() {
        when(orderRepository.findAll()).thenReturn(orderList);

        List<Order> orders = userService.listOrders();

        Assert.assertEquals(orders.size(), 2);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void testUserService_retrieveUserByUserId_returnSuccess() throws CustomException {
        when(orderRepository.findOne(any(String.class))).thenReturn(order1);

        Order userDO = userService.getOrder("5");

        Assert.assertNotNull(userDO);
        verify(orderRepository, times(1)).findOne(any(String.class));
    }

    @Test(expected = CustomException.class)
    public void testUserService_createUser_returnFailure() throws CustomException {
        when(orderRepository.findAll(any(Specification.class))).thenThrow(new DataAccessException("Error") {});

        userService.filterOrders(any(OrderQueryVO.class));
    }

    public void testUserService_createUser_returnSuccess() throws CustomException {
        when(orderRepository.findAll(any(Specification.class))).thenReturn(orderList);

        List<Order> orders = userService.filterOrders(any(OrderQueryVO.class));

        Assert.assertEquals(orders.size(), 2);
        Order order = (Order) orders.get(0);
        Assert.assertEquals(order.getGrandTotal().abs(), new BigDecimal("50.41").abs());
        Assert.assertEquals(order.getOrderId(), null);
    }

}
