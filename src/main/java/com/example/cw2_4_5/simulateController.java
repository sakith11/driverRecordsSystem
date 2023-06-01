package com.example.cw2_4_5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class simulateController  implements Initializable {
    @FXML
    private ComboBox simComboRace;
    @FXML
    private ComboBox simComboWheather;
    @FXML
    private DatePicker simDate;
    @FXML
    public TableView<simulateObject> simTbl;
    @FXML
    public TableColumn<simulateObject,String> simTblRace;
    @FXML
    public TableColumn<simulateObject,LocalDate> simTblDate;
    @FXML
    public TableColumn<simulateObject,String> simTblWheather;
    @FXML
    public TableColumn<simulateObject,String> simTblFirst;
    @FXML
    public TableColumn<simulateObject,String> simTblSecond;
    @FXML
    public TableColumn<simulateObject,String> simTblThird;


    private driverData dData = new driverData();

    ArrayList<String> races =new ArrayList<>();
    ArrayList<Integer> driverId = new ArrayList<>();
    ArrayList<driver> driver = new ArrayList<>();
    ArrayList<Integer> driverPoints= new ArrayList<>();
    ArrayList<Integer> sortedId = new ArrayList<>();

    ArrayList<driver> soetedDriver = new ArrayList<>();
    String raceName;
    String raceWheather;
    LocalDate raceDate;
    ObservableList<simulateObject> simList= FXCollections.observableArrayList();
    ObservableList<race> raceList = FXCollections.observableArrayList();
    ObservableList<driver> driverList = FXCollections.observableArrayList();
    ObservableList<driver> deletedList = FXCollections.observableArrayList();
    simulateObject clicked;
    DirectoryChooser dirchooser = new DirectoryChooser();
    public void onMenuClick(ActionEvent event) {
        paneNavigator.loadPane(paneNavigator.menu);
    }
    private void simulate()
    {
        double winProb = 0;
        double loseProb = 0;
        raceName=simComboRace.getValue().toString();
        raceWheather=simComboWheather.getValue().toString();
        raceDate=simDate.getValue();
        int total=0;
        for (int point:driverPoints)
        {
            total+=point;
        }
        if (total==0){total+=1;}
        ArrayList<Double> prob = new ArrayList<>();
        for (int point:driverPoints)
        {
            prob.add((double)point/total);
        }

        if (raceWheather.equals("Sunny")){winProb=0.5;loseProb=0.5;}
        else if (raceWheather.equals("Light rain")) {winProb=0.4;loseProb=0.5;}
        else if (raceWheather.equals("Heavy rain")) {winProb=0.3;loseProb=0.6;}
        else if (raceWheather.equals("Light snow")) {winProb=0.4;loseProb=0.6;}
        else if (raceWheather.equals("Heavy snow")) {winProb=0.3;loseProb=0.7;}

        Random rand =new Random();
        int random1=rand.nextInt(0,prob.size());
        int random2 =rand.nextInt(0,prob.size());
        double prob1= prob.get(random1);
        prob.set(random1,prob1+winProb);
        double prob2 = prob.get(random2);
        prob.set(random2,prob2-loseProb);
        while (prob.size()!=0)
        {
            for (int i = 0; i < prob.size(); i++) {
                if (prob.get(i).equals(Collections.max(prob))) {
                    soetedDriver.add(driver.get(i));
                    driver.remove(i);
                   sortedId.add(driverId.get(i));

                    driverId.remove(i);

                    prob.remove(i);
                }
            }
        }
        race race = new race(raceName,raceWheather,raceDate,sortedId);
        dData.addRace(race);
        dData.saveRace();
        int racenum=0;
        for (int i=0;i<races.size();i++)
        {
            if (races.get(i).equals(raceName)){racenum=i;break;}
        }
        for (driver dr:driverList)
        {
            Integer points=Integer.parseInt(dr.getPoints());
            ArrayList<String> positions = dr.getPositions();

            for (int i=0;i<sortedId.size();i++)
            {
                if (sortedId.get(i).equals(dr.getId()))
                {
                    Integer pos = i+1;
                    positions.set(racenum,pos.toString());
                    if (i==0){points+=10;}
                    else if (i==1) {points+=7;}
                    else if (i==2) {points+=5;}
                    driver dr1= new driver(dr.getId(),dr.getName(),dr.getAge(),dr.getTeam(),dr.getCar(),points.toString(),positions);
                    dData.update(dr1);
                    break;
                }
            }


        }
        dData.saveDrivers();
        simulateObject sim = new simulateObject(raceName,raceDate,raceWheather,soetedDriver.get(0).getName(),soetedDriver.get(1).getName(),soetedDriver.get(2).getName());
        simList.add(sim);
        paneNavigator.loadPane(paneNavigator.simulate);

    }
    private void setTable(ObservableList<simulateObject> simList)
    {
        simTblRace.setCellValueFactory(new PropertyValueFactory<simulateObject,String>("raceName"));
        simTblDate.setCellValueFactory(new PropertyValueFactory<simulateObject,LocalDate>("date"));
        simTblWheather.setCellValueFactory(new PropertyValueFactory<simulateObject,String>("wheather"));
        simTblFirst.setCellValueFactory(new PropertyValueFactory<simulateObject,String>("firstName"));
        simTblSecond.setCellValueFactory(new PropertyValueFactory<simulateObject,String>("secondName"));
        simTblThird.setCellValueFactory(new PropertyValueFactory<simulateObject,String>("thirdName"));
        simTbl.setItems(simList);
        simTbl.setRowFactory(tv->{
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
                else if (mouseEvent.getClickCount()==1 && !row.isEmpty())
                {
                    clicked =row.getItem();

                }
            });
            return row;
        });
    }

    public void onSimulateClick(ActionEvent event) throws IOException
    {
        //checking if fields are empty
        boolean completed =true;

        try {
            raceName = simComboRace.getValue().toString();


        }
        catch (NullPointerException e)
        {
            simComboRace.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;-fx-border-radius:2px");
            completed =false;

        }
        try {

            raceWheather = simComboWheather.getValue().toString();


        }
        catch (NullPointerException e)
        {
            simComboWheather.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;-fx-border-radius:2px");
            completed =false;

        }
        try {


            raceDate = simDate.getValue();

        }
        catch (NullPointerException e)
        {
            simDate.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;-fx-border-radius:2px");
            completed = false;

        }
        catch (DateTimeException e)
        {
            simDate.setStyle("-fx-background-color: red ; -fx-border-width: 2px ;-fx-border-radius:2px");
            completed = false;
        }
        if (completed = true)// if all the fields are populated
        {
            simulate();
        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try {
            dData.loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] racesNames={"Monte Carlo","Sweden","Mexico","Croatia","Portugal","Sardegna","Kenya","Estonia","Finland","Greece","Chile","Europe","Japan"};
        for (String name:racesNames)
        {
            races.add(name);
        }
        raceList= dData.getRaceList();
        driverList = dData.getDriverList();
        deletedList =dData.getDeletedList();
        ArrayList<LocalDate> compDates = new ArrayList<>();
        ArrayList<String> compRaces = new ArrayList<>();



        for (driver dr:driverList){
            driver.add(dr);
            driverId.add(dr.getId());

            driverPoints.add(Integer.parseInt(dr.getPoints()));

        }
        ArrayList<Integer> driverIdCopy = new ArrayList<>(driverId);
        ArrayList<driver> driverCopy = new ArrayList<>(driver);

        for (driver dr:deletedList){
            driverIdCopy.add(dr.getId());
            driverCopy.add(dr);


        }

        for (race race1:raceList)
        {
            compDates.add(race1.getDate());
            compRaces.add(race1.getName());
            String first="redacted";
            String second="redacted";
            String third = "redacted";
            ArrayList<Integer> pos = race1.getPositions();
            try{
                first= driverCopy.get(driverIdCopy.indexOf(pos.get(0))).getName();
            }
            catch (IndexOutOfBoundsException e)
            {

            }
            try{
                second= driverCopy.get(driverIdCopy.indexOf(pos.get(1))).getName();
            }
            catch (IndexOutOfBoundsException e)
            {

            }
            try{
                third =driverCopy.get(driverIdCopy.indexOf(pos.get(2))).getName();
            }
            catch (IndexOutOfBoundsException e)
            {

            }

            simulateObject sim = new simulateObject(race1.getName(),race1.getDate(),race1.getWheather(),first,second,third);
            simList.add(sim);
        }

        simComboRace.getItems().clear();
        simComboRace.getItems().setAll(races);
        // making the already simulated races un available
        simComboRace.setCellFactory((new Callback<ListView<String>, ListCell<String >>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<String>()
                {
                    @Override
                    protected void updateItem(String race,boolean empty)
                    {
                        super.updateItem(race,empty);
                        if (!compRaces.contains(race))
                        {
                            this.setText(race);
                            this.setDisable(false);
                        }
                        else
                        {
                            this.setText(race);
                            this.setDisable(true);
                            this.setStyle("-fx-background-color:gray");
                        }
                    }
                };
            }
        }));
        // making the race dates that were completed unavailable
        simDate.setDayCellFactory(picker -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date,empty);
                    LocalDate today = LocalDate.now();
                    if (empty || compDates.contains(date)) {
                        setDisable(true);
                    }
                }
            };
        });

        simComboWheather.getItems().clear();
        simComboWheather.getItems().setAll("Sunny","Light rain","Heavy rain","Light snow","Heavy snow");
        setTable(simList);


    }

    public void onMinClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void onCloseClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onExportClick(ActionEvent event) throws IOException {

        try {


            if (!clicked.equals(null)) {
                dirchooser.setInitialDirectory(new File("C:\\"));// choosing a folder
                File selected = dirchooser.showDialog(new Stage());
                ObservableList<driver> drList = dData.getDriverList();
                ObservableList<race> raceList = dData.getRaceList();
                ArrayList<Integer> positions = new ArrayList<>();
                ArrayList<raceInfo> raceInfoList = new ArrayList<>();
                ArrayList<Integer> nameSize = new ArrayList<>();//lengths of names,teams,cars to make the file table
                ArrayList<Integer> teamSize = new ArrayList<>();
                ArrayList<Integer> carSize = new ArrayList<>();
                String fileName = clicked.getRaceName() + "  " + clicked.getDate().toString() + ".txt";
                int maxCar = 0;
                int maxName = 0;
                int maxTeam = 0;

                for (race Race : raceList) {
                    if (Race.getName().equals(clicked.getRaceName())) {
                        positions = Race.getPositions();

                    }

                }
                for (int i = 0; i < positions.size(); i++) {
                    for (driver dr : driverList) {
                        if (dr.getId() == positions.get(i)) {
                            int points = 0;
                            if (i == 0) {
                                points = 10;
                            } else if (i == 1) {
                                points = 7;
                            } else if (i == 2) {
                                points = 5;
                            }

                            raceInfo rInfo = new raceInfo(i + 1, dr.getName(), dr.getTeam(), dr.getCar(), points);
                            raceInfoList.add(rInfo);

                        }

                    }
                }
                for (raceInfo r : raceInfoList) {
                    nameSize.add(r.getName().length());
                    teamSize.add(r.getTeam().length());
                    carSize.add(r.getCar().length());

                }
                maxName = Collections.max(nameSize);
                if (!(maxName > 4)) {
                    maxName = 4;
                }
                maxTeam = Collections.max(teamSize);
                if (!(maxTeam > 4)) {
                    maxTeam = 4;
                }

                maxCar = Collections.max(carSize);
                if (!(maxCar > 3)) {
                    maxCar = 3;
                }

                selected.toPath().toFile().createNewFile();
                File file = new File(selected, fileName);
                file.createNewFile();
                Path filePath = Paths.get(file.getPath());
                String header1 = "+-----" + "+" + "-".repeat(maxName) + "+" + "-".repeat(maxTeam) + "+" + "-".repeat(maxCar) + "+" + "------+" + System.lineSeparator();
                String header2 = "|pos  |" + "Name" + " ".repeat(maxName - 4) + "|" + "Team" + " ".repeat(maxTeam - 4) + "|" + "Car"
                        + " ".repeat(maxCar - 3) + "|Points|" + System.lineSeparator();
                Files.write(filePath, header1.getBytes(), StandardOpenOption.APPEND);
                Files.write(filePath, header2.getBytes(), StandardOpenOption.APPEND);
                Files.write(filePath, header1.getBytes(), StandardOpenOption.APPEND);
                for (raceInfo rInfo : raceInfoList) {
                    String pos = String.valueOf(rInfo.getPos());
                    int posS = pos.length();
                    String name = rInfo.getName();
                    int nameS = name.length();
                    String team = rInfo.getTeam();
                    int teamS = team.length();
                    String car = rInfo.getCar();
                    int carS = car.length();
                    String points = String.valueOf(rInfo.getPoints());
                    int pointsS = points.length();
                    String row = "|" + pos + " ".repeat(5 - posS) + "|" + name + " ".repeat(maxName - nameS) + "|" + team + " ".repeat(maxTeam - teamS) +
                            "|" + car + " ".repeat(maxCar - carS) + "|" + points + " ".repeat(6 - pointsS) + "|" + System.lineSeparator();
                    Files.write(filePath, row.getBytes(), StandardOpenOption.APPEND);
                    Files.write(filePath, header1.getBytes(), StandardOpenOption.APPEND);

                }

            }
        }
        catch (NullPointerException e)
        {

        }

    }
}
