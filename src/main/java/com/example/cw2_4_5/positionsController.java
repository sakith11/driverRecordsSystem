package com.example.cw2_4_5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;


public class positionsController implements Initializable {
    @FXML
    public TableView<driverRace> posTbl;
    @FXML
    public TableColumn<driverRace,Integer> posTblPos;
    @FXML
    public TableColumn<driverRace,String> posTblName;
    @FXML
    public TableColumn<driverRace,String> posTblTeam;
    @FXML
    public TableColumn<driverRace,String> posTblCar;
    @FXML
    public TableColumn<driverRace,String> posTblMont;
    @FXML
    public TableColumn<driverRace,String> posTblSweden;
    @FXML
    public TableColumn<driverRace,String> posTblMexico;
    @FXML
    public TableColumn<driverRace,String> posTblCroatia;
    @FXML
    public TableColumn<driverRace,String> posTblPortugal;
    @FXML
    public TableColumn<driverRace,String> posTblSardegna;
    @FXML
    public TableColumn<driverRace,String> posTblKenya;
    @FXML
    public TableColumn<driverRace,String> posTblEstonia;
    @FXML
    public TableColumn<driverRace,String> posTblFinland;
    @FXML
    public TableColumn<driverRace,String> posTblGreece;
    @FXML
    public TableColumn<driverRace,String> posTblChile;
    @FXML
    public TableColumn<driverRace,String> posTblEurope;
    @FXML
    public TableColumn<driverRace,String> posTblJapan;
    @FXML
    public TableColumn<driverRace,String> posTblTotal;
    private driverData driverData;
    private final ObservableList<driverRace> driverList = FXCollections.observableArrayList();


    ArrayList<Integer> points = new ArrayList<>();

    ArrayList<Integer> sortedPoints = new ArrayList<>();


    public void onMenuClick(ActionEvent event) {
        paneNavigator.loadPane(paneNavigator.menu);
    }
    private void setTable(ObservableList<driverRace> dRace)
    {
        if (this.driverData!=null){
            throw new IllegalStateException(" previously initialized");
        }
        this.driverData = driverData;
        posTblPos.setCellValueFactory(new PropertyValueFactory<driverRace,Integer>("pos"));
        posTblName.setCellValueFactory(new PropertyValueFactory<driverRace,String>("name"));
        posTblTeam.setCellValueFactory(new PropertyValueFactory<driverRace,String>("team"));
        posTblCar.setCellValueFactory(new PropertyValueFactory<driverRace,String>("car"));
        posTblMont.setCellValueFactory(new PropertyValueFactory<driverRace,String>("monteCarlo"));
        posTblSweden.setCellValueFactory(new PropertyValueFactory<driverRace,String>("sweden"));
        posTblMexico.setCellValueFactory(new PropertyValueFactory<driverRace,String>("mexico"));
        posTblCroatia.setCellValueFactory(new PropertyValueFactory<driverRace,String>("croatia"));
        posTblPortugal.setCellValueFactory(new PropertyValueFactory<driverRace,String>("portugal"));
        posTblSardegna.setCellValueFactory(new PropertyValueFactory<driverRace,String>("sardegna"));
        posTblKenya.setCellValueFactory(new PropertyValueFactory<driverRace,String>("kenya"));
        posTblEstonia.setCellValueFactory(new PropertyValueFactory<driverRace,String>("estonia"));
        posTblFinland.setCellValueFactory(new PropertyValueFactory<driverRace,String>("finland"));
        posTblGreece.setCellValueFactory(new PropertyValueFactory<driverRace,String>("greece"));
        posTblChile.setCellValueFactory(new PropertyValueFactory<driverRace,String>("chile"));
        posTblEurope.setCellValueFactory(new PropertyValueFactory<driverRace,String>("europe"));
        posTblJapan.setCellValueFactory(new PropertyValueFactory<driverRace,String>("japan"));
        posTblTotal.setCellValueFactory(new PropertyValueFactory<driverRace,String>("total"));
        posTbl.setItems(dRace);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        driverData drData = new driverData();

        try {
            drData.loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObservableList<driver> drList= drData.getDriverList();
        ObservableList<driver> deletedList= drData.getDeletedList();
        ArrayList<driver> drivers = new ArrayList<>();
        ArrayList<driver> sortedDrivers = new ArrayList<>();
        ArrayList<ArrayList<String>> allPositions = new ArrayList<>();
        for (driver dr:drList)
        {
            drivers.add(dr);
            points.add(Integer.parseInt(dr.getPoints()));

        }
        for (driver deleted:deletedList)
        {
            drivers.add(deleted);

            points.add(Integer.parseInt(deleted.getPoints()));
        }
        while (points.size()!=0){

        for (int i=0;i<points.size();i++){
            if (points.get(i).equals(Collections.max(points))){// getting the max amount
                sortedPoints.add(points.get(i));//adding the max amount to a new ArrayList
                sortedDrivers.add(drivers.get(i));// removing the max amount
                drivers.remove(i);

                points.remove(i);


            }
        }
        }
        for(driver dr:sortedDrivers)
        {
            ArrayList<String> positions = dr.getPositions();
            ArrayList<String> points = new ArrayList<>();// swaps the race positions for respective points

            for (int j=0;j<positions.size();j++)
            {
                if (positions.get(j).equals("1"))
                {
                    points.add("10");
                }
                else if (positions.get(j).equals("2"))
                {
                    points.add("7");
                }
                else if (positions.get(j).equals("3"))
                {
                    points.add("5");
                }
                 else if (positions.get(j).equals("_"))  {
                    points.add("_");

                }
                 else
                 {
                     points.add("0");
                 }

            }
            allPositions.add(points);
        }

        for (int i=0;i<sortedPoints.size();i++)
        {
            driver dr= sortedDrivers.get(i);
            ArrayList<String> pos = allPositions.get(i);
            driverRace drRace = new driverRace(dr.getName(),dr.getTeam(),dr.getCar(),pos.get(0),
                    pos.get(1),pos.get(2),
                    pos.get(3),pos.get(4),pos.get(5),pos.get(6),pos.get(7),pos.get(8),pos.get(9),pos.get(10),pos.get(11),
                    pos.get(12),sortedPoints.get(i).toString(),i+1
                    
                    );
            driverList.add(drRace);
        }

        setTable(driverList);

    }

    public void onMinClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void onCloseClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onm(MouseEvent mouseEvent) {
        paneNavigator.loadPane(paneNavigator.menu);
    }
}
