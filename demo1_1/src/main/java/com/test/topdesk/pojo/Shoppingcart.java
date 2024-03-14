package com.test.topdesk.pojo;



import lombok.Data;

import java.io.Serializable;


/**
* 
* @TableName shoppingcart
*/
@Data
public class Shoppingcart implements Serializable {
    public Shoppingcart(Integer cartid, Integer customerid, Integer menuitemid, Integer quantity, String note) {
        this.cartid = cartid;
        this.customerid = customerid;
        this.menuitemid = menuitemid;
        this.quantity = quantity;
        this.note = note;
    }

    /**
    * 
    */

    private Integer cartid;
    /**
    * 
    */

    private Integer customerid;
    /**
    * 
    */

    private Integer menuitemid;
    /**
    * 
    */

    private Integer quantity;
    /**
    * 
    */

    private String note;



}
