package com.test.topdesk.pojo;


import lombok.Data;

import java.io.Serializable;



/**
* 
* @TableName reviews
*/
@Data
public class Reviews implements Serializable {

    /**
    * 
    */

    private Integer reviewid;
    /**
    * 
    */

    private Integer customerid;
    /**
    * 
    */

    private Integer orderid;
    /**
    * 
    */

    private Integer rating;
    /**
    * 
    */

    private String comment;


    public Reviews() {
    }

    public Reviews(Integer reviewid, Integer customerid, Integer orderid, Integer rating, String comment) {
        this.reviewid = reviewid;
        this.customerid = customerid;
        this.orderid = orderid;
        this.rating = rating;
        this.comment = comment;
    }


}
