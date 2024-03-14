package com.test.topdesk.pojo;



import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;


/**
* 
* @TableName orders
*/
@Data
public class Orders implements Serializable {
    public Orders() {
    }

    public Orders(Integer orderid, Integer customerid, Integer restaurantstaffid, Integer deliverystaffid, String note, String orderdate, String deliverydate, String status, BigDecimal totalamount) {
        this.orderid = orderid;
        this.customerid = customerid;
        this.restaurantstaffid = restaurantstaffid;
        this.deliverystaffid = deliverystaffid;
        this.note = note;
        this.orderdate = orderdate;
        this.deliverydate = deliverydate;
        this.status = status;
        this.totalamount = totalamount;
    }

    /**
    * 
    */

    private Integer orderid;
    /**
    * 
    */

    private Integer customerid;
    /**
    * 
    */

    private Integer restaurantstaffid;
    /**
    * 
    */

    private Integer deliverystaffid;
    /**
    * 
    */

    private String note;
    /**
    * 
    */

    private String orderdate;
    /**
    * 
    */

    private String deliverydate;
    /**
    * 
    */

    private String status;
    /**
    * 
    */

    private BigDecimal totalamount;

    /**
    * 
    */

}
