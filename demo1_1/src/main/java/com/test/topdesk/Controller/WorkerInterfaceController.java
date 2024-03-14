package com.test.topdesk.Controller;

import com.test.topdesk.HelloApplication;
import com.test.topdesk.mapper.MenuMapper;
import com.test.topdesk.mapper.OrdersMapper;
import com.test.topdesk.mapper.ReviewsMapper;
import com.test.topdesk.pojo.Menu;
import com.test.topdesk.pojo.Orders;
import com.test.topdesk.pojo.Reviews;
import com.test.topdesk.pojo.WorkerOrder;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WorkerInterfaceController {

    @FXML
    private Tab accept;

    @FXML
    private Tab manage;

    @FXML
    private TableView<Menu> menu;

    @FXML
    private TableView<WorkerOrder> order;

    @FXML
    private Tab request;

    @FXML
    private TableView<Reviews> reviews;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab view;

    @FXML
    private ChoiceBox<String> choiceOrder;

    @FXML
    private ChoiceBox<String> choiceStaff;


    //准备mapper类，对数据库进行访问
    MenuMapper menuMapper = new MenuMapper();
    OrdersMapper ordersMapper = new OrdersMapper();

    ReviewsMapper reviewsMapper = new ReviewsMapper();

    // 第一个表中的数据
    private ObservableList<Menu> data = FXCollections.observableArrayList();
    private ObservableList<WorkerOrder> data1 = FXCollections.observableArrayList();

    private ObservableList<Reviews> data2 = FXCollections.observableArrayList();

    private TableColumn<Menu, Integer> addColumn = new TableColumn<>("添加菜品");
    private TableColumn<Menu, Integer> deleteColumn = new TableColumn<>("删除菜品");
    private TableColumn<WorkerOrder, Integer> acceptColumn = new TableColumn<>("接受订单");
    @FXML
    void initialize() {
         Platform.runLater(()->{
            // 查询数据库，设置ObservableList，并添加至tableView当中
            List<Menu> menus = menuMapper.selectOnMenu(0,1);
            data.addAll(menus);
            menu.setItems(data);

            //1、添加编辑按钮
            addColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getMenuitemid()));
            addColumn.setCellFactory(param -> new TableCell<>() {
                final Button editBtn = new Button("添加菜品");

                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(editBtn);
                    editBtn.setOnMouseClicked( event ->{
                        Stage newStage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("add.fxml"));
                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load(), 280, 400);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        newStage.setTitle("添加菜品");
                        newStage.setScene(scene);


                        newStage.show();

                        newStage.setOnHiding(event1 -> {
                            refresh(menus);
                        });


                    });
                }
            });
            menu.getColumns().add(addColumn);
            deleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getMenuitemid()));
            deleteColumn.setCellFactory(param -> new TableCell<>() {
                final Button editBtn1 = new Button("删除菜品");

                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(editBtn1);
                    editBtn1.setOnMouseClicked( event -> {
                        menuMapper.deleteOnMenu(1, String.valueOf(item));
                        refresh(menus);
                    });
                }
            });
            menu.getColumns().add(deleteColumn);
            });
         TabPane.TabClosingPolicy tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE;
         tabPane.setTabClosingPolicy(tabClosingPolicy);
         tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            // 在这里执行数据刷新的逻辑
         refreshData(newTab);
        });
    }
    void refreshData(Tab selectedTab) {
            if (selectedTab == accept) {
                order.getItems().clear();
                // 在这里添加实际的数据刷新逻辑
                List<Orders> orders = ordersMapper.selectOnOrders(8, "未接受");
                List<WorkerOrder> workerOrderList = new CopyOnWriteArrayList<>();
                orders.forEach(orders1 -> {
                    WorkerOrder workerOrder = new WorkerOrder();
                    workerOrder.setDeliverydate(orders1.getDeliverydate());
                    workerOrder.setOrderid(orders1.getOrderid());
                    workerOrder.setOrderdate(orders1.getOrderdate());
                    workerOrder.setPrice(orders1.getTotalamount());
                    workerOrderList.add(workerOrder);
                });
                data1.addAll(workerOrderList);
                order.setItems(data1);
                acceptColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getOrderid()));
                acceptColumn.setCellFactory(param -> new TableCell<>() {
                    final Button editBtn = new Button("接受订单");

                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setGraphic(null);
                            return;
                        }
                        setGraphic(editBtn);
                        editBtn.setOnMouseClicked(event -> {
                            ordersMapper.updateOnOrders(8,"待配送",item);
                            for (WorkerOrder orders1 : workerOrderList) {
                                if (orders1.getOrderid() == item) {
                                    workerOrderList.remove(orders1);
                                }
                            }
                            order.getItems().clear();
                            data1.removeAll();
                            data1.addAll(workerOrderList);
                            order.setItems(data1);
                        });
                    }
                });
                if(!order.getColumns().contains(acceptColumn)){
                    order.getColumns().add(acceptColumn);
                }

            } else if (selectedTab == request) {
                choiceOrder.getItems().clear();
                choiceStaff.getItems().clear();
                List<Orders> orders = ordersMapper.selectOnOrders(8, "待配送");
                orders.forEach(orders1 -> {
                    choiceOrder.getItems().add(String.valueOf(orders1.getOrderid()));
                    Integer deliverystaffid = orders1.getDeliverystaffid();
                    if (!choiceStaff.getItems().contains(String.valueOf(deliverystaffid))) {
                        choiceStaff.getItems().add(String.valueOf(deliverystaffid));
                    }
                });

            } else if (selectedTab == view) {
                List<Reviews> reviewsList = reviewsMapper.selectOnReviews(0, String.valueOf(1));
                data2.addAll(reviewsList);
                reviews.setItems(data2);
            }

    }



    void refresh(List<Menu> menus){
        data.removeAll();
        menu.getItems().clear();
        menus = menuMapper.selectOnMenu(0,1);
        data.addAll(menus);
        menu.setItems(data);
    }



    @FXML
    void commit(ActionEvent event) {
        String choiceOrderValue = choiceOrder.getValue();
        String choiceStaffValue = choiceStaff.getValue();
        ordersMapper.updateOnOrders(8,"派送中", Integer.parseInt(choiceOrderValue));
    }



}

