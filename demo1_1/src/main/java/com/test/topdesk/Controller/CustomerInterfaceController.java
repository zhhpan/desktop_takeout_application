package com.test.topdesk.Controller;

import com.test.topdesk.mapper.MenuMapper;
import com.test.topdesk.mapper.OrdersMapper;
import com.test.topdesk.mapper.ReviewsMapper;
import com.test.topdesk.mapper.ShoppingcartMapper;
import com.test.topdesk.pojo.*;
import com.test.topdesk.pojo.Menu;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.text.SimpleDateFormat;
public class CustomerInterfaceController {

    private Integer reviewID = 1;



    private Integer orderNum ;

    private Integer CartID =1;
    @FXML
    private Button GoToPay;

    @FXML
    private TextArea commentContent;


    @FXML
    private Button confirmPayment;

    @FXML
    private CheckBox five_point;

    @FXML
    private CheckBox four_point;

    @FXML
    private TableView<Menu> menu;

    @FXML
    private TextField note;

    @FXML
    private Text oederId;

    @FXML
    private CheckBox one_point;

    @FXML
    private TableView<Menu> order;

    @FXML
    private ChoiceBox<Integer> orderId;

    @FXML
    private TableView<OrderState> orderstate;

    @FXML
    private ComboBox<?> paymentMethod;

    @FXML
    private CheckBox three_point;

    @FXML
    private ComboBox<String> time;

    @FXML
    private Text totalPrice;

    @FXML
    private CheckBox two_point;

    @FXML
    private Tab Pay;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab evaluate;

    @FXML
    private Tab states;

    //设置


    //用于对顾客的ID进行设置
    private String customerID = "1";
    //顾客id的set方法
    public void setCustomerID(String customerID){
        this.customerID=customerID;
    }

    //准备mapper类，对数据库进行访问
    MenuMapper menuMapper = new MenuMapper();
    ShoppingcartMapper shoppingcartMapper = new ShoppingcartMapper();

    OrdersMapper ordersMapper = new OrdersMapper();

    ReviewsMapper reviewsMapper = new ReviewsMapper();

    // 准备表格中的数据
    // 第一个表中的数据
    private ObservableList<Menu> data = FXCollections.observableArrayList();
    // 第二个表中的数据
    private ObservableList<Menu> data1 = FXCollections.observableArrayList();
    // 第四个表中的数据
    private ObservableList<OrderState> data2 = FXCollections.observableArrayList();

    //添加按钮
    private TableColumn<Menu, Integer> editColumn = new TableColumn<>("添加购物车");


    @FXML
    void initialize() {

        List<Orders> orders = ordersMapper.selectOnOrders(0, "4");
        orderNum = orders.size() + 1;
        List<Reviews> reviewsList = reviewsMapper.selectOnReviews(0, "5");
        reviewID = reviewsList.size() + 1;
        List<Shoppingcart> shoppingcarts = shoppingcartMapper.selectOnShoppingCart(0, "l");
        CartID = shoppingcarts.size() + 1;

        time.getItems().addAll("20分钟后","40分钟后","60分钟后");;

        // 通过UI线程获取值，并设置 菜单查看页面 的值
        Platform.runLater(()->{
            // 查询数据库，设置ObservableList，并添加至tableView当中
            List<Menu> menus = menuMapper.selectOnMenu(0,1);
            data.addAll(menus);
            menu.setItems(data);

            //1、添加编辑按钮
            editColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getMenuitemid()));
            editColumn.setCellFactory(param -> new TableCell<>(){
                final Button editBtn = new Button("添加购物车");

                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        setGraphic(null);
                        return;
                    }
                    setGraphic(editBtn);
                    // 添加点击事件
                    editBtn.setOnMouseClicked(event ->{

                        shoppingcartMapper.insertOnShoppingCart(String.valueOf(CartID),customerID,item.toString(),Integer.toString(1),"");
                        CartID++;
                        List<Shoppingcart> shoppingcarts = shoppingcartMapper.selectOnShoppingCart(2,customerID);
                        List<Menu> menus2 = new  ArrayList<>();
                        shoppingcarts.forEach( sc -> {
                            List<Menu> menus1 = menuMapper.selectOnMenu(1, sc.getMenuitemid());
                            menus2.addAll(menus1);
                        });
                        order.getItems().clear();
                        data1.removeAll();
                        data1.addAll(menus2);
                        System.out.println(menus2 +"//");
                        order.setItems(data1);
                        order.refresh();
                    });
                }
            });
            menu.getColumns().add(editColumn);
        });
        // 通过UI线程获取值，并设置 菜单查看页面 的值
//        Platform.runLater(()->{
//            // 查询数据库，设置ObservableList，并添加至tableView当中
//            List<Shoppingcart> shoppingcarts = shoppingcartMapper.selectOnShoppingCart(2,customerID);
//            List<Menu> menus = new ArrayList<>();
//            for (Shoppingcart sc : shoppingcarts) {
//                List<Menu> menus1 = menuMapper.selectOnMenu(1, sc.getMenuitemid());
//                System.out.println(menus1);
//                menus.addAll(menus1);
//            }
//            data1.addAll(menus);
//            System.out.println(menus);
//            order.setItems(data1);
//            order.refresh();
//
//
//
//        });

        TabPane.TabClosingPolicy tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE;
        tabPane.setTabClosingPolicy(tabClosingPolicy);
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            // 在这里执行数据刷新的逻辑
            refreshData(newTab);
        });



    }
    void refreshData(Tab selectedTab) {
            if (selectedTab == evaluate) {
                orderId.getItems().clear();
                // 在这里添加实际的数据刷新逻辑
                List<Orders> orders = ordersMapper.selectOnOrders(2, customerID);
                orders.forEach(orders1 -> {
                    if(orders1.getStatus().equals("已送达")){
                        orderId.getItems().add(orders1.getOrderid());
                    }
                });
            } else if (selectedTab == states) {
                orderstate.getItems().clear();
                List<Orders> ordersLists = ordersMapper.selectOnOrders(2,customerID);
                List<OrderState> orderStateList = new ArrayList<>();
                ordersLists.forEach(orders -> {

                    OrderState orderState = new OrderState();
                    orderState.setOrderid(orders.getOrderid());
                    orderState.setDeliverydate(orders.getDeliverydate());
                    orderState.setOrderdate(orders.getOrderdate());
                    orderState.setTotalamount(orders.getTotalamount());
                    orderState.setStatus(orders.getStatus());
                    orderStateList.add(orderState);
                });
                orderstate.getItems().clear();
                data2.addAll(orderStateList);
                System.out.println(ordersLists);
                orderstate.setItems(data2);
                orderstate.refresh();
            }

    }


    //点击前往支付
    @FXML
    void GoForPay(ActionEvent event) {
        tabPane.getSelectionModel().select(Pay);

        double price = 0;

        List<Shoppingcart> shoppingcarts = shoppingcartMapper.selectOnShoppingCart(2,customerID);

        for (Shoppingcart shoppingcart : shoppingcarts) {
            Integer menuitemid = shoppingcart.getMenuitemid();

            List<Menu> menus = menuMapper.selectOnMenu(1, menuitemid);

            for (Menu menu1 : menus) {
                price += menu1.getPrice().doubleValue();

            }

        }
        //Orders order = new Orders();
        totalPrice.setText(String.valueOf(price));
        oederId.setText(String.valueOf(orderNum));
        orderNum++;
        //order.setCustomerid(Integer.valueOf(customerID));
        //???
        //order.setDeliverystaffid(1);
        //初始化time


        //order.setDeliverydate("(Date) time.getValue()");
        //order.close();


    }
    @FXML
    void ConfirmPayment(ActionEvent event){

        double price = 0;

        List<Shoppingcart> shoppingcarts = shoppingcartMapper.selectOnShoppingCart(2,customerID);

        for (Shoppingcart shoppingcart : shoppingcarts) {
            Integer menuitemid = shoppingcart.getMenuitemid();

            List<Menu> menus = menuMapper.selectOnMenu(1, menuitemid);

            for (Menu menu1 : menus) {
                price += menu1.getPrice().doubleValue();

            }

        }

        // 创建SimpleDateFormat对象，指定日期时间格式
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         // 获取当前时间
        Date currentDate = new Date();

        // 使用Calendar类进行时间的加减
        // 使用Calendar类进行时间的加减
        Calendar calendar20Minutes = Calendar.getInstance();
        calendar20Minutes.setTime(currentDate);
        calendar20Minutes.add(Calendar.MINUTE, 20); // 加上20分钟

        Calendar calendar40Minutes = Calendar.getInstance();
        calendar40Minutes.setTime(currentDate);
        calendar40Minutes.add(Calendar.MINUTE, 40); // 加上40分钟

        Calendar calendar60Minutes = Calendar.getInstance();
        calendar40Minutes.setTime(currentDate);
        calendar40Minutes.add(Calendar.MINUTE, 60); // 加上40分钟
        // 获取加上20分钟后的时间
        Date newDate20 = calendar20Minutes.getTime();
        Date newDate40 = calendar40Minutes.getTime();
        Date newDate60 = calendar60Minutes.getTime();



        Orders order1 = new Orders();
        order1.setCustomerid(Integer.valueOf(customerID));
        order1.setDeliverystaffid(1);
        order1.setOrderid(Integer.valueOf(oederId.getText()));
        order1.setRestaurantstaffid(1);
        order1.setNote(note.getText());
        order1.setOrderdate(formatter.format(currentDate));

        if(time.getValue().equals("20分钟后")){
            order1.setDeliverydate(formatter.format(newDate20));
        } else if (time.getValue().equals("40分钟后")) {
            order1.setDeliverydate(formatter.format(newDate40));
        } else if (time.getValue().equals("60分钟后")) {
            order1.setDeliverydate(formatter.format(newDate60));
        }else {
            System.out.println("error in setDeliverydate");
        }
        order1.setStatus("未接受");
        order1.setTotalamount(BigDecimal.valueOf(price));
        ordersMapper.insertOnOrders(String.valueOf(order1.getOrderid()),String.valueOf(order1.getCustomerid()),String.valueOf(order1.getRestaurantstaffid()),String.valueOf(order1.getDeliverystaffid()),order1.getNote(),order1.getOrderdate(),order1.getDeliverydate(),order1.getStatus(),String.valueOf(order1.getTotalamount()));

        shoppingcartMapper.deleteOnShoppingCart(2, String.valueOf(order1.getCustomerid()));
        data1.removeAll();
        order.getItems().clear();
        //order.refresh();

        Platform.runLater(()->{
            // 查询数据库，设置ObservableList，并添加至tableView当中
            List<Orders> ordersLists = ordersMapper.selectOnOrders(2,customerID);
            List<OrderState> orderStateList = new ArrayList<>();
            ordersLists.forEach(orders -> {

                OrderState orderState = new OrderState();
                orderState.setOrderid(orders.getOrderid());
                orderState.setDeliverydate(orders.getDeliverydate());
                orderState.setOrderdate(orders.getOrderdate());
                orderState.setTotalamount(orders.getTotalamount());
                orderState.setStatus(orders.getStatus());
                orderStateList.add(orderState);
            });
            orderstate.getItems().clear();
            data2.addAll(orderStateList);
            System.out.println(ordersLists);
            orderstate.setItems(data2);
            orderstate.refresh();
        });

    }


    @FXML
    void commitComment(ActionEvent event) {
        Integer orderIdValue = orderId.getValue();
        Integer point;
        if(five_point.isSelected()){
            point = 5;
        } else if (four_point.isSelected()) {
            point = 4;
        } else if (three_point.isSelected()) {
            point = 3;
        } else if (two_point.isSelected()) {
            point = 2;
        } else if (one_point.isSelected()) {
            point = 1;
        }else {
            point = 0;
        }
        String text = commentContent.getText();

        Reviews reviews = new Reviews();
        reviews.setOrderid(orderIdValue);
        reviews.setComment(text);
        reviews.setReviewid(reviewID);
        reviewID++;
        reviews.setRating(point);
        reviews.setCustomerid(Integer.valueOf(customerID));
        reviewsMapper.insertOnReviews(Integer.toString(reviews.getReviewid()),Integer.toString(reviews.getCustomerid()),Integer.toString(reviews.getOrderid()),Integer.toString(reviews.getRating()),reviews.getComment());
    }
}
