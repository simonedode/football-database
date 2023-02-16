package it.unibo.database.controller;

import it.unibo.database.connection.MySQLConnection;
import it.unibo.database.model.HistoricPlayer;
import it.unibo.database.table.HistoricPlayersTable;
import it.unibo.database.utils.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class HistoricPlayersController {
    private HistoricPlayersTable historicPlayersTable;

    @FXML
    private TableView<HistoricPlayer> stoTableView;

    @FXML
    private TextField stoCardPlayer;

    @FXML
    private TextField stoDateFinish;

    @FXML
    private TextField stoDateStart;

    @FXML
    private TextField stoModifyCardPlayer;

    @FXML
    private TextField stoModifyNumber;

    @FXML
    private TextField stoModifyTeam;

    @FXML
    private TextField stoModifyYear;

    @FXML
    private TextField stoNumber;

    @FXML
    private TextField stoSalary;

    @FXML
    private TextField stoSearchCardPlayer;

    @FXML
    private TextField stoTeam;

    @FXML
    private TableColumn<HistoricPlayer, Optional<Date>> tabStoDateFinish;

    @FXML
    private TableColumn<HistoricPlayer, Date> tabStoDateStart;

    @FXML
    private TableColumn<HistoricPlayer, Integer> tabStoNumber;

    @FXML
    private TableColumn<HistoricPlayer, Integer> tabStoPlayer;

    @FXML
    private TableColumn<HistoricPlayer, Integer> tabStoSalary;

    @FXML
    private TableColumn<HistoricPlayer, String> tabStoTeam;

    @FXML
    void initialize() {
        Platform.runLater(() -> {
            this.historicPlayersTable = new HistoricPlayersTable(MySQLConnection.getConnection());
            this.historicPlayersTable.createTable();
            initializeTables();
            showHistoricPlayers();
        });
    }

    @FXML
    void addHistoryPlayer() {
        try {
            final String team = this.stoTeam.getText();
            final Date dateStart = Utils.convertStringDateToDate(this.stoDateStart.getText()).orElse(new Date());
            final int card = Integer.parseInt(this.stoCardPlayer.getText());
            final int number = Integer.parseInt(this.stoNumber.getText());
            final Date dateFinish = Utils.convertStringDateToDate(this.stoDateFinish.getText()).orElse(new Date());
            final int salary = Integer.parseInt(this.stoSalary.getText());
            this.historicPlayersTable.inserts(new HistoricPlayer(team, dateStart, card, number, dateFinish, salary));
            showHistoricPlayers();
            Utils.hideTextField(stoTeam, stoDateStart, stoCardPlayer, stoNumber, stoDateFinish, stoSalary);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void searchHistoryPlayer() {
        try {
            final int card = Integer.parseInt(this.stoSearchCardPlayer.getText());
            List<HistoricPlayer> history = this.historicPlayersTable.findByCard(card);
            ObservableList<HistoricPlayer> list = FXCollections.observableArrayList(history);
            this.stoTableView.setItems(list);
            Utils.hideTextField(stoSearchCardPlayer);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void modifyNumber() {
        try {
            final String team = this.stoModifyTeam.getText();
            final Date year = Utils.convertStringDateToDate(this.stoModifyYear.getText()).orElse(new Date());
            final int card = Integer.parseInt(this.stoModifyCardPlayer.getText());
            final int newNumber = Integer.parseInt(this.stoModifyNumber.getText());
            this.historicPlayersTable.updateNumberShirt(team, year, card, newNumber);
            showHistoricPlayers();
            Utils.hideTextField(stoModifyNumber, stoModifyCardPlayer, stoModifyTeam, stoModifyYear);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private void showHistoricPlayers(){
        List<HistoricPlayer> historicCoaches = this.historicPlayersTable.findAll();
        ObservableList<HistoricPlayer> list = FXCollections.observableArrayList(historicCoaches);
        this.stoTableView.setItems(list);
    }

    private void initializeTables() {
        tabStoTeam.setCellValueFactory(new PropertyValueFactory<>("nameTeam"));
        tabStoDateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        tabStoPlayer.setCellValueFactory(new PropertyValueFactory<>("cardPlayer"));
        tabStoNumber.setCellValueFactory(new PropertyValueFactory<>("numberShirt"));
        tabStoDateFinish.setCellValueFactory(new PropertyValueFactory<>("dateFinish"));
        tabStoSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

    }
}
