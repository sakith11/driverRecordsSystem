package com.example.cw2_4_5;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class race {
    private final String name;
    private final String wheather;
    private final LocalDate date;
    private final ArrayList<Integer> positions;

    public LocalDate getDate() {
        return date;
    }



    public String getName() {
        return name;
    }

    public String getWheather() {
        return wheather;
    }

    public ArrayList<Integer> getPositions() {
        return positions;
    }

    public race(String name, String wheather, LocalDate date, ArrayList<Integer> positions) {
        this.name = name;
        this.wheather = wheather;
        this.date = date;
        this.positions = positions;

    }
}
