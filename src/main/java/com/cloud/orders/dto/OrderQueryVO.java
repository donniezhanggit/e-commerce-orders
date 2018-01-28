package com.cloud.orders.dto;

import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * Query class encapsulating request parameters
 */
public class OrderQueryVO {

    private String orderStatus;

    private BigDecimal minimumProductPrice;

    private long productCount;

    private String discount;


    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(@RequestParam(required = false) String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getMinimumProductPrice() {
        return minimumProductPrice;
    }

    public void setMinimumProductPrice(@RequestParam(required = false) BigDecimal minimumProductPrice) {
        this.minimumProductPrice = minimumProductPrice;
    }

    public long getProductCount() {
        return productCount;
    }

    public void setProductCount(@RequestParam(required = false) long productCount) {
        this.productCount = productCount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(@RequestParam(required = false) String discount) {
        this.discount = discount;
    }
}
