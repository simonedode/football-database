package it.unibo.database.controller;

import it.unibo.database.connection.MySQLConnection;
import it.unibo.database.model.Trophy;
import it.unibo.database.table.TrophyTable;
import it.unibo.database.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class TrophiesPlayersController {
    private TrophyTable trophiesTable;

    @FXML
    private TableView<Trophy> trophyPlayerTableView;

    @FXML
    private TableColumn<Trophy, String> tabTrophyName;

    @FXML
    private TableColumn<Trophy, Integer> tabTrophyPlayer;
    @FXML
    private TableColumn<Trophy, Integer> tabTrophyYear;

    @FXML
    private TextField trophyName;

    @FXML
    private TextField trophyPlayer;

    @FXML
    private TextField trophySearchCardPlayer;

    @FXML
    private TextField trophyYear;

    @FXML
    void initialize() {
        Platform.runLater(() -> {
            this.trophiesTable = new TrophyTable(MySQLConnection.getConnection());
            this.trophiesTable.createTable();
            initializeTables();
            showTrophiesPlayer(this.trophiesTable.findAll().stream().filter(t -> t.getCardPlayer() > 0).collect(Collectors.toList()));
        });
    }

    @FXML
    void addTrophy() {
        try {
            final String name = this.trophyName.getText();
            final int year = Integer.parseInt(this.trophyYear.getText());
            final int player = Integer.parseInt(this.trophyPlayer.getText());
            this.trophiesTable.inserts(new Trophy(name, year, null, player));
            showTrophiesPlayer(this.trophiesTable.findAll().stream().filter(t -> t.getCardPlayer() > 0).collect(Collectors.toList()));
            Utils.hideTextField(trophyName, trophyYear, trophyPlayer);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void searchTrophyPlayer() {
        try {
            final int card = Integer.parseInt(this.trophySearchCardPlayer.getText());
            showTrophiesPlayer(this.trophiesTable.findTrophiesByCard(card));
            Utils.hideTextField(trophySearchCardPlayer);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void showTrophiesPlayer(final List<Trophy> trophies) {
        ObservableList<Trophy> list = FXCollections.observableArrayList(trophies);
        this.trophyPlayerTableView.setItems(list);
    }

    private void initializeTables() {
        tabTrophyName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabTrophyYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        tabTrophyPlayer.setCellValueFactory(new PropertyValueFactory<>("cardPlayer"));
    }
}
