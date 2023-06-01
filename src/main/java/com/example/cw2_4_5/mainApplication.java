package com.example.cw2_4_5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class mainApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(mainApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1091, 786);

//        String css= this.getClass().getResource("src/sw2_4_5style.css").toExternalForm();
//        scene.getStylesheets().add(css);
        stage.setTitle("main");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(createScene(loadMainPane()));

       // scene.getStylesheets().add(getClass().getResource("src/cw2_4_5style.css").toExternalForm());
        stage.show();

    }
    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = loader.load(getClass().getResourceAsStream(paneNavigator.main));
        mainController maincontroller = loader.getController();
        paneNavigator.setMaincontroller(maincontroller);
        paneNavigator.loadPane(paneNavigator.positions);
        return mainPane;
    }
    private Scene createScene(Pane mainpane)
    {
        Scene scene = new Scene(mainpane);
        return scene;
    }
}
