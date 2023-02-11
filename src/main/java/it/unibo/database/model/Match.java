package it.unibo.database.model;

import java.util.Objects;

public class Match {

    private final String teamHome;
    private final String teamAway;
    private final int year;
    private final int day;
    private final int golHome;
    private final int golAway;

    public Match(String teamHome, String teamAway, int year, int day, int golHome, int golAway) {
        this.teamHome = Objects.requireNonNull(teamHome);
        this.teamAway = teamAway;
        this.year = year;
        this.day = day;
        this.golHome = golHome;
        this.golAway = golAway;
    }

    public String getTeamHome() {
        return teamHome;
    }

    public String getTeamAway() {
        return teamAway;
    }

    public int getYear() {
        return year;
    }

    public int getDay() {
        return day;
    }

    public int getGolHome() {
        return golHome;
    }

    public int getGolAway() {
        return golAway;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return getYear() == match.getYear() && getDay() == match.getDay() && getGolHome() == match.getGolHome()
                && getGolAway() == match.getGolAway() && Objects.equals(getTeamHome(), match.getTeamHome())
                && Objects.equals(getTeamAway(), match.getTeamAway());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeamHome(), getTeamAway(), getYear(), getDay(), getGolHome(), getGolAway());
    }
}
