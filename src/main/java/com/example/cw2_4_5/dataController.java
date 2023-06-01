package com.example.cw2_4_5;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class dataController implements Initializable {
    @FXML
    private ChoiceBox dataChoice;
    String[] racesNames={"Monte Carlo","Sweden","Mexico","Croatia","Portugal","Sardegna","Kenya","Estonia","Finland","Greece","Chile","Europe","Japan"};
    ObservableList<driver> driverList;
    ObservableList<race> raceList;
    ObservableList<driver> deletedList;
    driverData drData;
    DirectoryChooser dirChooser = new DirectoryChooser();
    FileChooser fileChooser = new FileChooser();
    public void onExportDClick(ActionEvent event)
    {

        String name1 = dataChoice.getValue().toString();
        int id = Integer.parseInt(name1.split(" : ")[0]);
        writeHtml(id);





    }

    public void onBackupClick(ActionEvent event)
    {
        //saving the driver detail
        File export = dirChooser.showDialog(new Stage());
        File exportDrivers = new File(export.toPath()+"/driverRecords.csv");
        File exportRace = new File(export+"/raceDetails.csv");
        File exportDelete = new File(export+"/deletedDrivers.csv");
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
            FileWriter csvfile = new FileWriter(exportDrivers);
            PrintWriter write = new PrintWriter(csvfile);
            for (String[] name: update){
                StringBuilder updating = new StringBuilder();
                updating = drData.stringbildWrite(name,false);
                write.println(updating);
            }
            write.close();
        }
        catch (IOException e){}

        //saving the race details

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
            FileWriter csvfile = new FileWriter(exportRace);
            PrintWriter write = new PrintWriter(csvfile);
            for (String[] name: raceUpdates){
                StringBuilder updating = new StringBuilder();
                updating = drData.stringbildWrite(name,false);
                write.println(updating);
            }
            write.close();
        }
        catch (IOException e){}

        //saving deleted drivers
        ArrayList<String[]> update1 = new ArrayList<>();
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
            FileWriter csvfile = new FileWriter(exportDelete);
            PrintWriter write = new PrintWriter(csvfile);
            for (String[] name: update1){
                StringBuilder updating = new StringBuilder();
                updating = drData.stringbildWrite(name,false);
                write.println(updating);
            }
            write.close();
        }
        catch (IOException e){}



    }


    public void OnMenuClick(ActionEvent event)
    {
        paneNavigator.loadPane(paneNavigator.menu);
    }

    public void onMinClick(ActionEvent event)
    {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    public void onCloseClick(ActionEvent event)
    {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    private void writeHtml(int id)
    {
        String name1 = null;
        String age = null;
        String team = null;
        String car = null;
        String points = null;
        ArrayList<String> positions = new ArrayList<>();
        for (driver dr:driverList)
        {
            if (dr.getId()==id)
            {
                name1 = dr.getName();
                age = dr.getAge();;
                team = dr.getTeam();
                car = dr.getCar();
                points = dr.getPoints();
                positions =dr.getPositions();
                break;
            }
        }
        StringBuilder str = new StringBuilder();

        str.append("<table style='border: 1px solid black;'>");
        str.append("<thead>");
        str.append("<tr><th>race</th><th>position</th></tr>");
        str.append("</thead>");
        str.append("<tbody>");
        for (int i=0;i<racesNames.length;i++)
        {
            String row= "<tr><td>"+racesNames[i]+"</td><td>"+positions.get(i)+"</td></tr>";
            str.append(row);
        }
        str.append("</tbody>");
        str.append("</table>");
        String path1 ="driverImages/"+id+".png";
        String path2 ="driverImages/"+id+".jpg";
        String src=null;
        File f1 = new File(path1);
        File f2 = new File(path2);
        if (f1.exists())
        {
            src=path1;
        }
        else if(f2.exists())
        {
            src =path2;
        }
        else
        {
            src="C:/cw2_4/cw2_4_5backwork/driverImages/default.jpg";
        }
        String html="<html>" +
                "<head> <title>"+name1+"</title></head>"+
                "<body>" +"<div>"+
                "<img src ="+src+">"+"</div>"+
                "<div><p>dirver's name    :"+name1+"</p></div>"+
                "<div><p>driver's age     :"+age+"</p></div>"+
                "<div><p>driver's team    :"+team+"</p></div>"+
                "<div><p>driver's car     :"+car+"</p></div>"+
                "<div><p>driver's points  :"+points+"</p></div>"+
                "<div>"+str+"<div>"+
                "</body>"+
                "</html>";


        dirChooser.setInitialDirectory(new File("C:\\"));
        dirChooser.setTitle("choose a folder to save"+id+name1+".html");
        File selected = dirChooser.showDialog(new Stage());
      //  System.out.println(selected.toPath()+"\\"+id+name1+".html");
         File output = new File(selected.toPath()+"\\"+id+name1+".html");
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(output));
            writer.write(html);
            writer.close();
        }
        catch (IOException e){}

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        drData = new driverData();
        ArrayList<String> driverNAmes = new ArrayList<>();


        try {
            drData.loadData();
        }
        catch (IOException e){}
        driverList = drData.getDriverList();
        raceList = drData.getRaceList();
        deletedList = drData.getDeletedList();
        for (driver dr:driverList)
        {
            driverNAmes.add(dr.getId()+" : "+dr.getName());
        }
        dataChoice.getItems().setAll(driverNAmes);


    }

    public void onImportDrClick(ActionEvent event)
    {
        boolean correct =true;
        fileChooser.setTitle(" select driverRecords.csv");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV files","*.csv")
        );
        File csv = fileChooser.showOpenDialog(new Stage());
        ObservableList<driver> drList = FXCollections.observableArrayList();
        BufferedReader reader = null;
        String line ="";
        String[] row = new String[0];
        try {
            reader = new BufferedReader(new FileReader(csv));
            while ((line=reader.readLine())!=null){
                row=line.split(",");
                ArrayList<String> races= new ArrayList<>();
                races.addAll(Arrays.asList(row).subList(6, 19));
                driver dr = new driver(Integer.parseInt(row[0]), row[1],row[2],row[3],row[4],row[5],races);
                //setDriverObject(dr);
                drList.add(dr);
            }
            reader.close();
        }
        catch (IOException e){e.printStackTrace(); correct = false;}
        if (correct==true)
        {
        drData.clearDriver();
        for (driver dr:drList)
        {
            drData.add(dr);
        }
        drData.saveDrivers();
        }
    }

    public void onImportRaClick(ActionEvent event)
    {
        boolean correct = true;
        fileChooser.setTitle(" select racedetails.csv");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV files","*.csv")
        );
        File csv = fileChooser.showOpenDialog(new Stage());
        BufferedReader reader1 = null;
        String line1="";
        String[] row1 = new String[0];
        ObservableList<race> raList = FXCollections.observableArrayList();
        try {
            reader1 = new BufferedReader(new FileReader(csv));
            while ((line1=reader1.readLine())!=null){
                row1=line1.split(",");
                ArrayList<Integer> positions= new ArrayList<>();
                for (int i=3;i<row1.length;i++){
                    positions.add(Integer.parseInt(row1[i]));
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                formatter = formatter.withLocale(Locale.ENGLISH);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
                LocalDate date = LocalDate.parse(row1[2], formatter);
                //LocalDate date = new LocalDate(row1[3]);

                race race = new race(row1[0],row1[1],date, positions);
                raList.add(race);
            }
            reader1.close();
        }
        catch (IOException e){e.printStackTrace(); correct =false;}
        if (correct==true)
        {
            drData.clearRace();
            for (race race:raList)
            {
                drData.addRace(race);
            }
        }
    }

    public void onImportDelClick(ActionEvent event)
    {
        boolean correct = true;
        fileChooser.setTitle(" select racedetails.csv");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV files","*.csv")
        );
        File csv = fileChooser.showOpenDialog(new Stage());
        ObservableList<driver> delList = FXCollections.observableArrayList();
        BufferedReader reader2 = null;
        String line2="";
        String[] row2 = new String[0];
        try {
            reader2 = new BufferedReader(new FileReader(csv));
            while ((line2=reader2.readLine())!=null){
                row2=line2.split(",");
                ArrayList<String> races2= new ArrayList<>();
                races2.addAll(Arrays.asList(row2).subList(6, 19));
                driver dr = new driver(Integer.parseInt(row2[0]), row2[1],row2[2],row2[3],row2[4],row2[5],races2);
                delList.add(dr);
            }
            reader2.close();

        }
        catch (IOException e){ correct=false;}
        if (correct==true)
        {
            drData.clearDelete();
            for (driver dr:delList)
            {
                drData.saveTODelete(dr);
            }
        }

    }

    public void onDelClick(ActionEvent event)
    {
        drData.clearDelete();
    }
}
