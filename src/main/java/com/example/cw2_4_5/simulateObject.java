package com.example.cw2_4_5;

import java.time.LocalDate;

public class simulateObject {
    private final String raceName;
    private final LocalDate date;
    private final String wheather;
    private final String firstName;
    private final String secondName;
    private final String thirdName;

    public String getRaceName() {
        return raceName;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getWheather() {
        return wheather;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public simulateObject(String raceName, LocalDate date, String wheather, String firstName, String secondName, String thirdName) {
        this.raceName = raceName;
        this.date = date;
        this.wheather = wheather;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
    }
}
