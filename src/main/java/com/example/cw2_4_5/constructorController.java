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
import java.util.*;

public class constructorController implements Initializable {
    driverData drData;
    @FXML
    public TableView<driverRace> conTbl;
    @FXML
    public TableColumn<driverRace,Integer> conTblPos;
    @FXML
    public TableColumn<driverRace,String> conTblName;


    @FXML
    public TableColumn<driverRace,String> conTblMont;
    @FXML
    public TableColumn<driverRace,String> conTblSweden;
    @FXML
    public TableColumn<driverRace,String> conTblMexico;
    @FXML
    public TableColumn<driverRace,String> conTblCroatia;
    @FXML
    public TableColumn<driverRace,String> conTblPortugal;
    @FXML
    public TableColumn<driverRace,String> conTblSardegna;
    @FXML
    public TableColumn<driverRace,String> conTblKenya;
    @FXML
    public TableColumn<driverRace,String> conTblEstonia;
    @FXML
    public TableColumn<driverRace,String> conTblFinland;
    @FXML
    public TableColumn<driverRace,String> conTblGreece;
    @FXML
    public TableColumn<driverRace,String> conTblChile;
    @FXML
    public TableColumn<driverRace,String> conTblEurope;
    @FXML
    public TableColumn<driverRace,String> conTblJapan;
    @FXML
    public TableColumn<driverRace,Integer> conTblTotal;

    public void onm(MouseEvent mouseEvent) {
    }

    public void onCloseClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void onMinClick(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void onMenuClick(ActionEvent event) {
        paneNavigator.loadPane(paneNavigator.menu);
    }



    private void setTable(ObservableList<driverRace> constructors)
    {

        conTblPos.setCellValueFactory(new PropertyValueFactory<driverRace,Integer>("pos"));
        conTblName.setCellValueFactory(new PropertyValueFactory<driverRace,String>("name"));
        conTblMont.setCellValueFactory(new PropertyValueFactory<driverRace,String>("monteCarlo"));
        conTblSweden.setCellValueFactory(new PropertyValueFactory<driverRace,String>("sweden"));
        conTblMexico.setCellValueFactory(new PropertyValueFactory<driverRace,String>("mexico"));
        conTblCroatia.setCellValueFactory(new PropertyValueFactory<driverRace,String>("croatia"));
        conTblPortugal.setCellValueFactory(new PropertyValueFactory<driverRace,String>("portugal"));
        conTblSardegna.setCellValueFactory(new PropertyValueFactory<driverRace,String>("sardegna"));
        conTblKenya.setCellValueFactory(new PropertyValueFactory<driverRace,String>("kenya"));
        conTblEstonia.setCellValueFactory(new PropertyValueFactory<driverRace,String>("estonia"));
        conTblFinland.setCellValueFactory(new PropertyValueFactory<driverRace,String>("finland"));
        conTblGreece.setCellValueFactory(new PropertyValueFactory<driverRace,String>("greece"));
        conTblChile.setCellValueFactory(new PropertyValueFactory<driverRace,String>("chile"));
        conTblEurope.setCellValueFactory(new PropertyValueFactory<driverRace,String>("europe"));
        conTblJapan.setCellValueFactory(new PropertyValueFactory<driverRace,String>("japan"));
        conTblTotal.setCellValueFactory(new PropertyValueFactory<driverRace,Integer>("total"));
        conTbl.setItems(constructors);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drData = new driverData();
        try {
            drData.loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObservableList<driver> drList = drData.getDriverList();
        ArrayList<String> teams = new ArrayList<>();
        ArrayList<Integer> totalPoints = new ArrayList<>();
        ObservableList<driverRace> constructors = FXCollections.observableArrayList();


        if (drList.size()>0)
        {
            teams.add(drList.get(0).getTeam());// making a list with the teams
            for (driver dr:drList)
            {
                String team =dr.getTeam();
                if (!teams.contains(team))
                {
                    teams.add(team);
                }
            }
        }
        for (String s:teams)
        {
            totalPoints.add(0);
        }
        ArrayList<ArrayList<String>> races = new ArrayList<>();
        for (int i =0;i< teams.size();i++)
        {
            String team = teams.get(i);
            int points = totalPoints.get(i);
            ArrayList<String> racePoints = new ArrayList<>();
            for (int k=0;k<13;k++)
            {
                racePoints.add("_");
            }
            for (driver dr:drList)
            {
                if (dr.getTeam().equals(team))
                {
                    points += Integer.parseInt(dr.getPoints());
                    ArrayList<String> positions = dr.getPositions();
                    for (int j=0;j<positions.size();j++)
                    {
                        if (positions.get(j)!="_")
                        {
                            int points1;
                            try {
                                 points1 = Integer.parseInt(racePoints.get(j));
                            }
                            catch (NumberFormatException e)
                            {
                                points1 =0;
                            }

                            if (positions.get(j).equals("1"))
                            {
                                points1+=10;
                            }
                            else if (positions.get(j).equals("2"))
                            {
                                points1 +=7;
                            }
                            else if (positions.get(j).equals("3"))
                            {
                                points1+=5;
                            }
                            racePoints.set(j,String.valueOf(points1));

                        }
                    }

                }
            }
            races.add(racePoints);
            totalPoints.set(i,points);
        }
        ArrayList<String> sortedTeams = new ArrayList<>();
        ArrayList<Integer> sortedPoints = new ArrayList<>();
        ArrayList<ArrayList<String>> sortedRacePoints = new ArrayList<>();
        while (totalPoints.size()>0)
        {
            for (int i=0;i< totalPoints.size();i++)
            {
                int points = totalPoints.get(i);
                if (points==Collections.max(totalPoints))
                {
                    sortedPoints.add(points);
                    sortedTeams.add(teams.get(i));
                    sortedRacePoints.add(races.get(i));
                    totalPoints.remove(i);
                    teams.remove(i);
                    races.remove(i);
                }
            }
        }
        for (int i=0;i< sortedPoints.size();i++)
        {
            ArrayList<String> eachrace = sortedRacePoints.get(i);
            driverRace constructor = new driverRace(sortedTeams.get(i)," "," ",eachrace.get(0),eachrace.get(1),
                    eachrace.get(2),eachrace.get(3),eachrace.get(4),eachrace.get(5),eachrace.get(6),eachrace.get(7),
                    eachrace.get(8),eachrace.get(9),eachrace.get(10),eachrace.get(11),eachrace.get(12),String.valueOf(sortedPoints.get(i)),i+1);
            constructors.add(constructor);
        }
        setTable(constructors);


}
}
