package it.unibo.database.model;

import java.util.Date;
import java.util.Objects;

@SuppressWarnings("unused")
public class Player {
    private final String name;
    private final String surname;
    private final String CF;
    private final Date birth;
    private final String nationality;
    private int card;
    private final String role;
    private final int presences;
    private final String team;
    private final Date date;
    private final int salary;

    public Player(String name, String surname, String CF, Date birth, String nationality, int card, String role, int presences, String team, Date date, int salary) {
        this.name = Objects.requireNonNull(name);
        this.surname = Objects.requireNonNull(surname);
        this.CF = Objects.requireNonNull(CF);
        this.birth = Objects.requireNonNull(birth);
        this.nationality = Objects.requireNonNull(nationality);
        this.card = card;
        this.role = Objects.requireNonNull(role);
        this.presences = presences;
        this.team = Objects.requireNonNull(team);
        this.date = Objects.requireNonNull(date);
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
    public void setCard(int card) {
        this.card = card;
    }

    public String getRole() {
        return this.role;
    }

    public int getPresences() {
        return this.presences;
    }

    public String getTeam() {
        return this.team;
    }

    public Date getDate() {
        return this.date;
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
                ", role='" + role + '\'' +
                ", presences=" + presences +
                ", team='" + team + '\'' +
                ", date start=" + date +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return card == player.card && presences == player.presences && salary == player.salary && Objects.equals(name, player.name) && Objects.equals(surname, player.surname) && Objects.equals(CF, player.CF) && Objects.equals(birth, player.birth) && Objects.equals(nationality, player.nationality) && Objects.equals(role, player.role) && Objects.equals(team, player.team) && Objects.equals(date, player.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, CF, birth, nationality, card, role, presences, team, date, salary);
    }
}
