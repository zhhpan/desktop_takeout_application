package com.test.topdesk.mapper;


import com.test.topdesk.pojo.Orders;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
* @author ASUS
* @description 针对表【Orders】的数据库操作Mapper
* @createDate 2023-11-27 14:49:34
* @Entity com.test.topdesk.pojo.Orders
*/

/*
 * Orders
 * mode 1:OrderID
 * mode 2:CustomerID
 * mode 3:RestaurantStaffID
 * mode 4:DeliveryStaffID
 * mode 5:Note
 * mode 6:OrderDate
 * mode 7:DeliveryDate
 * mode 8:Status
 * mode 9:TotalAmount
 */
public class OrdersMapper {

	//查询函数，mode选择属性，values为查询值
    //mode为0则查询全部行记录
    public List<Orders> selectOnOrders(int mode,String value){
        Connection conn;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/topdesk?serverTimezone=Asia/Shanghai",
                    "root",
                    "panzhenghan"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql = null;
        if(mode == 0){
            sql="select * from Orders";//mode为0，查询全部行记录
        }else{
            switch(mode){
                case 1:
                    sql="select * from Orders where OrderID = ?";//查询OrderID=value的行记录
                    break;
                case 2:
                    sql="select * from Orders where CustomerID = ?";//查询CustomerID=value的行记录
                    break;
                case 3:
                    sql="select * from Orders where RestaurantStaffID = ?";//查询RestaurantStaffID=value的行记录
                    break;
                case 4:
                    sql="select * from Orders where DeliveryStaffID = ?";//查询DeliveryStaffID=value的行记录
                    break;
                case 5:
                    sql="select * from Orders where Note = ?";//查询Note=value的行记录
                    break;
                case 6:
                    sql="select * from Orders where OrderDate = ?";//查询OrderDate=value的行记录
                    break;
                case 7:
                    sql="select * from Orders where DeliveryDate = ?";//查询DeliveryDate=value的行记录
                    break;
                case 8:
                    sql="select * from Orders where Status = ?";//查询Status=value的行记录
                    break;
                case 9:
                    sql="select * from Orders where TotalAmount = ?";//查询TotalAmount=value的行记录
                    break;
            }
        }

        PreparedStatement ps ;
        List<Orders> orders = new ArrayList<>();

        try {
            ps = conn.prepareStatement(sql);
            if(mode >= 1){
                ps.setString(1,value);//将value填充进sql语句
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
		        int OrderID=rs.getInt("OrderID");
		        int CustomerID= rs.getInt("CustomerID");
		        int RestaurantStaffID=rs.getInt("RestaurantStaffID");
		        int DeliveryStaffID =rs.getInt("DeliveryStaffID");
                String Note=rs.getString("Note");
                String orderDate = rs.getString("OrderDate");
                //String OrderDate=rs.getString("OrderDate");
                String DeliveryDate=rs.getString("DeliveryDate");
                String Status=rs.getString("Status");
                BigDecimal TotalAmount=rs.getBigDecimal("TotalAmount");
                Orders order = new Orders(OrderID,CustomerID,RestaurantStaffID,DeliveryStaffID,Note,orderDate,DeliveryDate,Status,TotalAmount);
                System.out.println(order);

                orders.add(order);
	        }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //增加函数
    public void insertOnOrders(String _OrderID,String _CustomerID,String _RestaurantStaffID,String _DeliveryStaffID,String _Note,String _OrderDate,String _DeliveryDate,String _Status,String _TotalAmount){
        Connection conn;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/topdesk?serverTimezone=Asia/Shanghai",
                    "root",
                    "panzhenghan"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql ="insert into Orders(OrderID,CustomerID,RestaurantStaffID,DeliveryStaffID,Note,OrderDate,DeliveryDate,Status,TotalAmount) values(?,?,?,?,?,?,?,?,?);" ;//向Orders表插入一条行记录
        PreparedStatement ps ;
        try {
            ps = conn.prepareStatement(sql);
        	ps.setString(1,_OrderID);
        	ps.setString(2,_CustomerID);
        	ps.setString(3,_RestaurantStaffID);
        	ps.setString(4,_DeliveryStaffID);
            ps.setString(5,_Note);
            ps.setString(6,_OrderDate);
            ps.setString(7,_DeliveryDate);
            ps.setString(8,_Status);
            ps.setString(9,_TotalAmount);//将所有value填充进sql语句
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    //更新函数，一次函数调用仅支持更改单个属性的值，且不支持对OrderID进行修改
    //参数mode选取更新属性，value为更新内容，RID为对应行记录的OrderID
    public static void updateOnOrders(int mode, String value, int RID){
        Connection conn;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/topdesk?serverTimezone=Asia/Shanghai",
                    "root",
                    "panzhenghan"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = null;
        switch(mode) {
        case 2:
        	sql ="update Orders set CustomerID =?  where OrderID = ?;" ;//将OrderID=RID的行记录 的CustomerID更改为value
        	break;
        case 3:
        	sql ="update Orders set RestaurantStaffID =?  where OrderID = ?;" ;//将OrderID=RID的行记录 的RestaurantStaffID更改为value
        	break;
        case 4:
        	sql ="update Orders set DeliveryStaffID =?  where OrderID = ?;" ;//将OrderID=RID的行记录 的DeliveryStaffID更改为value
        	break;
        case 5:
        	sql ="update Orders set Note=?  where OrderID = ?;" ;//将OrderID=RID的行记录 的Note更改为value
        	break;
        case 6:
        	sql ="update Orders set OrderDate =?  where OrderID = ?;" ;//将OrderID=RID的行记录 的OrderDate更改为value
        	break;
        case 7:
        	sql ="update Orders set DeliveryDate =?  where OrderID = ?;" ;//将OrderID=RID的行记录 的DeliveryDate更改为value
        	break;
        case 8:
        	sql ="update Orders set Status =?  where OrderID = ?;" ;//将OrderID=RID的行记录 的Status更改为value
        	break;
        case 9:
        	sql ="update Orders set TotalAmount =?  where OrderID = ?;" ;//将OrderID=RID的行记录 的TotalAmount更改为value
        	break;
        }
        PreparedStatement ps ;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,value);
            ps.setInt(2,RID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //删除函数，mode选取删除所依据的属性，value为删除对象的该属性对应的值
    public void deleteOnOrders(int mode,String value){
        Connection conn;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/topdesk?serverTimezone=Asia/Shanghai",
                    "root",
                    "panzhenghan"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql=null;
        switch(mode) {
        case 1:
        	sql ="delete from Orders where OrderID = ?;" ;//删除OrderID=value的行记录
        	break;
        case 2:
        	sql ="delete from Orders where CustomerID = ?;" ;//删除CustomerID=value的行记录
        	break;
        case 3:
        	sql ="delete from Orders where RestaurantStaffID = ?;" ;//删除RestaurantStaffID=value的行记录
        	break;
        case 4:
        	sql ="delete from Orders where DeliveryStaffID = ?;" ;//删除DeliveryStaffID=value的行记录
        	break;
        case 5:
        	sql ="delete from Orders where Note = ?;" ;//删除Note=value的行记录
        	break;
        case 6:
        	sql ="delete from Orders where OrderDate = ?;" ;//删除OrderDate=value的行记录
        	break;
        case 7:
        	sql ="delete from Orders where DeliveryDate = ?;" ;//删除DeliveryDate=value的行记录
        	break;
        case 8:
        	sql ="delete from Orders where Status = ?;" ;//删除Status=value的行记录
        	break;
        case 9:
        	sql ="delete from Orders where TotalAmount = ?;" ;//删除TotalAmount=value的行记录
        	break;
        }

        PreparedStatement ps ;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,value);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}



