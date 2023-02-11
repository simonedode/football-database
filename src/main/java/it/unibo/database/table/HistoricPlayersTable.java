package it.unibo.database.table;

import it.unibo.database.model.HistoricPlayer;
import it.unibo.database.utils.Utils;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class HistoricPlayersTable {

    public static final String TABLE_NAME = "storicogiocatori";

    private final Connection connection;

    public HistoricPlayersTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    public void createTable() {
        try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "( " +
                                        "NomeSquadra CHAR(40) NOT NULL, " +
                                        "DataInizio DATE NOT NULL, " +
                                        "TesserinoGiocatore INT NOT NULL, " +
                                        "NumeroMaglia INT NOT NULL, " +
                                        "DataFine DATE, " +
                                        "Stipendio INT NOT NULL, " +
                                        "CONSTRAINT IDstoricogiocatori PRIMARY KEY (NomeSquadra , DataInizio , TesserinoGiocatore) )" );
        } catch (final SQLException ignored) {
        }
    }

    public List<HistoricPlayer> findByCard(final int card){
        final String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE TesserinoGiocatore = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, card);
            final ResultSet resultSet = statement.executeQuery();
            return readHistoricPlayerFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<HistoricPlayer> readHistoricPlayerFromResultSet(final ResultSet resultSet) {
        final List<HistoricPlayer> historicPlayers = new ArrayList<>();
        try {
            while (resultSet.next()) {
                final String team = resultSet.getString("NomeSquadra");
                final Date dateStart = resultSet.getDate("DataInizio");
                final int cardPlayer = resultSet.getInt("TesserinoGiocatore");
                final Date dateFinish = resultSet.getDate("DataFine");
                final int salary = resultSet.getInt("Stipendio");
                final int numberShirt = resultSet.getInt("NumeroMaglia");
                final HistoricPlayer historicPlayer = new HistoricPlayer(team, dateStart, cardPlayer, numberShirt, dateFinish, salary);
                historicPlayers.add(historicPlayer);
            }
        } catch (final SQLException ignored) {
        }
        return historicPlayers;
    }

    public List<HistoricPlayer> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readHistoricPlayerFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void inserts(final HistoricPlayer historicPlayer) {
        final String query = "INSERT INTO " + TABLE_NAME + " (NomeSquadra, DataInizio, TesserinoGiocatore, Stipendio, DataFine, NumeroMaglia) VALUES (?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, historicPlayer.getNameTeam());
            statement.setDate(2, Utils.dateToSqlDate(historicPlayer.getDateStart()));
            statement.setInt(3, historicPlayer.getCardPlayer());
            statement.setInt(4, historicPlayer.getSalary());
            statement.setDate(5, Utils.dateToSqlDate(historicPlayer.getDateFinish()));
            statement.setInt(6, historicPlayer.getNumberShirt());

            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void updateNumberShirt(final String team, final Date year, final int card, final int number) {
        final String query =
                "UPDATE " + TABLE_NAME + " SET" +
                        " NumeroMaglia = ? " +
                "WHERE NomeSquadra = ? AND DataInizio = ? AND TesserinoGiocatore = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, number);
            statement.setString(2, team);
            statement.setDate(3, Utils.dateToSqlDate(year));
            statement.setInt(4, card);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
