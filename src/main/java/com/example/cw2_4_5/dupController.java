package com.example.cw2_4_5;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class dupController implements Initializable {
    @FXML
    DialogPane dialogDup;
    @FXML
    private TableView<driver> dupTable;
    @FXML
    private TableColumn<driver, String> tblName;
    @FXML
    private TableColumn<driver, String> tblAge;
    @FXML
    private TableColumn <driver, String>tblTeam;
    @FXML
    private TableColumn <driver, String>tblCar;
    @FXML
    private TableColumn <driver, String>tblPoints;
    @FXML
    private TextField dupTxtName;
    @FXML
    private TextField dupTxtCar;
    @FXML
    private Button dupBtnChange;
    @FXML
    private Button dupBtnCancel;
    @FXML
    private  Label dupLblHead;
    @FXML
    private Label dupLblCar;
    @FXML
    private  Label dupLblWarn;
    @FXML
    private Label dupLblName;
    Pair<ArrayList<driver>,Boolean> pair;
    ArrayList<driver> drList;
    Boolean duplicated;
    driver previous;
    boolean ifadd;
    Path path;
    ObservableList<driver> driverList;
    ObservableList<driver> dupDrivers = FXCollections.observableArrayList();
    driverData drData;

    public dupController(Pair<ArrayList<driver>, Boolean> pair,driver previous, boolean ifadd) {
        this.pair = pair;
        this.previous=previous;
        this.ifadd= ifadd;
        this.drData = new driverData();
        driverList =drData.getDriverList();

    }
    public void setTable(ObservableList<driver> driverList){
        tblName.setCellValueFactory(new PropertyValueFactory<driver, String>("name"));
        tblAge.setCellValueFactory(new PropertyValueFactory<driver, String>("age"));
        tblTeam.setCellValueFactory(new PropertyValueFactory<driver, String>("team"));
        tblCar.setCellValueFactory(new PropertyValueFactory<driver, String>("car"));
        tblPoints.setCellValueFactory(new PropertyValueFactory<driver, String>("points"));
        dupTable.setItems(driverList);

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
         drList= pair.getKey();
         duplicated=pair.getValue();
         String name = null;
         int dupRow = drList.size()-1;
         for (int i=0;i<drList.size();i++)
         {
             name=drList.get(i).getName();
             if (duplicated.equals(true)&&i==dupRow)
             {
                 dupTable.setRowFactory(driverTableView -> {
                     TableRow<driver> row = new TableRow<>();
                     BooleanBinding similar = row.itemProperty().isEqualTo(drList.get(dupRow)).
                             and(row.itemProperty().isNotNull());
                     row.styleProperty().bind(Bindings.when(similar).then("-fx-background-color: #d71313 ;")
                             .otherwise("")
                     );
                     return row;

                 });
             }
             dupDrivers.add(drList.get(i));
         }
         if (duplicated.equals(false))
         {
             dupBtnChange.setDisable(false);// disablichangeng button for proceeding wihtout
             dupLblHead.setText("there are multiple records with the same name");
             dupLblName.setText(name);
             dupLblCar.setVisible(false);
             dupTxtCar.setDisable(true);
             dupTxtCar.setVisible(false);
             dupLblCar.setVisible(false);
             dupBtnChange.setDisable(false);
             
         } else if (duplicated.equals(true))
         {
             dupLblHead.setText("there is a similar record, change either the name or the car");
             String dupName= drList.get(drList.size()-1).getName();
             String dupCar= drList.get(drList.size()-1).getCar();
             dupLblName.setText(dupName);
             dupLblCar.setText(dupCar);
             dupBtnChange.setDisable(true);
         }
         setTable(dupDrivers);


    }

    public void onCancelClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();

    }

    public void onApplyClick(ActionEvent event) throws IOException {
        Boolean text=true;
        String name=dupTxtName.getText();
        String car=dupTxtCar.getText();
        if (duplicated.equals(true))
        {
            if (dupTxtName.getText().equals("") && dupTxtCar.getText().equals("")){
                dupTxtName.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                dupTxtCar.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                dupLblWarn.setText("fill at least one text field");
                dupLblWarn.setVisible(true);
                text=false;

            }
            else if ((name.equals(previous.getName()) &&dupTxtCar.getText().equals(""))|| car.equals(previous.getCar())&&dupTxtName.getText().equals(""))
            {
                dupTxtName.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                dupTxtCar.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                dupLblWarn.setVisible(true);
                text=false;
            }
            else
            {
                if (dupTxtName.getText().equals("")){
                    name=previous.getName();
                }
                else {
                    name=dupTxtName.getText();
                }
                if (dupTxtCar.getText().equals("")){
                    car=previous.getCar();
                }
                else {car=dupTxtCar.getText();}

            }


        }
        else
        {
            if (dupTxtName.getText().equals("")){
                dupTxtName.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                text=false;
            }
            else
            {
                name=dupTxtName.getText();
                car= previous.getCar();
            }

        }
        driver dr = new driver(previous.getId(),name,previous.getAge(), previous.getTeam(),car, previous.getPoints(),previous.getPositions());
        if (text) {
            driverData drData = new driverData();
            drData.loadData();
            if (ifadd==true)
            {

                drData.add(dr);

            }
            else
            {
                drData.update(dr);
            }
            drData.saveDrivers();
            paneNavigator.loadPane(paneNavigator.edit);
            close();
        }



    }

    public void onChangeClick(ActionEvent event) throws IOException {

        driverData drData= new driverData();
        drData.loadData();
        if (ifadd==true)
        {
        drData.add(previous);
//            if (path!="")
//            {
//                String extension = path.substring(path.length()-4);
//                String file ="driverImages/"+String.valueOf(previous.getId())+extension;
//                Path dir = Paths.get(file);
//                Files.copy(Paths.get(path),dir);
//            }
//            else
//            {
//                String file ="driverImages/"+String.valueOf(previous.getId())+".jpg";
//                String def="driverImages/default.jpg";
//                Path def1 = Paths.get(def);
//                Files.copy(def1,Paths.get(file));
//            }
            String extension = path.toString().substring(path.toString().length()-4);
            String file ="driverImages/"+previous.getId()+extension;
            drData.add(previous);
            Path dir = Paths.get(file);
            Files.copy(path,dir);

        }
        else if (ifadd==false)
        {
         drData.update(previous);
        }
        drData.saveDrivers();
        paneNavigator.loadPane(paneNavigator.edit);
        close();


    }
    private void close()
    {
        Stage stage = (Stage) (dupBtnCancel.getScene().getWindow());
        stage.close();
    }
}
