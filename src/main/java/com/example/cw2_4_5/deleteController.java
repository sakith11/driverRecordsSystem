package com.example.cw2_4_5;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class deleteController implements Initializable {
    driver previous;
    @FXML
    private Label dltLblWarn;
    driverData drData;


    public deleteController(driver previous) {
        this.previous = previous;

    }

    public void onDltClick(ActionEvent event) throws IOException {
        drData.delete(previous);
        drData.saveDrivers();

        close();
    }

    public void onCancelClick(ActionEvent event) {

        close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drData=new driverData();
//
        try {
            drData.loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(Integer.parseInt(previous.getPoints())>0)
        {
            dltLblWarn.setText("the driver has scored points in a previous race \n click save to keep the driver in the " +
                    "driver's championship");
        }
        else
        {
            dltLblWarn.setText("click save to keep the driver in driver's championship");
        }
    }

    public void onSaveClick(ActionEvent event) throws IOException {
        drData.delete(previous);
        driver dr = new driver(previous.getId(), previous.getName()+"(N/A)", previous.getAge(), previous.getTeam(),
                previous.getCar(), previous.getPoints(), previous.getPositions());
        drData.saveTODelete(dr);
        drData.saveDrivers();
        drData.saveDelete();

        close();
    }
    private void close()
    {
        //paneNavigator.loadPane(paneNavigator.edit);
        Stage stage = (Stage) (dltLblWarn.getScene().getWindow());
        stage.close();
    }

}
