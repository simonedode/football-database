package it.unibo.database;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.LogManager;
import java.io.IOException;

public class ApplicationDB extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationDB.class.getResource("database.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Campionato di Calcio");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        LogManager.getLogManager().reset();
        launch();
    }
}
