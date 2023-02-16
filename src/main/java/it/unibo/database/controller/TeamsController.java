package it.unibo.database.controller;

import it.unibo.database.connection.MySQLConnection;
import it.unibo.database.model.Team;
import it.unibo.database.table.TeamsTable;
import it.unibo.database.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TeamsController {
    private TeamsTable teamsTable;

    @FXML
    private TableView<Team> teamTableView;

    @FXML
    private ComboBox<String> teamChooseRole;

    @FXML
    private TableColumn<Team, String> tabTeamCity;

    @FXML
    private TableColumn<Team, String> tabTeamColors;

    @FXML
    private TableColumn<Team, String> tabTeamName;

    @FXML
    private TextField teamCity;

    @FXML
    private TextField teamColors;

    @FXML
    private TextField teamID;

    @FXML
    private TextField teamName;

    @FXML
    private TextField teamNameRole;

    @FXML
    void initialize() {
        Platform.runLater(() -> {
            this.teamsTable = new TeamsTable(MySQLConnection.getConnection());
            this.teamsTable.createTable();
            ObservableList<String> list = FXCollections.observableArrayList("P", "D", "C", "A");
            this.teamChooseRole.setItems(list);
            initializeTable();
            showTeams();
        });
    }
    @FXML
    void addTeam() {
        try {
            final String name = teamName.getText();
            final String city = teamCity.getText();
            final String colors = teamColors.getText();
            this.teamsTable.inserts(new Team(name, city, colors));
            showTeams();
            Utils.hideTextField(teamName, teamCity, teamColors);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void deleteTeam() {
        try {
            final String name = teamID.getText();
            teamsTable.delete(name);
            showTeams();
            Utils.hideTextField(teamID);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void showNumberInRole() {
        try {
            final String name = teamNameRole.getText();
            final String role = teamChooseRole.getSelectionModel().getSelectedItem();
            List<Team> teams = this.teamsTable.findNumberInRole(name, role);
            ObservableList<Team> list = FXCollections.observableArrayList(teams);
            teamTableView.setItems(list);
            tabTeamColors.setText("NumeroNelRuolo: " + role);
            Utils.hideTextField(teamNameRole);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private void showTeams() {
        this.tabTeamColors.setText("Colori");
        List<Team> teams = this.teamsTable.findAll();
        ObservableList<Team> list = FXCollections.observableArrayList(teams);
        this.teamTableView.setItems(list);
    }

    private void initializeTable() {
        tabTeamName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabTeamCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tabTeamColors.setCellValueFactory(new PropertyValueFactory<>("colors"));
    }
}
