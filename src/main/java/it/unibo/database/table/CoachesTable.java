package it.unibo.database.table;

import it.unibo.database.model.Coach;
import it.unibo.database.utils.Utils;

import java.sql.*;
import java.util.*;
import java.util.Date;

public final class CoachesTable{
    public static final String TABLE_NAME = "allenatori";

    private final Connection connection;

    public CoachesTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    public void createTable() {
        try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                            "CodiceFiscale CHAR(16) NOT NULL, " +
                            "Nome CHAR(40) NOT NULL, " +
                            "Cognome CHAR(40) NOT NULL, " +
                            "Nazionalità CHAR(40) NOT NULL, " +
                            "DataNascita DATE NOT NULL, " +
                            "TesserinoAllenatore INT NOT NULL auto_increment, " +
                            "NomeSquadra CHAR(40) NOT NULL, " +
                            "NumeroSquadreAllenate INT NOT NULL, " +
                            "DataInizio DATE NOT NULL, " +
                            "Stipendio INT NOT NULL, " +
                            "CONSTRAINT IDALLENATORE PRIMARY KEY (TesserinoAllenatore), " +
                            "CONSTRAINT IDALLENATORE_1 UNIQUE (CodiceFiscale), " +
                            "CONSTRAINT FKattualitàAllenatore_ID UNIQUE (NomeSquadra , DataInizio) " +
                            ") AUTO_INCREMENT = 1"
            );
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Coach> readStudentsFromResultSet(final ResultSet resultSet) {
        final List<Coach> coaches = new ArrayList<>();
        try {
            while (resultSet.next()) {
                final String name = resultSet.getString("Nome");
                final String surname = resultSet.getString("Cognome");
                final String CF = resultSet.getString("CodiceFiscale");
                final Date birth = resultSet.getDate("DataNascita");
                final String nationality = resultSet.getString("Nazionalità");
                final int card = resultSet.getInt("TesserinoAllenatore");
                final String team = resultSet.getString("NomeSquadra");
                final int teamTrained = resultSet.getInt("NumeroSquadreAllenate");
                final Date dateStart = resultSet.getDate("DataInizio");
                final int salary = resultSet.getInt("Stipendio");
                final Coach coach = new Coach(name, surname, CF, birth, nationality, card,
                        team, teamTrained, dateStart, salary);
                coaches.add(coach);
            }
        } catch (final SQLException ignored) {
        }
        return coaches;
    }

    public List<Coach> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readStudentsFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void inserts(String name, String surname, String CF, Date birth, String nationality, String team, int numberTeams, Date dateStart, int salary) {
        final String query = "INSERT INTO " + TABLE_NAME + " (Nome, Cognome, CodiceFiscale, DataNascita, Nazionalità, NomeSquadra, NumeroSquadreAllenate, DataInizio, Stipendio) VALUES (?,?,?,?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, CF);
            statement.setDate(4, Utils.dateToSqlDate(birth));
            statement.setString(5, nationality);
            statement.setString(6, team);
            statement.setInt(7, numberTeams);
            statement.setDate(8, Utils.dateToSqlDate(dateStart));
            statement.setInt(9, salary);

           statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void delete(final Integer id) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE TesserinoAllenatore = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
