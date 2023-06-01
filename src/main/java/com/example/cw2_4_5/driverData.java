package com.example.cw2_4_5;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class driverData implements  Serializable{
    private final ObservableList<driver> driverList;
    private final ObservableList<race> raceList;
    private final ObservableList<driver> deletedList;

    public driverData() {
        this.driverList = FXCollections.observableArrayList();
        this.raceList = FXCollections.observableArrayList();
        this.deletedList = FXCollections.observableArrayList();
    }
    public void loadData() throws IOException {
        BufferedReader reader = null;
        String line="";
        String[] row = new String[0];
        try {
            reader = new BufferedReader(new FileReader("src/driverrecords1.csv"));
            while ((line=reader.readLine())!=null){
                row=line.split(",");
                ArrayList<String> races= new ArrayList<>();
                races.addAll(Arrays.asList(row).subList(6, 19));//was19
                driver dr = new driver(Integer.parseInt(row[0]), row[1],row[2],row[3],row[4],row[5],races);
                //setDriverObject(dr);
                driverList.add(dr);
            }
            reader.close();
        }
        catch (IOException e){e.printStackTrace();}
        BufferedReader reader1 = null;
        String line1="";
        String[] row1 = new String[0];
        try {
            reader1 = new BufferedReader(new FileReader("src/racedetails1.csv"));
            while ((line1=reader1.readLine())!=null){
                row1=line1.split(",");
                ArrayList<Integer> positions= new ArrayList<>();
                for (int i=3;i<row1.length;i++){
                    positions.add(Integer.parseInt(row1[i]));
                }
                ArrayList<Integer> drivers  = new ArrayList<>();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                formatter = formatter.withLocale(Locale.ENGLISH);
                LocalDate date = LocalDate.parse(row1[2], formatter);
                race race = new race(row1[0],row1[1],date,positions);
                raceList.add(race);
            }
            reader.close();
        }
        catch (IOException e){e.printStackTrace();}
        BufferedReader reader2 = null;
        String line2="";
        String[] row2 = new String[0];
        try {
            reader2 = new BufferedReader(new FileReader("src/deleted.csv"));
            while ((line2=reader2.readLine())!=null){
                row2=line2.split(",");
                ArrayList<String> races2= new ArrayList<>();
                races2.addAll(Arrays.asList(row2).subList(6, 19));
                driver dr = new driver(Integer.parseInt(row2[0]), row2[1],row2[2],row2[3],row2[4],row2[5],races2);
                deletedList.add(dr);
            }
            reader.close();

        }
        catch (IOException e){}

    }

    public void update(driver updated)
    {
        int count=0;
        for (driver dr:driverList)
        {
            if (dr.getId()==updated.getId())
            {
                break;
            }
            count++;
        }
        driverList.set(count,updated);
    }
    public void delete(driver toDelete)
    {
        int count=0;
        for (driver dr:driverList)
        {
            if (dr.getId()==toDelete.getId())
            {
                break;
            }
            count++;
        }
        driverList.remove(count);
    }
    public void add(driver toAdd)
    {
        driverList.add(toAdd);
    }

    public ObservableList<driver> getDriverList() {
        return driverList;
    }

    public ObservableList<race> getRaceList() {
        return raceList;
    }

    public ObservableList<driver> getDeletedList() {
        return deletedList;
    }
    public  StringBuilder stringbildWrite(String[] row, Boolean ifnew){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0; i< row.length;i++){
            stringBuilder.append(row[i]);
            if(i != row.length-1){
                stringBuilder.append(",");
            }
            else if (i == row.length-1 && ifnew){stringBuilder.append(System.lineSeparator());}
        }
        return stringBuilder;
    }
    public void saveDelete()
    {
        ArrayList<String[]> update = new ArrayList<>();
        for (driver dr:deletedList){
            ArrayList<String> positions = new ArrayList<>();
            positions=dr.getPositions();
            String[] add =new String[19];
            add[0] =Integer.toString(dr.getId());
            add[1] = dr.getName();
            add[2] =dr.getAge();
            add[3] =dr.getTeam();
            add[4] =dr.getCar();
            add[5] =dr.getPoints();
            for (int i=0;i<positions.size();i++)
            {
                add[6+i]=positions.get(i);
            }
            update.add(add);
        }
        try {
            FileWriter csvfile = new FileWriter("src/deleted.csv");
            PrintWriter write = new PrintWriter(csvfile);
            for (String[] name: update){
                StringBuilder updating = new StringBuilder();
                updating = stringbildWrite(name,false);
                write.println(updating);
            }
            write.close();
        }
        catch (IOException e){}

    }
    public void saveRace()
    {
        ArrayList<String[]> raceUpdates = new ArrayList<>();
        for (race rc:raceList){


            ArrayList<String> addRace = new ArrayList<>();
            addRace.add(rc.getName());
            addRace.add(rc.getWheather());
            addRace.add(rc.getDate().toString());

            ArrayList<Integer> positions = new ArrayList<>();
            positions=rc.getPositions();
            for (int i=0;i<positions.size();i++){
                addRace.add( String.valueOf(positions.get(i)));
            }
            String[] str= new String[addRace.size()];
            for (int i=0;i<addRace.size();i++)
            {
                str[i]=addRace.get(i);
            }
            raceUpdates.add(str);

        }
        try {
            FileWriter csvfile = new FileWriter("src/racedetails1.csv");
            PrintWriter write = new PrintWriter(csvfile);
            for (String[] name: raceUpdates){
                StringBuilder updating = new StringBuilder();
                updating = stringbildWrite(name,false);
                write.println(updating);
            }
            write.close();
        }
        catch (IOException e){}

    }
    public void saveDrivers()
    {

        ArrayList<String[]> update = new ArrayList<>();
        for (driver dr:driverList){
            ArrayList<String> positions = new ArrayList<>();
            positions=dr.getPositions();
            String[] add =new String[19];//was19
            add[0] =Integer.toString(dr.getId());
            add[1] = dr.getName();
            add[2] =dr.getAge();
            add[3] =dr.getTeam();
            add[4] =dr.getCar();
            add[5] =dr.getPoints();
            for (int i=0;i<positions.size();i++)
            {
                add[6+i]=positions.get(i);
            }
            update.add(add);
        }
        try {
            FileWriter csvfile = new FileWriter("src/driverrecords1.csv");
            PrintWriter write = new PrintWriter(csvfile);
            for (String[] name: update){
                StringBuilder updating = new StringBuilder();
                updating = stringbildWrite(name,false);
                write.println(updating);
            }
            write.close();
        }
        catch (IOException e){}

    }
    public void saveTODelete(driver toSave)
    {
        deletedList.add(toSave);
    }
    public void addRace(race race)
    {
        raceList.add(race);
    }
    public void clearDriver()
    {
        driverList.clear();
    }
    public void clearRace()
    {
        raceList.clear();
    }
    public void clearDelete()
    {
        deletedList.clear();
    }
    public void clearDeleted()
    {
        deletedList.clear();
        saveDelete();
    }
}
