package com.cloud.orders.resource;

import com.cloud.orders.domain.Order;
import com.cloud.orders.dto.OrderQueryVO;
import com.cloud.orders.exception.CustomException;
import com.cloud.orders.service.OrderService;
import com.cloud.orders.utils.Constants;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/orders")
public class OrderResource {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> listOrders() {
        return ok(orderService.listOrders());
    }

    @GetMapping(value = "/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            return notFound().build();
        }
        return ok(order);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> searchOrders(OrderQueryVO orderQueryVO) {
        List<Order> orders = null;
        try {
            orders = orderService.filterOrders(orderQueryVO);
        } catch (CustomException e) {
            LOGGER.error(Constants.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<Error>(new Error(e.getCause()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ok(orders);
    }

}
