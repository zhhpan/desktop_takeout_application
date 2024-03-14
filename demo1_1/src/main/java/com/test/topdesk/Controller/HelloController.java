package com.test.topdesk.Controller;

import com.test.topdesk.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloController {

    @FXML
    private PasswordField password;

    @FXML
    private ChoiceBox<String> role;

    @FXML
    private TextField username;

    @FXML
    private Button botton;




    @FXML
    void onLoginBotton(ActionEvent event) throws IOException {
        String usernameText = username.getText();
        String passwordText = password.getText();

         // 在这里添加验证用户名和密码的逻辑

        if(usernameText.equals("123")&&passwordText.equals("123")&&role.getValue().equals("用户")){
            Stage stage = (Stage) botton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("customer-interface.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),600,400);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } else if (usernameText.equals("12")&&passwordText.equals("12")&&role.getValue().equals("外卖小哥")) {
            Stage stage = (Stage) botton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fooddeliverystaff-interface.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),600,400);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();

        } else if (usernameText.equals("1")&&passwordText.equals("1")&&role.getValue().equals("员工")) {
            Stage stage = (Stage) botton.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("worker-interface.fxml"));
            Scene scene = new Scene(fxmlLoader.load(),600,400);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }else {
             Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("登录失败");
            alert.setHeaderText(null);
            alert.setContentText("账号或密码错误");
            alert.showAndWait();
        }


//        // 如果验证失败，显示错误提示
//        if (!usernameText.equals("123") || !passwordText.equals("password")) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("登录失败");
//            alert.setHeaderText(null);
//            alert.setContentText("账号或密码错误");
//            alert.showAndWait();
//        } else {
//        // 如果验证通过，跳转到登录后的界面
//            Stage stage = (Stage) botton.getScene().getWindow();
//            stage.close();
//            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("customer-interface.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(),600,400);
//            stage.setTitle("Hello!");
//            stage.setScene(scene);
//            stage.show();
//        }
    }

}

/*
    customer: 123 123 customer
    fooddelivery :12 12 fooddelivery
    worker : 1 1 worker

 */
