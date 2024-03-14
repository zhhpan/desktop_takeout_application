package com.test.topdesk.pojo;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Fooddelivery {

    private Integer OrderId;

    private String orderDate;

    private String deliveryDate;

    private BigDecimal price;


    public Fooddelivery(Integer orderId, String orderDate, String deliveryDate, BigDecimal price) {
        OrderId = orderId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.price = price;
    }
}
