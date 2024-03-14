package com.test.topdesk.Controller;

import com.test.topdesk.mapper.MenuMapper;
import com.test.topdesk.pojo.Menu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class addController {

    @FXML
    private TextField Description;

    @FXML
    private TextField ImageURL;

    @FXML
    private TextField ItemName;

    @FXML
    private TextField Price;

    @FXML
    private Button add;


    MenuMapper menuMapper = new MenuMapper();

    @FXML
    void addMenu(ActionEvent event) {
        String itemName = ItemName.getText();
        String urlText = ImageURL.getText();
        String descriptionText = Description.getText();
        String priceText = Price.getText();
        List<Menu> menus = menuMapper.selectOnMenu(0, 1);
        int num = menus.size();
        int menuItemID = num + 1;
        menuMapper.insertOnMenu(Integer.toString(menuItemID),itemName,descriptionText, Float.parseFloat(priceText),urlText);
        ((Stage)add.getScene().getWindow()).close();

    }

}