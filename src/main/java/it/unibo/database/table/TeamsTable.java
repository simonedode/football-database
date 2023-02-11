package it.unibo.database.table;

import it.unibo.database.model.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class TeamsTable{
    public static final String TABLE_NAME = "squadre";

    private final Connection connection;

    public TeamsTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    public void createTable() {
        try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                                        "Nome CHAR(40) NOT NULL, " +
                                        "Città CHAR(40) NOT NULL, " +
                                        "Colori CHAR(40) NOT NULL, " +
                                        "CONSTRAINT IDSQUADRA_ID PRIMARY KEY (Nome) " +
                                        ")"
            );
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Team> readTeamsFromResultSet(final ResultSet resultSet) {
        final List<Team> teams = new ArrayList<>();
        try {
            while (resultSet.next()) {
                final String name = resultSet.getString("Nome");
                final String city = resultSet.getString("Città");
                final String colors = resultSet.getString("Colori");
                final Team team = new Team(name, city, colors);
                teams.add(team);
            }
        } catch (final SQLException ignored) {
        }
        return teams;
    }

    public List<Team> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readTeamsFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void inserts(final Team team) {
        final String query = "INSERT INTO " + TABLE_NAME + " (Nome, Città, Colori) VALUES (?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, team.getName());
            statement.setString(2, team.getCity());
            statement.setString(3, team.getColors());
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void delete(final String id) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE Nome = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Team> findNumberInRole(final String nameTeam, final String role) {
        final String query = "SELECT s.Nome, s.Città, COUNT(*) AS NumeroNelRuolo " +
                                "FROM squadre AS s, giocatori AS g " +
                                "WHERE " +
                                " s.Nome = ? " +
                                "     AND g.NomeSquadra = s.Nome " +
                                "     AND g.Ruolo = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, nameTeam);
            statement.setString(2, role);
            final ResultSet resultSet = statement.executeQuery();
            final List<Team> teams = new ArrayList<>();
            while (resultSet.next()) {
                final String name = resultSet.getString("Nome");
                final String city = resultSet.getString("Città");
                final String colors = resultSet.getString("NumeroNelRuolo");
                final Team team = new Team(name, city, colors);
                teams.add(team);
            }
            return teams;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
