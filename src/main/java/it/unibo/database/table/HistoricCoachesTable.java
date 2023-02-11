package it.unibo.database.table;

import it.unibo.database.model.HistoricCoach;
import it.unibo.database.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class HistoricCoachesTable {

    public static final String TABLE_NAME = "storicoallenatori";

    private final Connection connection;

    public HistoricCoachesTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    public void createTable() {
        try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                                        "NomeSquadra CHAR(40) NOT NULL, " +
                                        "DataInizio DATE NOT NULL, " +
                                        "TesserinoAllenatore INT NOT NULL, " +
                                        "StipendioAllenatore INT NOT NULL, " +
                                        "DataFine DATE, " +
                                        "CONSTRAINT IDAPPARTENENZA_ID PRIMARY KEY (NomeSquadra , DataInizio) " +
                                        ")" );
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<HistoricCoach> findByPrimaryKey(final String team, final Date dateStart) {
        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE NomeSquadra = ? AND DataInizio = ? ";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, team);
            statement.setDate(2, Utils.dateToSqlDate(dateStart));
            final ResultSet resultSet = statement.executeQuery();
            return readHistoricCoachFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<HistoricCoach> findByCardCoach(final int cardCoach){
        final String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE TesserinoAllenatore = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, cardCoach);
            final ResultSet resultSet = statement.executeQuery();
            return readHistoricCoachFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<HistoricCoach> readHistoricCoachFromResultSet(final ResultSet resultSet) {
        final List<HistoricCoach> historicCoaches = new ArrayList<>();
        try {
            while (resultSet.next()) {
                final int cardCoach = resultSet.getInt("TesserinoAllenatore");
                final String team = resultSet.getString("NomeSquadra");
                final int salary = resultSet.getInt("StipendioAllenatore");
                final Date dateStart = resultSet.getDate("DataInizio");
                final Date dateFinish = resultSet.getDate("DataFine");
                final HistoricCoach historicCoach = new HistoricCoach(cardCoach, dateStart, dateFinish, team, salary);
                historicCoaches.add(historicCoach);
            }
        } catch (final SQLException ignored) {
        }
        return historicCoaches;
    }

    public List<HistoricCoach> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readHistoricCoachFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void inserts(final HistoricCoach historicCoach) {
        final String query = "INSERT INTO " + TABLE_NAME + " (NomeSquadra, DataInizio, DataFine, TesserinoAllenatore, StipendioAllenatore) VALUES (?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, historicCoach.getNameTeam());
            statement.setDate(2, Utils.dateToSqlDate(historicCoach.getDateStart()));
            statement.setDate(3, Utils.dateToSqlDate(historicCoach.getDateFinish()));
            statement.setInt(4, historicCoach.getCardCoach());
            statement.setInt(5, historicCoach.getSalary());

            statement.executeUpdate();
        }  catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void updateSalary(final String team, final Date dateStart, final int salary) {
        final String query =
                "UPDATE " + TABLE_NAME + " SET " +
                        " StipendioAllenatore = ? " +
                "WHERE NomeSquadra = ? AND DataInizio = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, salary);
            statement.setString(2, team);
            statement.setDate(3, Utils.dateToSqlDate(dateStart));

            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
