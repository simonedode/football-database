package it.unibo.database.connection;

import it.unibo.database.table.ConnectionProvider;

import java.io.FileInputStream;
import java.util.Properties;

public final class MySQLConnection {
    private static final java.sql.Connection CONNECTION = new ConnectionProvider(
            properties().getProperty("DATABASE_USERNAME"),
            properties().getProperty("DATABASE_PASSWORD"),
            properties().getProperty("DATABASE_TABLE")
    ).getMySQLConnection();

    private MySQLConnection() {
    }

    public static java.sql.Connection getConnection() {
        return CONNECTION;
    }

    private static Properties properties() {
        Properties properties = new Properties();
        String fileName = "app.config";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            properties.load(fis);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return properties;
    }
}
