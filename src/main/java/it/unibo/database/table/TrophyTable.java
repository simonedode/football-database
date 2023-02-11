package it.unibo.database.table;

import it.unibo.database.model.Trophy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TrophyTable {
    public static final String TABLE_NAME = "trofei";

    private final Connection connection;

    public TrophyTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    public void createTable() {
        try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                                        "AnnoCampionato INT NOT NULL, " +
                                        "Nome CHAR(40) NOT NULL, " +
                                        "NomeSquadra CHAR(40), " +
                                        "TesserinoGiocatore INT, " +
                                        "CONSTRAINT IDTROFEO PRIMARY KEY (AnnoCampionato , Nome) )" );
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Trophy> readTrophiesFromResultSet(final ResultSet resultSet) {
        final List<Trophy> trophies = new ArrayList<>();
        try {
            while (resultSet.next()) {
                final String name = resultSet.getString("Nome");
                final int year = resultSet.getInt("AnnoCampionato");
                final String nameTeam = resultSet.getString("NomeSquadra");
                final int cardPlayer = resultSet.getInt("TesserinoGiocatore");
                final Trophy trophy = new Trophy(name, year, nameTeam, cardPlayer);
                trophies.add(trophy);
            }
        } catch (final SQLException ignored) {
        }
        return trophies;
    }

    public List<Trophy> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readTrophiesFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void inserts(final Trophy trophy) {
        final String query = "INSERT INTO " + TABLE_NAME + " (Nome, AnnoCampionato, NomeSquadra, TesserinoGiocatore) VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, trophy.getName());
            statement.setInt(2, trophy.getYear());
            statement.setString(3, trophy.getNameTeam());
            statement.setInt(4, trophy.getCardPlayer());
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Trophy> findTrophiesByCard(final int card) {
        final String query = "SELECT Nome, AnnoCampionato, TesserinoGiocatore as Giocatore " +
                                " FROM " + TABLE_NAME +
                                " WHERE  TesserinoGiocatore = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, card);
            final ResultSet resultSet = statement.executeQuery();
            final List<Trophy> trophies = new ArrayList<>();
            while (resultSet.next()) {
                final String name = resultSet.getString("Nome");
                final int year = resultSet.getInt("AnnoCampionato");
                final int cardPlayer = resultSet.getInt("Giocatore");
                final Trophy trophy = new Trophy(name, year, null, cardPlayer);
                trophies.add(trophy);
            }
            return trophies;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Trophy> findTrophiesTeams(final String team, final int year) {
        final String query = "SELECT Nome, AnnoCampionato, NomeSquadra " +
                " FROM " + TABLE_NAME +
                " WHERE AnnoCampionato = ? " +
                "   AND NomeSquadra = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, year);
            statement.setString(2, team);
            final ResultSet resultSet = statement.executeQuery();
            final List<Trophy> trophies = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    final String name = resultSet.getString("Nome");
                    final int yearChampionship = resultSet.getInt("AnnoCampionato");
                    final String nameTeam = resultSet.getString("NomeSquadra");
                    final Trophy trophy = new Trophy(name, yearChampionship, nameTeam, -1);
                    trophies.add(trophy);
                }
            } catch (final SQLException ignored) {
            }
            return trophies;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
