package com.test.topdesk.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WorkerOrder {
    private Integer orderid;
    private String orderdate;
    private String deliverydate;
    private BigDecimal price;

}
