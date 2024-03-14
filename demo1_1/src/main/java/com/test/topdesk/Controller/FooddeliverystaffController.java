package com.test.topdesk.Controller;

import com.test.topdesk.mapper.OrdersMapper;
import com.test.topdesk.pojo.Fooddelivery;
import com.test.topdesk.pojo.Menu;
import com.test.topdesk.pojo.Orders;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FooddeliverystaffController {

    @FXML
    private TableView<Fooddelivery> Table;


    private String deliveryStaffID;

    public void setDeliveryStaffID(String deliveryStaffID) {
        this.deliveryStaffID = deliveryStaffID;
    }

    //准备mapper类，对数据库进行访问
    OrdersMapper ordersMapper = new OrdersMapper();

    // 准备表格中的数据
    private ObservableList<Fooddelivery> data = FXCollections.observableArrayList();

    //按钮
    private TableColumn<Fooddelivery, Integer> editColumn = new TableColumn<>("是否送达");
    @FXML
    void initialize() {

        // 通过UI线程获取值，并设置值
        Platform.runLater(() -> {


            // 查询数据库，设置ObservableList，并添加至tableView当中
            List<Fooddelivery> fooddelivery = new CopyOnWriteArrayList<>();
            List<Orders> orders1 = ordersMapper.selectOnOrders(8 , "派送中");

            int i;
            for(i=0;i<orders1.size();i++) {
                Orders orders = orders1.get(i);//.OrderId(orders1.getOrderid()) ;
                Integer orderid = orders.getOrderid();
                Fooddelivery fooddelivery1 = new Fooddelivery(orders.getOrderid(),orders.getOrderdate(),orders.getDeliverydate(),orders.getTotalamount());
                System.out.println(fooddelivery1);
            	fooddelivery.add(fooddelivery1);
            }
            data.addAll(fooddelivery);
            Table.setItems(data);

            //1、添加编辑按钮
            editColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getOrderId()));
            editColumn.setCellFactory(param -> new TableCell<>() {
                final Button editBtn = new Button("已送达");


                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(editBtn);
                    // 添加点击事件
                    editBtn.setOnMouseClicked(event -> {
                        OrdersMapper.updateOnOrders(8,"已送达",item);
                        for (Fooddelivery fooddelivery1 : fooddelivery) {
                            if (fooddelivery1.getOrderId() == item) {
                                fooddelivery.remove(fooddelivery1);
                            }
                        }
                        Table.getItems().clear();
                        data.removeAll();
                        data.addAll(fooddelivery);
                        Table.setItems(data);
                        Table.refresh();
                    });
                }
            });
            Table.getColumns().add(editColumn);
        });

    }

}
//    @FXML
//    void initialize() {
//
//        // 通过UI线程获取值，并设置值
//        Platform.runLater(() -> {
//
//
//            // 查询数据库，设置ObservableList，并添加至tableView当中
//            List<Fooddelivery> fooddelivery = null;
//            List<Orders> orders = ordersMapper.selectOnOrders(0 , String.valueOf(1));
//            orders.forEach(orders1 -> {
//
//            });
//            data.addAll(fooddelivery);
//            Table.setItems(data);
//
//            //1、添加编辑按钮
//            editColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getOrderId()));
//            editColumn.setCellFactory(param -> new TableCell<>() {
//                final Button editBtn = new Button("已送达");
//
//
//                @Override
//                protected void updateItem(Integer item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (item == null) {
//                        setGraphic(null);
//                        return;
//                    }
//                    setGraphic(editBtn);
//                    // 添加点击事件
//                    editBtn.setOnMouseClicked(event -> {
//                        //shoppingcartMapper.
//
//                    });
//                }
//            });
//            Table.getColumns().add(editColumn);
//
//
//        });
//
//    }

