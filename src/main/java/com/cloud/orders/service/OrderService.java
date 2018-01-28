package com.cloud.orders.service;

import com.cloud.orders.domain.Order;
import com.cloud.orders.dto.OrderQueryVO;
import com.cloud.orders.exception.CustomException;
import com.cloud.orders.repository.OrderRepository;
import com.cloud.orders.utils.Constants;
import com.cloud.orders.utils.SpecificationsUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SpecificationsUtil specificationsUtil;

    public List<Order> listOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(String orderId) {
        return orderRepository.findOne(orderId);
    }

    public List<Order> filterOrders(OrderQueryVO orderQueryVO) throws CustomException {
        try {
            List<Order> orders = orderRepository.findAll(specificationsUtil.constructOrderFilterQuery(orderQueryVO));

            // Filter and remove all products within each order with product price less than than minimumProductPrice
            if (!CollectionUtils.isEmpty(orders) && !StringUtils.isEmpty(orderQueryVO.getMinimumProductPrice())) {
                for (Order order : orders) {
                    order.setProducts(order.getProducts().parallelStream()
                            .filter(product -> (product.getPrice().compareTo(orderQueryVO.getMinimumProductPrice()) > 0)).collect(Collectors.toList()));
                }
            }
            return orders;
        } catch (DataAccessException dataAccessException) {
            LOGGER.error(Constants.RETRIEVE_ORDER_INFORMATION_ERROR, dataAccessException);
            throw new CustomException(Constants.RETRIEVE_ORDER_INFORMATION_ERROR, dataAccessException);
        }
    }

}
