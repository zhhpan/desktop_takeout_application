package com.test.topdesk.mapper;



import com.test.topdesk.pojo.Shoppingcart;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
* @author ASUS
* @description 针对表【ShoppingCart】的数据库操作Mapper
* @createDate 2023-11-27 14:49:34
* @Entity com.test.topdesk.pojo.ShoppingCart
*/

/*
 * ShoppingCart
 * mode 1:CartID
 * mode 2:CustomerID
 * mode 3:MenuItemID
 * mode 4:Quantity
 * mode 5:Note
 */
public class ShoppingcartMapper {

	//查询函数，mode选择属性，values为查询值
    //mode为0则查询全部行记录
    public List<Shoppingcart> selectOnShoppingCart(int mode, String value){
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
            sql="select * from ShoppingCart";//mode为0，查询全部行记录
        }else{
            switch(mode){
                case 1:
                    sql="select * from ShoppingCart where CartID = ?";//查询CartID=value的行记录
                    break;
                case 2:
                    sql="select * from ShoppingCart where CustomerID = ?";//查询Customer=value的行记录
                    break;
                case 3:
                    sql="select * from ShoppingCart where MenuItemID = ?";//查询MenuItemID=value的行记录
                    break;
                case 4:
                    sql="select * from ShoppingCart where Quantity = ?";//查询Quantity=value的行记录
                    break;
                case 5:
                    sql="select * from ShoppingCart where Note = ?";//查询Note=value的行记录
                    break;
            }
        }

        PreparedStatement ps ;
        List<Shoppingcart> shoppingcarts = new ArrayList<>();

        try {
            ps = conn.prepareStatement(sql);
            if(mode >= 1){
                ps.setString(1,value);//将value填充进sql语句
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
		        int CartID=rs.getInt("CartID");
		        int CustomerID= rs.getInt("CustomerID");
		        int MenuItemID=rs.getInt("MenuItemID");
		        int Quantity =rs.getInt("Quantity");
                String Note=rs.getString("Note");
                Shoppingcart shoppingcart = new Shoppingcart(CartID,CustomerID,MenuItemID,Quantity,Note);
                System.out.println(shoppingcart);
                shoppingcarts.add(shoppingcart);
	        }
            return shoppingcarts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //增加函数
    public void insertOnShoppingCart(String _CartID,String _CustomerID,String _MenuItemID,String _Quantity,String _Note){
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

        String sql ="insert into ShoppingCart(CartID,CustomerID,MenuItemID,Quantity,Note) values(?,?,?,?,?);" ;//向ShoppingCart表插入一条行记录
        PreparedStatement ps ;
        try {
            ps = conn.prepareStatement(sql);
        	ps.setString(1,_CartID);
        	ps.setString(2,_CustomerID);
        	ps.setString(3,_MenuItemID);
        	ps.setString(4,_Quantity);
            ps.setString(5,_Note);//将所有value填充进sql语句
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    //更新函数，一次函数调用仅支持更改单个属性的值，且不支持对CartID进行修改
    //参数mode选取更新属性，value为更新内容，RID为对应行记录的CartID
    public static void updateOnShoppingCart(int mode, String value, int RID){
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
        	sql ="update ShoppingCart set CustomerID =?  where CartID = ?;" ;//将CartID=RID的行记录 的CustomerID更改为value
        	break;
        case 3:
        	sql ="update ShoppingCart set MenuItemID =?  where CartID = ?;" ;//将CartID=RID的行记录 的MenuItemID更改为value
        	break;
        case 4:
        	sql ="update ShoppingCart set Quantity =?  where CartID = ?;" ;//将CartID=RID的行记录 的Quantity更改为value
        	break;
        case 5:
        	sql ="update ShoppingCart set Note =?  where CartID = ?;" ;//将CartID=RID的行记录 的Note更改为value
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
    public void deleteOnShoppingCart(int mode,String value){
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
        	sql ="delete from ShoppingCart where CartID = ?;" ;//删除CartID=value的行记录
        	break;
        case 2:
        	sql ="delete from ShoppingCart where CustomerID = ?;" ;//删除CustomerID=value的行记录
        	break;
        case 3:
        	sql ="delete from ShoppingCart where MenuItemID = ?;" ;//删除MenuItemID=value的行记录
        	break;
        case 4:
        	sql ="delete from ShoppingCart where Quantity = ?;" ;//删除Quantity=value的行记录
        	break;
        case 5:
        	sql ="delete from ShoppingCart where Note = ?;" ;//删除Note=value的行记录
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



