module it.unibo.database {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens it.unibo.database to javafx.fxml;
    opens it.unibo.database.model to javafx.base;
    exports it.unibo.database;
    exports it.unibo.database.controller;
    opens it.unibo.database.controller to javafx.fxml;
}