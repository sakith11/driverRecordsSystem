package com.example.cw2_4_5;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;

public class paneNavigator implements Serializable {
    public static final String main = "main.fxml";
    public static final String menu = "menu.fxml";
    public static final String edit = "edit.fxml";
    public static final String positions ="positions.fxml";
    public static final String simulate ="simulate.fxml";
    public static final String information ="information.fxml";
    public static final String constructor = "constructor.fxml";
    public static final String Export ="data.fxml";

    private static mainController maincontroller;
    public static void setMaincontroller(mainController maincontroller){
        paneNavigator.maincontroller = maincontroller;
    }
    public static void loadPane(String fxml){
        try {
            maincontroller.setPane(FXMLLoader.load(paneNavigator.class.getResource(fxml)));

        }
        catch (IOException e){e.printStackTrace();}
//        if (fxml.equals("edit.fxml")){
//            driverData drData = new driverData();
//            try {
//                drData.loadData();
//            }
//            catch (IOException e){}
//
//
//        }
    }
    public static void loadInfoBox(String fxml, int x, int y, simulateObject simObj) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(raceInfoController.class.getResource(fxml));
       fxmlLoader.setControllerFactory(raceInfoController -> new raceInfoController(simObj));
        //FXMLLoader fxmlLoader = new FXMLLoader();
        //fxmlLoader.setLocation(raceInfoController.class.getResource(fxml));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent,x,y);
       // scene.getStylesheets().add(paneNavigator.class.getResource("src/cw2_4_5style.css").toExternalForm());
      //  raceInfoController raceCon = fxmlLoader.getController();
       // dupController dup = fxmlLoader.getController();
       // dup.setInfo(info);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();


    }
    public static void loadDupBox(Pair<ArrayList<driver>,Boolean> pair, driver previous, Boolean ifadd) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(dupController.class.getResource("duplicated.fxml"));
        fxmlLoader.setControllerFactory(dupController -> new dupController(pair,previous,ifadd));
        //FXMLLoader fxmlLoader = new FXMLLoader();
        //fxmlLoader.setLocation(raceInfoController.class.getResource(fxml));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent,696,538);
        // scene.getStylesheets().add(paneNavigator.class.getResource("src/cw2_4_5style.css").toExternalForm());
        //  raceInfoController raceCon = fxmlLoader.getController();
        // dupController dup = fxmlLoader.getController();
        // dup.setInfo(info);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();


    }
    public static void loadDeleteAlert(String fxml,driver previous) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(dupController.class.getResource(fxml));
        fxmlLoader.setControllerFactory(delController -> new deleteController(previous));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent,468,233);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

    }
}
