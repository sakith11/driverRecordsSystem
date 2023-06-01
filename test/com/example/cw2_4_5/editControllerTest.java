package com.example.cw2_4_5;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class editControllerTest  {




    @Test
    void onUpdateClick()
    {
        ArrayList<String> positions = new ArrayList<>();
        for (int i=0;i<13;i++)
        {
            positions.add("_");
        }
        driver dr = new driver(1,"carl","22","ford","f1","22",positions);
        driverData drdata = new driverData();
        drdata.add(dr);
        driver dr1 = new driver(1,"carl junior","22","ford","f1","22",positions);
        drdata.update(dr1);
        assertEquals(dr1.getName(),drdata.getDriverList().get(0).getName());
    }

    @Test
    void onDltClick()
    {
        ArrayList<String> positions = new ArrayList<>();
        for (int i=0;i<13;i++)
        {
            positions.add("_");
        }
        driver dr = new driver(1,"carl","22","ford","f1","22",positions);
        driver dr1 = new driver(2,"carl1","22","ford","f1","22",positions);
        driver dr2 = new driver(3,"carl2","22","ford","f1","22",positions);
        driver dr3 = new driver(4,"carl3","22","ford","f1","22",positions);
        driverData drdata = new driverData();
        drdata.add(dr);
        drdata.add(dr1);
        drdata.add(dr2);
        drdata.add(dr3);
        drdata.delete(dr2);

        for (driver driver:drdata.getDriverList())
        {

            assertNotEquals(driver.getId(),3);
        }

    }

    @Test
    void onAddClick() throws IOException, InterruptedException
    {
        ArrayList<String> positions = new ArrayList<>();
        for (int i=0;i<13;i++)
        {
            positions.add("_");
        }
        driver dr = new driver(1,"carl","22","ford","f1","22",positions);
        driverData drdata = new driverData();
        drdata.add(dr);
        assertEquals(dr.getName(),drdata.getDriverList().get(0).getName());

    }



}