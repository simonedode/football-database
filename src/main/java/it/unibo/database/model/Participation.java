package it.unibo.database.model;

import java.util.Objects;

public class Participation {
    private final int year;
    private final String teamHome;
    private final int numberDay;
    private final int cardPlayer;
    private final int numberGol;
    private final int minutes;
    private final int shots;

    public Participation(int year, String teamHome, int numberDay, int cardPlayer, int numberGol, int minutes, int shots) {
        this.year = year;
        this.teamHome = teamHome;
        this.numberDay = numberDay;
        this.cardPlayer = cardPlayer;
        this.numberGol = numberGol;
        this.minutes = minutes;
        this.shots = shots;
    }

    public int getYear() {
        return year;
    }

    public String getTeamHome() {
        return teamHome;
    }

    public int getNumberDay() {
        return numberDay;
    }

    public int getCardPlayer() {
        return cardPlayer;
    }

    public int getNumberGol() {
        return numberGol;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getShots() {
        return shots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participation that = (Participation) o;
        return year == that.year && numberDay == that.numberDay && cardPlayer == that.cardPlayer && numberGol == that.numberGol && minutes == that.minutes && shots == that.shots && Objects.equals(teamHome, that.teamHome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, teamHome, numberDay, cardPlayer, numberGol, minutes, shots);
    }
}
