package com.example.cw2_4_5;

public class driverRace {
    private  Integer pos;
    private final String name;
    private final String team;
    private final String car;
    private final String monteCarlo;
    private final String sweden;
    private final String mexico;
    private final String croatia;
    private final String portugal;
    private final String sardegna;
    private final String kenya;
    private final String estonia;
    private final String finland;
    private final String greece;
    private final String chile;
    private final String europe;
    private final String japan;
    private final String total;



    public driverRace(String name, String team, String car, String monteCarlo,
                      String sweden, String mexico, String croatia, String portugal, String sardegna, String kenya,
                      String estonia,
                      String finland, String greece, String chile, String europe, String japan, String total, Integer pos)
    {
        this.name = name;
        this.team = team;
        this.car = car;
        this.monteCarlo = monteCarlo;
        this.sweden = sweden;
        this.mexico = mexico;
        this.croatia = croatia;
        this.portugal = portugal;
        this.sardegna = sardegna;
        this.kenya = kenya;
        this.estonia = estonia;
        this.finland = finland;
        this.greece = greece;
        this.chile = chile;
        this.europe = europe;
        this.japan = japan;
        this.total = total;
        this.pos = pos;
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

    public String getMonteCarlo() {
        return monteCarlo;
    }

    public String getSweden() {
        return sweden;
    }

    public String getMexico() {
        return mexico;
    }

    public String getCroatia() {
        return croatia;
    }

    public String getPortugal() {
        return portugal;
    }

    public String getSardegna() {
        return sardegna;
    }

    public String getKenya() {
        return kenya;
    }

    public String getEstonia() {
        return estonia;
    }

    public String getFinland() {
        return finland;
    }

    public String getGreece() {
        return greece;
    }

    public String getChile() {
        return chile;
    }

    public String getEurope() {
        return europe;
    }

    public String getJapan() {
        return japan;
    }

    public String getTotal() {
        return total;
    }
    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }
}
