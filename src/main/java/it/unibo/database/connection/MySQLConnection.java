package it.unibo.database.connection;

import it.unibo.database.table.ConnectionProvider;

public final class MySQLConnection {
    private final static java.sql.Connection CONNECTION = new ConnectionProvider("root", "", "football").getMySQLConnection();

    private MySQLConnection() {
    }

    public static java.sql.Connection getConnection() {
        return CONNECTION;
    }
}
