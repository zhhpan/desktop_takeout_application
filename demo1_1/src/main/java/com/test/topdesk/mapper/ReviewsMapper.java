package com.test.topdesk.mapper;


import com.test.topdesk.pojo.Reviews;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
* @author ASUS
* @description 针对表【Reviews】的数据库操作Mapper
* @createDate 2023-11-27 14:49:34
* @Entity com.test.topdesk.pojo.Reviews
*/

/*
 * Reviews
 * mode 1:ReviewID
 * mode 2:CustomerID
 * mode 3:OrderID
 * mode 4:Rating
 * mode 5:Comment
 */
public class ReviewsMapper {

	//查询函数，mode选择属性，values为查询值
    //mode为0则查询全部行记录
    public List<Reviews> selectOnReviews(int mode,String value){
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
            sql="select * from Reviews";//mode为0，查询全部行记录
        }else{
            switch(mode){
                case 1:
                    sql="select * from Reviews where ReviewID = ?";//查询ReviewID=value的行记录
                    break;
                case 2:
                    sql="select * from Reviews where CustomerID = ?";//查询Customer=value的行记录
                    break;
                case 3:
                    sql="select * from Reviews where OrderID = ?";//查询OrderID=value的行记录
                    break;
                case 4:
                    sql="select * from Reviews where Rating = ?";//查询Rating=value的行记录
                    break;
                case 5:
                    sql="select * from Reviews where Comment = ?";//查询Comment=value的行记录
                    break;
            }
        }

        PreparedStatement ps ;
        List<Reviews> reviews = new ArrayList<>();

        try {
            ps = conn.prepareStatement(sql);
            if(mode >= 1){
                ps.setString(1,value);//将value填充进sql语句
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
		        int ReviewID =rs.getInt("ReviewID");
		        int CustomerID= rs.getInt("CustomerID");
		        int OrderID=rs.getInt("OrderID");
		        int Rating =rs.getInt("Rating");
                String Comment=rs.getString("Comment");
                Reviews review = new Reviews(ReviewID,CustomerID,OrderID,Rating,Comment);
                System.out.println(review);

                reviews.add(review);
	        }
            return reviews;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //增加函数
    public void insertOnReviews(String _ReviewID,String _CustomerID,String _OrderID,String _Rating,String _Comment){
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

        String sql ="insert into Reviews(ReviewID,CustomerID,OrderID,Rating,Comment) values(?,?,?,?,?);" ;//向Reviews表插入一条行记录
        PreparedStatement ps ;
        try {
            ps = conn.prepareStatement(sql);
        	ps.setString(1,_ReviewID);
        	ps.setString(2,_CustomerID);
        	ps.setString(3,_OrderID);
        	ps.setString(4,_Rating);
            ps.setString(5,_Comment);//将所有value填充进sql语句
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    //更新函数，一次函数调用仅支持更改单个属性的值，且不支持对ReviewID进行修改
    //参数mode选取更新属性，value为更新内容，RID为对应行记录的ReviewID
    public static void updateOnReviews(int mode, String value, int RID){
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
        	sql ="update Reviews set CustomerID =?  where ReviewID = ?;" ;//将ReviewID=RID的行记录 的CustomerID更改为value
        	break;
        case 3:
        	sql ="update Reviews set OrderID =?  where ReviewID = ?;" ;//将ReviewID=RID的行记录 的OrderID更改为value
        	break;
        case 4:
        	sql ="update Reviews set Rating =?  where ReviewID = ?;" ;//将ReviewID=RID的行记录 的Rating更改为value
        	break;
        case 5:
        	sql ="update Reviews set Comment =?  where ReviewID = ?;" ;//将ReviewID=RID的行记录 的Comment更改为value
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
    public void deleteOnReviews(int mode,String value){
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
        	sql ="delete from Reviews where ReviewID = ?;" ;//删除ReviewID=value的行记录
        	break;
        case 2:
        	sql ="delete from Reviews where CustomerID = ?;" ;//删除CustomerID=value的行记录
        	break;
        case 3:
        	sql ="delete from Reviews where OrderID = ?;" ;//删除OrderID=value的行记录
        	break;
        case 4:
        	sql ="delete from Reviews where Rating = ?;" ;//删除Rating=value的行记录
        	break;
        case 5:
        	sql ="delete from Reviews where Comment = ?;" ;//删除Comment=value的行记录
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



