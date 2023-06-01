package com.example.cw2_4_5;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class driver implements Serializable {


    private final int id;
    private final String name;
    private final String age;
    private final String team;
    private final String car;
    private final String points;
    private final ArrayList<String> positions;




    public driver(int id, String name, String age, String team, String car, String points, ArrayList positions) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.team = team;
        this.car = car;
        this.points = points;
        this.positions = positions;
    }



    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getTeam() {
        return team;
    }

    public String getCar() {
        return car;
    }

    public String getPoints() {
        return points;
    }
    public ArrayList<String> getPositions() {return positions;}
    public int getId() {
        return id;
    }



    @Override
    public String toString(){
        return "driver{"+"id "+String.valueOf(id)+" name "+name+" age "+ age+" team " +team+" car "+car+" points "+points+""+positions+" }";
    }

}
