package it.unibo.database.model;

import java.util.Objects;

public class Trophy {
    private final String name;
    private final int year;
    private final String nameTeam;
    private final int cardPlayer;

    public Trophy(String name, int year, String nameTeam, int cardPlayer) {
        this.name = Objects.requireNonNull(name);
        this.year = year;
        this.nameTeam = nameTeam;
        this.cardPlayer = cardPlayer;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public String getNameTeam() {
        return nameTeam;
    }

    public int getCardPlayer() {
        return cardPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trophy trophy = (Trophy) o;
        return year == trophy.year && Objects.equals(name, trophy.name) && Objects.equals(nameTeam, trophy.nameTeam) && Objects.equals(cardPlayer, trophy.cardPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, nameTeam, cardPlayer);
    }
}
