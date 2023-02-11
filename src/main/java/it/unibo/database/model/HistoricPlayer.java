package it.unibo.database.model;

import java.util.Date;
import java.util.Objects;

public class HistoricPlayer {

    private final String nameTeam;
    private final Date dateStart;
    private final int cardPlayer;
    private final int numberShirt;
    private final Date dateFinish;
    private final int salary;

    public HistoricPlayer(String nameTeam, Date dateStart, int cardPlayer, int numberShirt, Date dateFinish, int salary) {
        this.cardPlayer = cardPlayer;
        this.dateStart = Objects.requireNonNull(dateStart);
        this.dateFinish = Objects.requireNonNull(dateFinish);
        this.nameTeam = Objects.requireNonNull(nameTeam);
        this.salary = salary;
        this.numberShirt = numberShirt;
    }

    public int getCardPlayer() {
        return cardPlayer;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateFinish() {
        return dateFinish;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public int getSalary() {
        return salary;
    }

    public int getNumberShirt() {
        return numberShirt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricPlayer that = (HistoricPlayer) o;
        return getCardPlayer() == that.getCardPlayer() && getNumberShirt() == that.getNumberShirt() && getSalary() == that.getSalary() && Objects.equals(getNameTeam(), that.getNameTeam()) && Objects.equals(getDateStart(), that.getDateStart()) && Objects.equals(getDateFinish(), that.getDateFinish());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNameTeam(), getDateStart(), getCardPlayer(), getNumberShirt(), getDateFinish(), getSalary());
    }
}
