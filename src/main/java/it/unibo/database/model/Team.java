package it.unibo.database.model;

import java.util.Objects;

public class Team {
    private final String name;
    private final String city;
    private final String colors;

    public Team(String name, String city, String colors) {
        this.name = Objects.requireNonNull(name);
        this.city = Objects.requireNonNull(city);
        this.colors = Objects.requireNonNull(colors);
    }

    public String getName() {
        return this.name;
    }

    public String getCity() {
        return this.city;
    }

    public String getColors() {
        return this.colors;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", colors='" + colors + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(getName(), team.getName()) && Objects.equals(getCity(), team.getCity()) && Objects.equals(getColors(), team.getColors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCity(), getColors());
    }
}
