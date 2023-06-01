package com.example.cw2_4_5;

public class raceInfo {
    Integer pos;
    String name;
    String team;
    String car;
    Integer points;



    public raceInfo(Integer pos, String name, String team, String car, Integer points) {
        this.pos = pos;
        this.name = name;
        this.team = team;
        this.car = car;
        this.points = points;
    }

    public Integer getPos() {
        return pos;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getCar() {
        return car;
    }

    public Integer getPoints() {
        return points;
    }
}
