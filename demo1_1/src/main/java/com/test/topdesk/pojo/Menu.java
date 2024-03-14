package com.test.topdesk.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @TableName menu
 */
@TableName(value ="menu")
@Data
public class Menu implements Serializable {
    private Integer menuitemid;

    private String itemname;

    private String description;

    private BigDecimal price;

    private String imageurl;


    public Menu(Integer menuitemid, String itemname, String description, BigDecimal price, String imageurl) {
        this.menuitemid = menuitemid;
        this.itemname = itemname;
        this.description = description;
        this.price = price;
        this.imageurl = imageurl;
    }

    public Menu() {
    }
}