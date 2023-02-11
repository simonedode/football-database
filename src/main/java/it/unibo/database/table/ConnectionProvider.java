package it.unibo.database.table;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public final class ConnectionProvider {
    private final String username;
    private final String password;
    private final String dbName;

    public ConnectionProvider (final String username, final String password, final String dbName) {
        this.username = username;
        this.password = password;
        this.dbName = dbName;
    }

    public Connection getMySQLConnection() {
        final String dbUri = "jdbc:mysql://localhost:3306/" + this.dbName;
        try {
            return DriverManager.getConnection(dbUri, this.username, this.password);
        } catch (final SQLException e) {
            throw new IllegalStateException("Could not establish a connection with db", e);
        }
    }
}
