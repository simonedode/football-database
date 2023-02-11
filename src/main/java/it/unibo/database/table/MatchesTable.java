package it.unibo.database.table;

import it.unibo.database.model.Match;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class MatchesTable {
    public static final String TABLE_NAME = "partite";

    private final Connection connection;

    public MatchesTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    public void createTable() {
        try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                                        "NomeSquadraCasa CHAR(40) NOT NULL, " +
                                        "NumeroGiornata INT NOT NULL, " +
                                        "AnnoCampionato INT NOT NULL, " +
                                        "NomeSquadraTrasferta CHAR(40) NOT NULL, " +
                                        "GolCasa INT NOT NULL, " +
                                        "GolTrasferta INT NOT NULL, " +
                                        "CONSTRAINT IDPARTITA_ID PRIMARY KEY (AnnoCampionato , NomeSquadraCasa , NumeroGiornata), " +
                                        "CONSTRAINT IDPARTITA_1 UNIQUE (AnnoCampionato , NomeSquadraTrasferta , NumeroGiornata), " +
                                        "CONSTRAINT IDPARTITA_2 UNIQUE (NomeSquadraCasa , AnnoCampionato , NomeSquadraTrasferta) )" );
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Match> findMatch(int year, String teamHome, String teamAway) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE NomeSquadraCasa = ? AND NomeSquadraTrasferta = ? AND AnnoCampionato = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, teamHome);
            statement.setString(2, teamAway);
            statement.setInt(3, year);
            final ResultSet resultSet = statement.executeQuery();
            return readMatchesFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Optional<Match> findMatchMoreGoals(final int year) {
        final String query = "SELECT NomeSquadraCasa, NumeroGiornata, AnnoCampionato, NomeSquadraTrasferta, (GolCasa + GolTrasferta) AS GolTotali " +
                                "FROM " + TABLE_NAME + " " +
                                "WHERE AnnoCampionato = ? " +
                                "ORDER BY GolTotali DESC";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, year);
            final ResultSet resultSet = statement.executeQuery();
            final List<Match> matches = new ArrayList<>();
            while (resultSet.next()) {
                final String teamHome = resultSet.getString("NomeSquadraCasa");
                final String teamAway = resultSet.getString("NomeSquadraTrasferta");
                final int yearMatch = resultSet.getInt("AnnoCampionato");
                final int day = resultSet.getInt("NumeroGiornata");
                final int golHome = resultSet.getInt("GolTotali");
                final Match match = new Match(teamHome, teamAway, yearMatch, day, golHome, 0);
                matches.add(match);
            }
            return matches.stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<Match> readMatchesFromResultSet(final ResultSet resultSet) {
        final List<Match> matches = new ArrayList<>();
        try {
            while (resultSet.next()) {
                final String teamHome = resultSet.getString("NomeSquadraCasa");
                final String teamAway = resultSet.getString("NomeSquadraTrasferta");
                final int year = resultSet.getInt("AnnoCampionato");
                final int day = resultSet.getInt("NumeroGiornata");
                final int golHome = resultSet.getInt("GolCasa");
                final int golAway = resultSet.getInt("GolTrasferta");

                final Match match = new Match(teamHome, teamAway, year, day, golHome, golAway);
                matches.add(match);
            }
        } catch (final SQLException ignored) {
        }
        return matches;
    }

    public List<Match> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readMatchesFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void inserts(final Match match) {
        final String query = "INSERT INTO " + TABLE_NAME + " (NomeSquadraCasa, NomeSquadraTrasferta, AnnoCampionato, NumeroGiornata, GolCasa, GolTrasferta) VALUES (?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, match.getTeamHome());
            statement.setString(2, match.getTeamAway());
            statement.setInt(3, match.getYear());
            statement.setInt(4, match.getDay());
            statement.setInt(5, match.getGolHome());
            statement.setInt(6, match.getGolAway());
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void update(final Match match) {
        final String query =
                "UPDATE " + TABLE_NAME + " SET " +
                        "GolCasa = ?, " +
                        "GolTrasferta = ? " +
                "WHERE NomeSquadraCasa = ? AND AnnoCampionato = ? AND NumeroGiornata = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, match.getGolHome());
            statement.setInt(2, match.getGolAway());
            statement.setString(3, match.getTeamHome());
            statement.setInt(4, match.getYear());
            statement.setInt(5, match.getDay());
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

}
