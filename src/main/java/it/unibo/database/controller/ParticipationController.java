package it.unibo.database.controller;

import it.unibo.database.connection.MySQLConnection;
import it.unibo.database.model.Match;
import it.unibo.database.model.Participation;
import it.unibo.database.table.MatchesTable;
import it.unibo.database.table.ParticipationTable;
import it.unibo.database.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class ParticipationController {
    private ParticipationTable participationTable;

    private MatchesTable matchesTable;

    @FXML
    private TableView<Participation> statsTableView;

    @FXML
    private RadioButton statsModifyGol;

    @FXML
    private RadioButton statsModifyMinutes;

    @FXML
    private TextField statsModifyNumber;

    @FXML
    private TextField statsModifyPlayer;

    @FXML
    private RadioButton statsModifyShots;

    @FXML
    private TextField statsModifyNumberDay;

    @FXML
    private TextField statsModifyTeamHome;

    @FXML
    private TextField statsModifyYear;

    @FXML
    private TextField statsTeamAway;

    @FXML
    private TextField statsTeamHome;

    @FXML
    private TextField statsYear;

    @FXML
    private TextField showStatsYear;

    @FXML
    private TableColumn<Participation, Integer> tabStatsGol;

    @FXML
    private TableColumn<Participation, Integer> tabStatsMinutes;

    @FXML
    private TableColumn<Participation, Integer> tabStatsPlayer;

    @FXML
    private TableColumn<Participation, Integer> tabStatsShots;

    @FXML
    private TableColumn<Participation, Integer> tabStatsNumberDay;

    @FXML
    private TableColumn<Participation, String> tabStatsTeamHome;

    @FXML
    private TableColumn<Participation, Integer> tabStatsYear;


    @FXML
    void initialize() {
        Platform.runLater(() -> {
            this.participationTable = new ParticipationTable(MySQLConnection.getConnection());
            this.matchesTable = new MatchesTable(MySQLConnection.getConnection());
            this.participationTable.createTable();
            this.matchesTable.createTable();
            initializeTables();
            showMatchesStats();
        });
    }

    @FXML
    void modifyStats() {
        try {
            final int year = Integer.parseInt(this.statsModifyYear.getText());
            final String teamHome = this.statsModifyTeamHome.getText();
            final int numberDay = Integer.parseInt(this.statsModifyNumberDay.getText());
            final int card = Integer.parseInt(this.statsModifyPlayer.getText());
            final int stats = Integer.parseInt(this.statsModifyNumber.getText());
            if (statsModifyGol.isSelected()){
                this.participationTable.updateGol(teamHome, year, numberDay, card, stats);
            } else if (statsModifyMinutes.isSelected()){
                this.participationTable.updateMinutes(teamHome, year, numberDay, card, stats);
            } else {
                this.participationTable.updateShots(teamHome, year, numberDay, card, stats);
            }
            showMatchesStats();
            Utils.hideTextField(statsModifyNumber, statsModifyYear, statsModifyTeamHome, statsModifyNumberDay, statsModifyPlayer);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void showStats() {
        try {
            final int year = Integer.parseInt(this.statsYear.getText());
            final String teamHome = this.statsTeamHome.getText();
            final String teamAway = this.statsTeamAway.getText();
            final Optional<Match> match = this.matchesTable.findMatch(year, teamHome, teamAway);
            if (match.isPresent()) {
                final var stats = this.participationTable.findStatsMatch(match.get().getTeamHome(), match.get().getYear(), match.get().getDay());
                ObservableList<Participation> list = FXCollections.observableArrayList(stats);
                this.statsTableView.setItems(list);
            }
            Utils.hideTextField(statsYear, statsTeamHome, statsTeamAway);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void showStatsMatches() {
        try {
            final int year = Integer.parseInt(this.showStatsYear.getText());
            final var stats = this.participationTable.findStatsPlayerInMatches(year);
            ObservableList<Participation> list = FXCollections.observableArrayList(stats);
            this.statsTableView.setItems(list);
            Utils.hideTextField(showStatsYear);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    public void selectRadioGol() {
        unselectRadioButtons(statsModifyMinutes, statsModifyShots);
    }
    @FXML
    public void selectRadioShots() {
        unselectRadioButtons(statsModifyGol, statsModifyMinutes);
    }
    @FXML
    public void selectRadioMinutes() {
        unselectRadioButtons(statsModifyGol, statsModifyShots);
    }

    private void unselectRadioButtons(final RadioButton b1, final RadioButton b2){
        if(b1.isSelected()){
            b1.selectedProperty().setValue(false);
        }
        if(b2.isSelected()){
            b2.selectedProperty().setValue(false);
        }
    }

    private void showMatchesStats() {
        var participation = this.participationTable.findAll();
        var list = FXCollections.observableArrayList(participation);
        this.statsTableView.setItems(list);
    }

    private void initializeTables() {
        tabStatsYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        tabStatsTeamHome.setCellValueFactory(new PropertyValueFactory<>("teamHome"));
        tabStatsNumberDay.setCellValueFactory(new PropertyValueFactory<>("numberDay"));
        tabStatsPlayer.setCellValueFactory(new PropertyValueFactory<>("cardPlayer"));
        tabStatsGol.setCellValueFactory(new PropertyValueFactory<>("numberGol"));
        tabStatsMinutes.setCellValueFactory(new PropertyValueFactory<>("minutes"));
        tabStatsShots.setCellValueFactory(new PropertyValueFactory<>("shots"));

    }
}
