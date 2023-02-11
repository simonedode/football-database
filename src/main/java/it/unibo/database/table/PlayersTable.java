package it.unibo.database.table;

import it.unibo.database.model.Player;
import it.unibo.database.utils.Utils;

import java.sql.*;
import java.util.Date;
import java.util.*;

public final class PlayersTable {
    public static final String TABLE_NAME = "giocatori";

    private final Connection connection;

    public PlayersTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

    public void createTable() {
        try (final Statement statement = this.connection.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    "( CodiceFiscale CHAR(16) NOT NULL, " +
                    "Nome CHAR(40) NOT NULL, " +
                    "Cognome CHAR(40) NOT NULL, " +
                    "Nazionalità CHAR(40) NOT NULL, " +
                    "DataNascita DATE NOT NULL, " +
                    "TesserinoGiocatore INT NOT NULL auto_increment, " +
                    "Ruolo CHAR(1) NOT NULL, " +
                    "NumeroPresenze INT NOT NULL, " +
                    "NomeSquadra CHAR(40) NOT NULL, " +
                    "DataInizio DATE NOT NULL, " +
                    "Stipendio INT NOT NULL," +
                    "CONSTRAINT IDGIOCATORE PRIMARY KEY(TesserinoGiocatore), " +
                    "CONSTRAINT IDGIOCATORE_1 UNIQUE(CodiceFiscale) ) AUTO_INCREMENT = 1"
            );
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Player> readStudentsFromResultSet(final ResultSet resultSet) {
        final List<Player> players = new ArrayList<>();
        try {
            while (resultSet.next()) {
                final String name = resultSet.getString("Nome");
                final String surname = resultSet.getString("Cognome");
                final String CF = resultSet.getString("CodiceFiscale");
                final Date birth = resultSet.getDate("DataNascita");
                final String nationality = resultSet.getString("Nazionalità");
                final int card = resultSet.getInt("TesserinoGiocatore");
                final String role = resultSet.getString("Ruolo");
                final int presences = resultSet.getInt("NumeroPresenze");
                final String team = resultSet.getString("NomeSquadra");
                final Date date = resultSet.getDate("DataInizio");
                final int salary = resultSet.getInt("Stipendio");
                final Player player = new Player(name, surname, CF, birth,
                        nationality, card, role, presences, team, date, salary);
                players.add(player);
            }
        } catch (final SQLException ignored) {
        }
        return players;
    }

    public List<Player> findAll() {
        try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readStudentsFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void inserts(final String name, final String surname, final String CF, Date birth, final String nationality,
                        final String role, final int presences, final String team, Date date, int salary) {
        final String query = "INSERT INTO " + TABLE_NAME + "  (Nome, Cognome, CodiceFiscale, DataNascita, Nazionalità, Ruolo, NumeroPresenze, NomeSquadra, DataInizio, Stipendio) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, CF);
            statement.setDate(4, Utils.dateToSqlDate(birth));
            statement.setString(5, nationality);
            statement.setString(6, role);
            statement.setInt(7, presences);
            statement.setString(8, team);
            statement.setDate(9, Utils.dateToSqlDate(date));
            statement.setInt(10, salary);

            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void delete(final Integer id) {
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE TesserinoGiocatore = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
