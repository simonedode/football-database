package it.unibo.database.controller;

import it.unibo.database.connection.MySQLConnection;
import it.unibo.database.model.Coach;
import it.unibo.database.table.CoachesTable;
import it.unibo.database.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class CoachesController {
    private CoachesTable coachesTable;

    @FXML
    public TextField coachSalary;

    @FXML
    private TableView<Coach> coachTableView;

    @FXML
    private TextField coachCF;

    @FXML
    private TextField coachDate;

    @FXML
    private TextField coachDateStart;

    @FXML
    private TextField coachID;

    @FXML
    private TextField coachName;

    @FXML
    private TextField coachNationality;

    @FXML
    private TextField coachNumberTeams;

    @FXML
    private TextField coachSurname;

    @FXML
    private TextField coachTeam;

    @FXML
    private TableColumn<Coach, String> tabCoachCF;

    @FXML
    private TableColumn<Coach, Integer> tabCoachCard;

    @FXML
    private TableColumn<Coach, Date> tabCoachDate;
    @FXML
    public TableColumn<Coach, Integer> tabCoachSalary;

    @FXML
    private TableColumn<Coach, Date> tabCoachDateStart;

    @FXML
    private TableColumn<Coach, String> tabCoachName;

    @FXML
    private TableColumn<Coach, String> tabCoachNationality;

    @FXML
    private TableColumn<Coach, Integer> tabCoachPresence;

    @FXML
    private TableColumn<Coach, String> tabCoachSurname;

    @FXML
    private TableColumn<Coach, String> tabCoachTeam;

    @FXML
    void initialize() {
        Platform.runLater(() ->{
            final Connection connection = MySQLConnection.getConnection();
            this.coachesTable = new CoachesTable(connection);
            this.coachesTable.createTable();

            initializeTable();
            showCoaches();
        });
    }

    @FXML
    void addCoach() {
        try {
            final String name = coachName.getText();
            final String surname = coachSurname.getText();
            final String CF = coachCF.getText();
            final Date birth = Utils.convertStringDateToDate(coachDate.getText()).orElse(new Date());
            final String nationality = coachNationality.getText();
            final String team = coachTeam.getText();
            final int numberTeams = Integer.parseInt(coachNumberTeams.getText());
            final Date dateStart = Utils.convertStringDateToDate(coachDateStart.getText()).orElse(new Date());
            final int salary = Integer.parseInt(this.coachSalary.getText());
            this.coachesTable.inserts(name, surname, CF, birth, nationality, team, numberTeams, dateStart, salary);
            showCoaches();
            Utils.hideTextField(coachName, coachSurname, coachCF, coachDate, coachNationality, coachTeam, coachNumberTeams, coachDateStart);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    void deleteCoach() {
        try {
            final int card = Integer.parseInt(coachID.getText());
            coachesTable.delete(card);
            showCoaches();
            Utils.hideTextField(coachID);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void showCoaches() {
        List<Coach> coaches = this.coachesTable.findAll();
        ObservableList<Coach> list = FXCollections.observableArrayList(coaches);
        this.coachTableView.setItems(list);
    }

    private void initializeTable() {
        tabCoachName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabCoachSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tabCoachCF.setCellValueFactory(new PropertyValueFactory<>("CF"));
        tabCoachDate.setCellValueFactory(new PropertyValueFactory<>("birth"));
        tabCoachNationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        tabCoachCard.setCellValueFactory(new PropertyValueFactory<>("card"));
        tabCoachTeam.setCellValueFactory(new PropertyValueFactory<>("team"));
        tabCoachPresence.setCellValueFactory(new PropertyValueFactory<>("teamTrained"));
        tabCoachDateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        tabCoachSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

}
