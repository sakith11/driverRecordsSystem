package com.example.cw2_4_5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class mainController {
    @FXML
    private Button mainBtnMenu;

    @FXML
    private StackPane paneMain;

    public void setPane(Node node){paneMain.getChildren().setAll(node);}
    public void onMenuClick(ActionEvent event) {
        paneNavigator.loadPane(paneNavigator.menu);
    }
}
