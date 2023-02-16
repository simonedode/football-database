package it.unibo.database.controller;

import it.unibo.database.connection.MySQLConnection;
import it.unibo.database.model.Match;
import it.unibo.database.table.MatchesTable;
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

public class MatchesController {
    private MatchesTable matchesTable;

    @FXML
    private TableView<Match> matchTableView;

    @FXML
    private TextField matchChampionship;

    @FXML
    private TextField matchGolAway;

    @FXML
    private TextField matchGolHome;

    @FXML
    private TextField matchGolsYear;

    @FXML
    private TextField matchModifyGolAway;

    @FXML
    private TextField matchModifyGolHome;

    @FXML
    private TextField matchModifyNumberDay;

    @FXML
    private TextField matchModifyTeamHome;

    @FXML
    private TextField matchModifyYear;

    @FXML
    private TextField matchNumberDay;

    @FXML
    private TextField matchTeamAway;

    @FXML
    private TextField matchTeamHome;

    @FXML
    private TableColumn<Match, Integer> tabMatchChampionship;

    @FXML
    private TableColumn<Match, Integer> tabMatchGolAway;

    @FXML
    private TableColumn<Match, Integer> tabMatchGolHome;

    @FXML
    private TableColumn<Match, Integer> tabMatchNumberDay;

    @FXML
    private TableColumn<Match, String> tabMatchTeamAway;

    @FXML
    private TableColumn<Match, String> tabMatchTeamHome;

    @FXML
    void initialize() {
        Platform.runLater(() -> {
            this.matchesTable = new MatchesTable(MySQLConnection.getConnection());
            this.matchesTable.createTable();
            initializeTables();
            showMatches();
        });
    }

    @FXML
    void addMatch() {
        try {
            final String teamHome = this.matchTeamHome.getText();
            final String teamAway = this.matchTeamAway.getText();
            final int year = Integer.parseInt(this.matchChampionship.getText());
            final int day = Integer.parseInt(this.matchNumberDay.getText());
            final int golHome = Integer.parseInt(this.matchGolHome.getText());
            final int golAway = Integer.parseInt(this.matchGolAway.getText());
            this.matchesTable.inserts(new Match(teamHome, teamAway, year, day, golHome, golAway));
            showMatches();
            Utils.hideTextField(matchTeamHome, matchTeamAway, matchChampionship, matchNumberDay, matchGolHome, matchGolAway);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void modifyResultMatch() {
        try {
            final String teamHome = this.matchModifyTeamHome.getText();
            final int year = Integer.parseInt(this.matchModifyYear.getText());
            final int day = Integer.parseInt(this.matchModifyNumberDay.getText());
            final int golHome = Integer.parseInt(this.matchModifyGolHome.getText());
            final int golAway = Integer.parseInt(this.matchModifyGolAway.getText());
            this.matchesTable.update(new Match(teamHome, null, year, day, golHome, golAway));
            showMatches();
            Utils.hideTextField(matchModifyTeamHome, matchModifyYear, matchModifyGolAway, matchModifyGolHome, matchModifyNumberDay);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void searchMatchMoreGoals() {
        try {
            final int year = Integer.parseInt(this.matchGolsYear.getText());
            var match = this.matchesTable.findMatchMoreGoals(year);
            if (match.isPresent()){
                ObservableList<Match> list = FXCollections.observableArrayList(match.get());
                this.matchTableView.setItems(list);
                this.tabMatchGolAway.setVisible(false);
                this.tabMatchGolHome.setText("GolTotali");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void showMatch() {
        try {
            showMatches();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void showMatches() {
        List<Match> matches = this.matchesTable.findAll();
        ObservableList<Match> list = FXCollections.observableArrayList(matches);
        this.matchTableView.setItems(list);
        this.tabMatchGolAway.setVisible(true);
        this.tabMatchGolHome.setText("GolCasa");
    }

    private void initializeTables() {
        tabMatchTeamHome.setCellValueFactory(new PropertyValueFactory<>("teamHome"));
        tabMatchTeamAway.setCellValueFactory(new PropertyValueFactory<>("teamAway"));
        tabMatchChampionship.setCellValueFactory(new PropertyValueFactory<>("year"));
        tabMatchNumberDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        tabMatchGolHome.setCellValueFactory(new PropertyValueFactory<>("golHome"));
        tabMatchGolAway.setCellValueFactory(new PropertyValueFactory<>("golAway"));

    }
}
