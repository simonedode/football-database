package it.unibo.database.model;

import java.util.Date;
import java.util.Objects;

public class HistoricCoach {

    private final int cardCoach;
    private final Date dateStart;
    private final Date dateFinish;
    private final String nameTeam;
    private final int salary;

    public HistoricCoach(int cardCoach, Date dateStart, Date dateFinish, String nameTeam, int salary) {
        this.cardCoach = cardCoach;
        this.dateStart = Objects.requireNonNull(dateStart);
        this.dateFinish = Objects.requireNonNull(dateFinish);
        this.nameTeam = Objects.requireNonNull(nameTeam);
        this.salary = salary;
    }

    public int getCardCoach() {
        return cardCoach;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricCoach that = (HistoricCoach) o;
        return getCardCoach() == that.getCardCoach() && getSalary() == that.getSalary() && Objects.equals(getDateStart(), that.getDateStart()) && Objects.equals(getDateFinish(), that.getDateFinish()) && Objects.equals(getNameTeam(), that.getNameTeam());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCardCoach(), getDateStart(), getDateFinish(), getNameTeam(), getSalary());
    }
}
