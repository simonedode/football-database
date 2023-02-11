package it.unibo.database.model;

import java.util.Date;
import java.util.Objects;

@SuppressWarnings("unused")
public class Coach {
    private final String name;
    private final String surname;
    private final String CF;
    private final Date birth;
    private final String nationality;
    private final int card;
    private final String team;
    private final int teamTrained;
    private final Date dateStart;
    private final int salary;

    public Coach(String name, String surname, String CF, Date birth, String nationality, int card, String team, int teamTrained, Date dateStart, int salary) {
        this.name = Objects.requireNonNull(name);
        this.surname = Objects.requireNonNull(surname);
        this.CF = Objects.requireNonNull(CF);
        this.birth = Objects.requireNonNull(birth);
        this.nationality = Objects.requireNonNull(nationality);
        this.card = card;
        this.team = Objects.requireNonNull(team);
        this.teamTrained = teamTrained;
        this.dateStart = Objects.requireNonNull(dateStart);
        this.salary = salary;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getCF() {
        return this.CF;
    }

    public Date getBirth() {
        return this.birth;
    }

    public String getNationality() {
        return this.nationality;
    }

    public int getCard() {
        return this.card;
    }

    public String getTeam() {
        return this.team;
    }

    public int getTeamTrained() {
        return this.teamTrained;
    }

    public Date getDateStart() {
        return this.dateStart;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", CF='" + CF + '\'' +
                ", birth='" + birth + '\'' +
                ", nationality='" + nationality + '\'' +
                ", card='" + card + '\'' +
                ", team='" + team + '\'' +
                ", team trained=" + teamTrained +
                ", date start=" + dateStart +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return card == coach.card && teamTrained == coach.teamTrained && salary == coach.salary && Objects.equals(name, coach.name) && Objects.equals(surname, coach.surname) && Objects.equals(CF, coach.CF) && Objects.equals(birth, coach.birth) && Objects.equals(nationality, coach.nationality) && Objects.equals(team, coach.team) && Objects.equals(dateStart, coach.dateStart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, CF, birth, nationality, card, team, teamTrained, dateStart, salary);
    }
}
