package com.example.cw2_4_5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class raceInfoController implements Initializable {
    @FXML
    private Label infoLblRace;
    @FXML
    private Label infoLblDate;
    @FXML
    private Label infoLblWheather;
    @FXML private TableView infoTbl;
    @FXML private TableColumn infoTblPos;
    @FXML private TableColumn infoTblName;
    @FXML private TableColumn infoTblTeam;
    @FXML private TableColumn infoTblCar;
    @FXML private TableColumn infoTblPoints;

    simulateObject simOBj;
    String raceName;
    LocalDate date;
    String wheather;
    ObservableList<raceInfo> raceInfoList = FXCollections.observableArrayList();


    public raceInfoController(simulateObject simOBj) {
        this.simOBj = simOBj;
    }

    public void onPositionClick(ActionEvent event) {
        paneNavigator.loadPane(paneNavigator.positions);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();

    }

//    public class raceInfo {
//        Integer pos;
//        String name;
//        String team;
//        String car;
//        Integer points;
//
//
//
//        public raceInfo(Integer pos, String name, String team, String car, Integer points) {
//            this.pos = pos;
//            this.name = name;
//            this.team = team;
//            this.car = car;
//            this.points = points;
//        }
//
//        public Integer getPos() {
//            return pos;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getTeam() {
//            return team;
//        }
//
//        public String getCar() {
//            return car;
//        }
//
//        public Integer getPoints() {
//            return points;
//        }
//    }
    private void setTable(ObservableList<raceInfo> raceinfo)
    {
        infoTblPos.setCellValueFactory(new PropertyValueFactory<raceInfo,Integer>("pos"));
        infoTblName.setCellValueFactory(new PropertyValueFactory<raceInfo,String>("name"));
        infoTblTeam.setCellValueFactory(new PropertyValueFactory<raceInfo,String>("team"));
        infoTblCar.setCellValueFactory(new PropertyValueFactory<raceInfo,String>("car"));
        infoTblPoints.setCellValueFactory(new PropertyValueFactory<raceInfo,Integer>("points"));
        infoTbl.setItems(raceInfoList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        raceName=simOBj.getRaceName();
        date = simOBj.getDate();
        wheather = simOBj.getWheather();
        infoLblRace.setText(raceName);
        infoLblDate.setText(date.toString());
        infoLblWheather.setText(wheather);
        driverData drData = new driverData();
        ArrayList<Integer> positions = new ArrayList<>();

//
        try {
            drData.loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObservableList<race> raceList = drData.getRaceList();
        ObservableList<driver> driverList = drData.getDriverList();
        ObservableList<driver> deletedList = drData.getDeletedList();
        for (driver dr:deletedList){driverList.add(dr);}

        for (race Race: raceList)
        {
            if (Race.getName().equals(simOBj.getRaceName()))
            {
                positions=Race.getPositions();

            }

        }
        for (int i=0;i<positions.size();i++)
        {
            for (driver dr:driverList)
            {
                if (dr.getId()==positions.get(i))
                {
                    int points =0;
                    if (i==0){points=10;}
                    else if (i==1) {points=7;}
                    else if(i==2){points=5;}
                    raceInfo rInfo = new raceInfo(i+1,dr.getName(),dr.getTeam(),dr.getCar(),points);
                    raceInfoList.add(rInfo);

                }

            }
        }
        setTable(raceInfoList);




    }
}

