package com.example.cw2_4_5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

public class informationController implements Initializable {
    @FXML
    public TableView<simulateObject> infoTbl;
    @FXML
    public TableColumn<simulateObject,String> infoTblName;
    @FXML
    public TableColumn<simulateObject, LocalDate> infoTblDate;
    @FXML
    public TableColumn<simulateObject,String> infoTblWheather;
    @FXML
    public TableColumn<simulateObject,String> infoTblFirst;
    @FXML
    public TableColumn<simulateObject,String> infoTblsecond;
    @FXML
    public TableColumn<simulateObject,String> infoTblThird;



    ArrayList<Integer> intDate = new ArrayList<>();
    ObservableList<race> raceList = FXCollections.observableArrayList();
    ObservableList<driver> driverList = FXCollections.observableArrayList();
    driverData drData = new driverData();

    public void OnMenuClick(ActionEvent event)
    {
        paneNavigator.loadPane(paneNavigator.menu);
    }
    private void setTable(ObservableList<simulateObject> simList)
    {
        infoTblName.setCellValueFactory(new PropertyValueFactory<simulateObject,String>("raceName"));
        infoTblDate.setCellValueFactory(new PropertyValueFactory<simulateObject,LocalDate>("date"));
        infoTblWheather.setCellValueFactory(new PropertyValueFactory<simulateObject,String>("wheather"));
        infoTblFirst.setCellValueFactory(new PropertyValueFactory<simulateObject,String>("firstName"));
        infoTblsecond.setCellValueFactory(new PropertyValueFactory<simulateObject,String>("secondName"));
        infoTblThird.setCellValueFactory(new PropertyValueFactory<simulateObject,String>("thirdName"));
        infoTbl.setItems(simList);
        infoTbl.setRowFactory(tv->{
            TableRow<simulateObject> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount()==2&& !row.isEmpty()){
                    simulateObject sim = row.getItem();
                    try {
                        paneNavigator.loadInfoBox("raceInfo.fxml",429,622,sim);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

            });
            return row;
        });


    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try {
            drData.loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         raceList = drData.getRaceList();
         driverList = drData.getDriverList();
         ObservableList<driver> deletedList= FXCollections.observableArrayList();
         deletedList= drData.getDeletedList();
         for (driver dr:deletedList){driverList.add(dr);}// drivers in the deleted list are added
        for (race race:raceList)
        {
            String d =race.getDate().toString().replaceAll("-","");// transforms 2022-02-01 into
            // 20220201 whic can be compared
            intDate.add(Integer.parseInt(d));
        }
        ActionEvent event = new ActionEvent();
        onRecent(event);


    }

        public void onRecent(ActionEvent event) {
            ObservableList<race> raceListCopy = FXCollections.observableArrayList(raceList);
            ArrayList<Integer> intDateCopy = new ArrayList<>(intDate);
            ObservableList<simulateObject> race = FXCollections.observableArrayList();
            while (intDateCopy.size()!=0) {
                for (int i = 0; i < intDateCopy.size(); i++) {
                    if (intDateCopy.get(i).equals(Collections.min(intDateCopy))) {
                        ArrayList<Integer> pos = raceListCopy.get(i).getPositions();
                        String first = null;
                        String second = null;
                        String third = null;
                        for (driver dr : driverList) {
                            if (dr.getId() == pos.get(0)) {
                                first = dr.getName();
                            }
                            if (dr.getId() == pos.get(1)) {
                                second = dr.getName();
                            }
                            if (dr.getId() == pos.get(2)) {
                                third = dr.getName();
                            }

                        }
                        simulateObject simObj = new simulateObject(raceListCopy.get(i).getName(), raceListCopy.get(i).getDate(),
                                raceListCopy.get(i).getWheather(), first, second, third);

                        intDateCopy.remove(i);
                        raceListCopy.remove(i);
                        race.add(simObj);

                    }
                }
            }
            setTable(race);
        }

    public void onFirst(ActionEvent event)
    {
        ObservableList<race> raceListCopy = FXCollections.observableArrayList(raceList);
        ArrayList<Integer> intDateCopy = new ArrayList<>(intDate);

        ObservableList<simulateObject> race = FXCollections.observableArrayList();
        while (intDateCopy.size()!=0) {
            for (int i = 0; i < intDateCopy.size(); i++) {
                if (intDateCopy.get(i).equals(Collections.max(intDateCopy))) {
                    ArrayList<Integer> pos = raceListCopy.get(i).getPositions();
                    String first = null;
                    String second = null;
                    String third = null;
                    for (driver dr : driverList) {
                        if (dr.getId() == pos.get(0)) {
                            first = dr.getName();
                        }
                        if (dr.getId() == pos.get(1)) {
                            second = dr.getName();
                        }
                        if (dr.getId() == pos.get(2)) {
                            third = dr.getName();
                        }

                    }
                    simulateObject simObj = new simulateObject(raceListCopy.get(i).getName(), raceListCopy.get(i).getDate(),
                            raceListCopy.get(i).getWheather(), first, second, third);

                    intDateCopy.remove(i);
                    raceListCopy.remove(i);
                    race.add(simObj);

                }
            }
        }
        setTable(race);
    }

    public void onCloseClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onMinClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
