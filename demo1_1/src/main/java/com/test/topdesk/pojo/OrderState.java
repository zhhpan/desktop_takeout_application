package com.test.topdesk.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderState {
    private Integer orderid;
    private String orderdate;
    private String deliverydate;
    private String status;
    private BigDecimal totalamount;
}
