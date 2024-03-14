package com.test.topdesk.mapper;


import com.test.topdesk.pojo.Menu;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
* @author ASUS
* @description 针对表【menu】的数据库操作Mapper
* @createDate 2023-11-27 14:49:34
* @Entity com.test.topdesk.pojo.Menu
*/

/*
 * Menu
 * mode 1:MenuItemID
 * mode 2:ItemName
 * mode 3:Description
 * mode 4:Price
 * mode 5:ImageURl
 */
public class MenuMapper {

	//查询Menu行记录
    //mode为0查询全部，mode为1根据MenuItemID查询指定行记录
    public List<Menu> selectOnMenu(int mode,int value){
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
        if(mode == 0){
            sql ="select * from Menu"; //查询Menu所有行记录
        }else{
            sql="select * from Menu where MenuItemID = ?";//查询MenuItemID=value的行记录
        }
        PreparedStatement ps ;
        List<Menu> menus = new ArrayList<>();

        try {
            ps = conn.prepareStatement(sql);
            if(mode >= 1){
                ps.setInt(1,value);
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
		        int MenuItemID =rs.getInt("MenuItemID");
		        String ItemName= rs.getString("ItemName");
		        String Description=rs.getString("Description");
		        BigDecimal Price =rs.getBigDecimal("Price");
		        String ImageURL=rs.getString("ImageURL");
                Menu menu = new Menu(MenuItemID, ItemName, Description, Price, ImageURL);
                System.out.println(menu);
                menus.add(menu);
	        }
            return menus;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //增加函数
    public void insertOnMenu(String _MenuItemID,String _ItemName,String _Description,float _Price,String _ImageURL){
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

        String sql ="insert into Menu(MenuItemID,ItemName,Description,Price,ImageURL) values(?,?,?,?,?);" ;//向Menu插入一条行记录
        PreparedStatement ps ;
        try {
            ps = conn.prepareStatement(sql);
        	ps.setString(1,_MenuItemID);
        	ps.setString(2,_ItemName);
        	ps.setString(3,_Description);
        	ps.setFloat(4,_Price);
        	ps.setString(5,_ImageURL);//将value填充进sql语句
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    //更新函数，一次函数调用仅支持更改单个属性的值，且不支持对MenuItemID进行修改
    //参数mode选取更新属性，value为更新内容，RID为对应行记录的MenuItemID
    public static void updateOnMenu(int mode, String value, int RID){
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
        	sql ="update Menu set ItemName =?  where MenuItemID = ?;" ;//将MenuItemID=RID的行记录 的ItemName更改为value
        	break;
        case 3:
        	sql ="update Menu set Description =? where MenuItemID = ?;" ;//将MenuItemID=RID的行记录 的Description更改为value
        	break;
        case 4:
        	sql ="update Menu set Price =? where MenuItemID = ?;" ;//将MenuItemID=RID的行记录 的Price更改为value
        	break;
        case 5:
        	sql ="update Menu set ImageURL =? where MenuItemID = ?;" ;//将MenuItemID=RID的行记录 的ImageURL更改为value
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
    public void deleteOnMenu(int mode,String value){
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
        	sql ="delete from Menu where MenuItemID = ?;" ;//删除MenuItemID=value的行记录
        	break;
        case 2:
        	sql ="delete from Menu where ItemName = ?;" ;//删除ItemName=value的行记录
        	break;
        case 3:
        	sql ="delete from Menu where Description = ?;" ;//删除Description=value的行记录
        	break;
        case 4:
        	sql ="delete from Menu where Price = ?;" ;//删除Price=value的行记录
        	break;
        case 5:
        	sql ="delete from Menu where ImageURL = ?;" ;//删除ImageURL=value的行记录
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





