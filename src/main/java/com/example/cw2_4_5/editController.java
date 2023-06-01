package com.example.cw2_4_5;

import javafx.application.Platform;
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
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.lang.constant.DirectMethodHandleDesc;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;


public class editController implements Initializable {

    @FXML
    public TableView<driver> editTable;
    @FXML
    public TableColumn<driver,String> tblName;
    @FXML
    public TableColumn<driver,String> tblAge;
    @FXML
    public TableColumn<driver,String> tblTeam;
    @FXML
    public TableColumn<driver,String> tblCar;
    @FXML
    public TableColumn<driver,String> tblPoints;
    @FXML
    public TableColumn<driver,Integer> tblId;
    @FXML
    private Label editLblName;
    @FXML private Label editLblAge;
    @FXML private Label editLblTeam;
    @FXML private Label editLblCar;
    @FXML private Label editLblPoints;
    @FXML private Label editLblHead;
    @FXML
    TextField editTxtname;
    @FXML
    TextField editTxtage;
    @FXML
    TextField editTxtTeam;
    @FXML
    TextField editTxtCar;
    @FXML
    TextField editTxtPoints;
    @FXML
    Button editBtnAdd;
    @FXML TextField editTxtId;

    Boolean carChanged;
    Boolean nameChanged;
    String previousname = null;
    String previousCar =null;
    driver prervious;
    int c=0;
    int id;

    private driverData drData;
    private ObservableList<driver> drList;

    private final Object pause = new Object();


    public void setId(int id) {
        this.id = id;
    }

    public void clearTxt(){
        editTxtname.clear();
        editTxtage.clear();
        editTxtTeam.clear();
        editTxtCar.clear();
        editTxtPoints.clear();
        editTxtId.clear();
        editTxtname.setStyle("-fx-border-color:'fc4e00'; -fx-border-width: 2px ;");
        editTxtage.setStyle("-fx-border-color:'fc4e00'; -fx-border-width: 2px ;");
        editTxtTeam.setStyle("-fx-border-color:'fc4e00'; -fx-border-width: 2px ;");
        editTxtPoints.setStyle("-fx-border-color:'fc4e00'; -fx-border-width: 2px ;");
        editTxtCar.setStyle("-fx-border-color:'fc4e00'; -fx-border-width: 2px ;");

    }
    public void clearlabels(){
        editLblName.setText(null);
        editLblAge.setText(null);
        editLblTeam.setText(null);
        editLblCar.setText(null);
        editLblPoints.setText(null);
        editLblHead.setText(null);
    }

    private class compare // used to compare two driver objects
    {
        private final String name;
        private final String age;
        private final String team;
        private final String car;
        private final String points;


        private compare(String name, String age, String team, String car, String points) {
            this.name = name;
            this.age = age;
            this.team = team;
            this.car = car;
            this.points = points;
        }

        public String getName() {
            return name;
        }
        @Override public boolean equals(Object obj)
        {
            if (this ==obj)
            {
                return true;
            }
            compare comp =(compare) obj;
            return this.name.equals(comp.name)&&this.age.equals(comp.age)&&this.team.equals(comp.team)&&this.car.equals(comp.car)&&this.points.equals(comp.points);
        }

    }
    private Pair<ArrayList<driver>,Boolean> checkDuplication(compare inputDriver)
    {
        Boolean duplicated=false ;
        driver similarDriver= null;
        ArrayList<driver> dup = new ArrayList<>();
        for (driver driver:drList)// the input driver is checked against every driver using compare class
        {
            compare compareDriver= new compare(driver.getName(), driver.getAge(),driver.getTeam(), driver.getCar(), driver.getPoints());
            if (compareDriver.equals(inputDriver))
            {
                similarDriver=driver;
                duplicated=true;

            }

           else if (driver.getName().equals(inputDriver.getName()))
            {
                dup.add(driver);// if only the names are similar and the rest is different
            }


        }
        if (duplicated.equals(true))
        {
         dup.add(similarDriver);

        }
        return new Pair<>(dup,duplicated);
    }
    public Pair<ArrayList<String>,Boolean> getData() {
        boolean completed=true;
        ArrayList<String> record = new ArrayList<String>();
        clearlabels();
        Boolean[] comp = {true,true,true,true,true};// used to verify if all the text fields are filled out
        String name = editTxtname.getText();
        if (editTxtname.getText()== null || editTxtname.getText().trim().isEmpty())
        {editTxtname.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");comp[0] = false; editLblHead.setText("incomplete fields");}
        String age = editTxtage.getText();
        if (editTxtage.getText()== null || editTxtage.getText().trim().isEmpty())
        {editTxtage.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");comp[1] = false;editLblHead.setText("incomplete fields");}
        String team = editTxtTeam.getText();
        if (editTxtTeam.getText()== null || editTxtTeam.getText().trim().isEmpty())
        {editTxtTeam.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");comp[2]=false;editLblHead.setText("incomplete fields");}
        String car = editTxtCar.getText();
        if (editTxtCar.getText()== null || editTxtCar.getText().trim().isEmpty())
        {editTxtCar.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");comp[3]=false;editLblHead.setText("incomplete fields");}
        String points = editTxtPoints.getText();
        if (editTxtPoints.getText()== null || editTxtPoints.getText().trim().isEmpty())
        {editTxtPoints.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");comp[4]=false;editLblHead.setText("incomplete fields");}

        for (boolean bool:comp)
        {
            if (bool==false)
            {
                completed=false;
            }
        }
                    record.add(name);
                    record.add(age);
                    record.add(team);
                    record.add(car);
                    record.add(points);


        return new Pair<>(record,completed);
    }


    public void onMinClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();//minimize the app
        stage.setIconified(true);
    }

    public void onCloseClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();//close the app
        stage.close();
    }


    private void updateTable(int search,String query)
    {
        ObservableList<driver> updatedDriver = FXCollections.observableArrayList();
        ObservableList<driver> Driver = FXCollections.observableArrayList();

        Driver=drData.getDriverList();

            if (search == 1)// when id is changed
            {
                for (driver dr:Driver)
                {
                if (Integer.toString(dr.getId()).startsWith(query)){updatedDriver.add(dr);}
                }
                editTable.getSelectionModel().select(-1);// making the tableview select nothing

            }
            else if (search == 2)// when name is changed
            {
                for (driver dr:Driver) {
                    if (dr.getName().startsWith(query)) {
                        updatedDriver.add(dr);
                    }
                }

            }
            else if (search == 3)//when age is changed
            {
                for (driver dr:Driver)
                {
                if (dr.getAge().startsWith(query)){updatedDriver.add(dr);}
                }
            }
            else if (search == 4)//when team is changed
            {
                for (driver dr:Driver)
                {
                if (dr.getTeam().startsWith(query)){updatedDriver.add(dr);}
                }
            }
            else if (search == 5)//when car is changed
            {
                for (driver dr:Driver) {
                    if (dr.getCar().startsWith(query)){updatedDriver.add(dr);}
                }
            }
            else if (search == 6) {// when points are changed
                for (driver dr:Driver)
                {
                    if (dr.getPoints().startsWith(query)){updatedDriver.add(dr);}
                }
            }
            else if (search==7){// when all the elements needs to be added without filtering
                updatedDriver.addAll(Driver);

            }
            try {


                tblId.setCellValueFactory(new PropertyValueFactory<driver, Integer>("id"));
                tblName.setCellValueFactory(new PropertyValueFactory<driver, String>("name"));
                tblAge.setCellValueFactory(new PropertyValueFactory<driver, String>("age"));
                tblTeam.setCellValueFactory(new PropertyValueFactory<driver, String>("team"));
                tblCar.setCellValueFactory(new PropertyValueFactory<driver, String>("car"));
                tblPoints.setCellValueFactory(new PropertyValueFactory<driver, String>("points"));
                editTable.setItems(updatedDriver);
                editTable.getSelectionModel().selectedItemProperty().addListener((observableValue, driver, t1) -> {
                    prervious = new driver(t1.getId(), t1.getName(), t1.getAge(), t1.getTeam(), t1.getCar(), t1.getPoints(), t1.getPositions());
                    carChanged = false;// so that names wont be checked for duplication if it is not changed
                    nameChanged = false;
                    previousname = t1.getName();
                    previousCar = t1.getCar();
                    editTxtId.setText(Integer.toString(t1.getId()));
                    editTxtname.setText(t1.getName());
                    editTxtage.setText(t1.getAge());
                    editTxtTeam.setText(t1.getTeam());
                    editTxtCar.setText(t1.getCar());
                    editTxtPoints.setText(t1.getPoints());


                });
            }catch (NullPointerException e) { }

    }

    public void onMenuClick(ActionEvent event) {
        paneNavigator.loadPane(paneNavigator.menu);
    }

    public void onAddNClick(ActionEvent event) {

        editBtnAdd.setDisable(false);
    }

    public void onUpdateClick(ActionEvent event) throws IOException {
        Pair<ArrayList<String>,Boolean> pair = getData();
        ArrayList<String> data = pair.getKey();
        compare comp = new compare(data.get(0),data.get(1),data.get(2),data.get(3),data.get(4));
        boolean selected = true;
        try {
            String s = prervious.getName();// checks weather the previous object is null or not

        }
        catch (NullPointerException e)
        {
            selected = false;
        }
        if (pair.getValue().equals(true)&& selected)
        {
            if (nameChanged || carChanged)// name or car is changed it is checked for duplication
            {
                Pair<ArrayList<driver>, Boolean> dupPair =checkDuplication(comp);
                if (dupPair.getValue().equals(false)&&dupPair.getKey().size()==0||dupPair.getValue().equals(false)&&dupPair.getKey().size()==1)
                {
                    driver updateDriver = new driver(prervious.getId(),data.get(0),data.get(1),data.get(2),data.get(3),data.get(4),prervious.getPositions());
                    drData.update(updateDriver);
                    drData.saveDrivers();
                }
                else
                {
                    paneNavigator.loadDupBox(dupPair,prervious,false);
                }


            }
            else // if not it is added
            {
                driver updateDriver = new driver(prervious.getId(),data.get(0),data.get(1),data.get(2),data.get(3),data.get(4),prervious.getPositions());
                drData.update(updateDriver);
                drData.saveDrivers();
            }
        }


        updateTable(7,null);
    }

    public void onDltClick(ActionEvent event) throws IOException {
        try {
            String s = prervious.getName();// checks whether the previous driver is null or not
            paneNavigator.loadDeleteAlert("delete.fxml",prervious);
        }
        catch (NullPointerException e)
        {

        }

        updateTable(7,null);
    }

    public void onAddClick(ActionEvent event) throws IOException {

        Pair<ArrayList<String>,Boolean> pair = getData();// gets the data from the text fields
        ArrayList<String> positions = new ArrayList<>();// to create arraylist for the positions attribute
        // getting the inputs
        ArrayList<String> data= pair.getKey();

        // if all the textfields are populated
        if (pair.getValue().equals(true))

        {
            //id++;
            for (int i = 0; i < 13; i++) {//creates an arraylist which will be used to store race positions
                positions.add("_");
            }
            compare input = new compare(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4));
            Pair<ArrayList<driver>,Boolean> dupPair = checkDuplication(input);
            if (dupPair.getValue().equals(false)&&dupPair.getKey().size()==0)
            {
                id++;
                driver addDriver = new driver(id,data.get(0), data.get(1), data.get(2), data.get(3), data.get(4),positions);
                drData.add(addDriver);
                drData.saveDrivers();


                updateTable(7, null);

            }
            else
            {

                id++;
                driver addDriver = new driver(id,data.get(0), data.get(1), data.get(2), data.get(3), data.get(4),positions);
                paneNavigator.loadDupBox(dupPair,addDriver,true);

            }

        }
        editBtnAdd.setDisable(true);



    }


    public void onClearClick(ActionEvent event) {
        clearTxt();
        clearlabels();

    }

    public void onAgeChanged(KeyEvent keyEvent)
    {
        editTable.getSelectionModel().select(null);
        try {
            int i = Integer.parseInt(editTxtage.getText());
        }
        catch (NumberFormatException e)
        {
            editTxtage.clear();
        }
       // editTxtage.setTextFormatter(new TextFormatter<Object>(new PositiveIntegerFilter()));
        updateTable(3,editTxtage.getText());
    }

    public void onTeamChanged(KeyEvent keyEvent) {
        editTable.getSelectionModel().select(null);
        updateTable(4,editTxtTeam.getText());
    }

    public void onCarChanged(KeyEvent keyEvent) {
        editTable.getSelectionModel().select(null);
        carChanged=true;
        updateTable(5,editTxtCar.getText());
    }

    public void onPointsChanged(KeyEvent keyEvent) {
        editTable.getSelectionModel().select(null);
        try {
            int i = Integer.parseInt(editTxtPoints.getText());
        }
        catch (NumberFormatException e)
        {
            editTxtPoints.clear();
        }

        updateTable(6,editTxtPoints.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       drData = new driverData();

        try {
            drData.loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        drList = drData.getDriverList();
        updateTable(7,null);
        ArrayList<Integer> ids = new ArrayList<>();
        if (drList.size()==0){
            ids.add(0);
        }
        else {
            for (driver dr:drList){
                ids.add(dr.getId());
            }
            for (driver dr:drData.getDeletedList())
            {
                ids.add(dr.getId());
            }

        }
        id = Collections.max(ids);





    }

    public void onNameChanged(KeyEvent keyEvent) {
        editTable.getSelectionModel().select(null);
        nameChanged=true;
        updateTable(2,editTxtname.getText());
    }

    public void onIdChange(KeyEvent keyEvent) {
        editTable.getSelectionModel().select(null);
        updateTable(1,editTxtId.getText());
    }
    //for use in testing
    protected driver testAdd(String name,String age,String team,String car,String points) throws IOException {
        editTxtname.setText(name);
        editTxtage.setText(age);
        editTxtTeam.setText(team);
        editTxtCar.setText(car);
        editTxtPoints.setText(points);
        ActionEvent event = new ActionEvent();
        onAddClick(event);

        return drData.getDriverList().get(drData.getDriverList().size()-1);
    }
}
