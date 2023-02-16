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

public class TrophiesTeamsController {
    private TrophyTable trophiesTable;

    @FXML
    private TableView<Trophy> trophyTeamTableView;

    @FXML
    private TextField trophyTeamName;

    @FXML
    private TextField trophyTeamSearch;

    @FXML
    private TextField trophyTeamYear;

    @FXML
    private TextField trophyTeam;

    @FXML
    private TextField trophyTeamSearchYear;

    @FXML
    private TableColumn<Trophy, String> tabTrophyTeamName;

    @FXML
    private TableColumn<Trophy, Integer> tabTrophyTeamYear;

    @FXML
    private TableColumn<Trophy, String> tabTrophyTeam;


    @FXML
    void initialize() {
        Platform.runLater(() -> {
            this.trophiesTable = new TrophyTable(MySQLConnection.getConnection());
            this.trophiesTable.createTable();
            initializeTables();
            showTrophiesTeam(this.trophiesTable.findAll().stream().filter(t -> t.getCardPlayer() < 0).collect(Collectors.toList()));
        });
    }

    @FXML
    void searchTrophyTeams() {
        try {
            final int year = Integer.parseInt(this.trophyTeamSearchYear.getText());
            final String team = this.trophyTeamSearch.getText();
            showTrophiesTeam(this.trophiesTable.findTrophiesTeams(team, year));
            Utils.hideTextField(trophyTeamSearch, trophyTeamSearchYear);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @FXML
    public void addTrophyTeam() {
        try {
            final String name = this.trophyTeamName.getText();
            final int year = Integer.parseInt(this.trophyTeamYear.getText());
            final String team = this.trophyTeam.getText();
            this.trophiesTable.inserts(new Trophy(name, year, team, -1));
            showTrophiesTeam(this.trophiesTable.findAll().stream().filter(t -> t.getCardPlayer() < 0).collect(Collectors.toList()));
            Utils.hideTextField(trophyTeamName, trophyTeamYear, trophyTeam);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void showTrophiesTeam(List<Trophy> trophies) {
        ObservableList<Trophy> list = FXCollections.observableArrayList(trophies);
        this.trophyTeamTableView.setItems(list);
    }

    private void initializeTables(){
        tabTrophyTeamName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabTrophyTeamYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        tabTrophyTeam.setCellValueFactory(new PropertyValueFactory<>("nameTeam"));
    }
}
