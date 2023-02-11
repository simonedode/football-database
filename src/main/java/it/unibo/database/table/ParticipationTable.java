package it.unibo.database.table;


import it.unibo.database.model.Participation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ParticipationTable {
    public static final String TABLE_NAME = "partecipazioni";

    private final Connection connection;

    public ParticipationTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    public void createTable() {
        try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                                        "AnnoCampionato INT NOT NULL, " +
                                        "NomeSquadraCasa CHAR(40) NOT NULL, " +
                                        "NumeroGiornata INT NOT NULL, " +
                                        "TesserinoGiocatore INT NOT NULL, " +
                                        "NumeroGol INT NOT NULL, " +
                                        "Minutaggio INT NOT NULL, " +
                                        "TiriInPorta INT NOT NULL, " +
                                        "CONSTRAINT IDPARTECIPAZIONE PRIMARY KEY (AnnoCampionato , NomeSquadraCasa , NumeroGiornata , TesserinoGiocatore) " +
                    ")" );
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Participation> findStatsMatch(final String teamHome, final int year, final int day) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE NomeSquadraCasa = ? AND AnnoCampionato = ? AND NumeroGiornata = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, teamHome);
            statement.setInt(2, year);
            statement.setInt(3, day);
            final ResultSet resultSet = statement.executeQuery();
            return readParticipationFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Participation> findStatsPlayerInMatches(final int year){
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE AnnoCampionato = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, year);
            final ResultSet resultSet = statement.executeQuery();
            return readParticipationFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<Participation> readParticipationFromResultSet(final ResultSet resultSet) {
        final List<Participation> participations = new ArrayList<>();
        try {
            while (resultSet.next()) {
                final String teamHome = resultSet.getString("NomeSquadraCasa");
                final int year = resultSet.getInt("AnnoCampionato");
                final int numberDay = resultSet.getInt("NumeroGiornata");
                final int cardPlayer = resultSet.getInt("TesserinoGiocatore");
                final int goals = resultSet.getInt("NumeroGol");
                final int minutes = resultSet.getInt("Minutaggio");
                final int shots = resultSet.getInt("TiriInPorta");

                final Participation participation = new Participation(year, teamHome, numberDay, cardPlayer, goals, minutes, shots);
                participations.add(participation);
            }
        } catch (final SQLException ignored) {
        }
        return participations;
    }

    public List<Participation> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readParticipationFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void updateGol(final String teamHome, final int year, final int day, final int card, final int goal) {
        String query = "UPDATE " + TABLE_NAME + " SET " +
                        "NumeroGol = ? " +
                        "WHERE NomeSquadraCasa = ? AND AnnoCampionato = ? AND NumeroGiornata = ? AND TesserinoGiocatore = ?";
        executeUpdate(teamHome, year, day, card, goal, query);
    }

    public void updateMinutes(final String teamHome, final int year, final int day, final int card, final int minutes) {
        String query = "UPDATE " + TABLE_NAME + " SET " +
                "Minutaggio = ? " +
                "WHERE NomeSquadraCasa = ? AND AnnoCampionato = ? AND NumeroGiornata = ? AND TesserinoGiocatore = ?";
        executeUpdate(teamHome, year, day, card, minutes, query);
    }

    public void updateShots(final String teamHome, final int year, final int day, final int card, final int shots) {
        String query = "UPDATE " + TABLE_NAME + " SET " +
                "TiriInPorta = ? " +
                "WHERE NomeSquadraCasa = ? AND AnnoCampionato = ? AND NumeroGiornata = ? AND TesserinoGiocatore = ?";
        executeUpdate(teamHome, year, day, card, shots, query);
    }
    private void executeUpdate(String teamHome, int year, int day, int card, int stat, String query) {
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, stat);
            statement.setString(2, teamHome);
            statement.setInt(3, year);
            statement.setInt(4, day);
            statement.setInt(5, card);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
