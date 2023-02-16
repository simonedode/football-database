package it.unibo.database.controller;

import it.unibo.database.connection.MySQLConnection;
import it.unibo.database.model.HistoricCoach;
import it.unibo.database.table.HistoricCoachesTable;
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

public class HistoricCoachesController {
    private HistoricCoachesTable historicCoachesTable;

    @FXML
    private TableView<HistoricCoach> hisTableView;

    @FXML
    private TextField hisDateStartCoach;

    @FXML
    private TextField hisSalaryCoach;

    @FXML
    private TextField hisSearchName;

    @FXML
    private TextField hisSearchYear;

    @FXML
    private TextField hisSearchSalary;

    @FXML
    private TextField hisTeamCoach;

    @FXML
    private TextField histCardCoach;

    @FXML
    private TextField histDateFinishCoach;

    @FXML
    private TextField searchHisCardCoach;

    @FXML
    private TableColumn<HistoricCoach, Integer> tabHisCoach;

    @FXML
    private TableColumn<HistoricCoach, Optional<Date>> tabHisDateFinishCoach;

    @FXML
    private TableColumn<HistoricCoach, Date> tabHisDateStartCoach;

    @FXML
    private TableColumn<HistoricCoach, Integer> tabHisSalaryCoach;

    @FXML
    private TableColumn<HistoricCoach, String> tabHisTeamCoach;

    @FXML
    void initialize() {
        Platform.runLater(() ->{
            this.historicCoachesTable = new HistoricCoachesTable(MySQLConnection.getConnection());
            this.historicCoachesTable.createTable();
            initializeTables();
            showHistoricCoaches();
        });
    }

    @FXML
    void addHistoryCoach() {
        try {
            final int cardCoach = Integer.parseInt(this.histCardCoach.getText());
            final Date dateStart = Utils.convertStringDateToDate(this.hisDateStartCoach.getText()).orElse(new Date());
            final Date dateFinish = Utils.convertStringDateToDate(this.histDateFinishCoach.getText()).orElse(new Date());
            final String team = this.hisTeamCoach.getText();
            final int salary = Integer.parseInt(this.hisSalaryCoach.getText());
            this.historicCoachesTable.inserts(new HistoricCoach(cardCoach, dateStart, dateFinish, team, salary));
            showHistoricCoaches();
            Utils.hideTextField(histCardCoach, hisDateStartCoach, histDateFinishCoach, hisTeamCoach, hisSalaryCoach);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void searchHistoryCoachByCard() {
        try {
            final int cardCoach = Integer.parseInt(this.searchHisCardCoach.getText());
            List<HistoricCoach> historicCoaches = this.historicCoachesTable.findByCardCoach(cardCoach);
            ObservableList<HistoricCoach> list = FXCollections.observableArrayList(historicCoaches);
            this.hisTableView.setItems(list);
            Utils.hideTextField(searchHisCardCoach);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void searchCoach() {
        try {
            final String team = this.hisSearchName.getText();
            final var checkDate = Utils.convertStringDateToDate(this.hisSearchYear.getText());
            if(checkDate.isPresent()) {
                final Date dateStart = checkDate.get();
                Optional<HistoricCoach> historicCoach = this.historicCoachesTable.findByPrimaryKey(team, dateStart);
                if(historicCoach.isPresent()) {
                    ObservableList<HistoricCoach> list = FXCollections.observableArrayList(historicCoach.get());
                    this.hisTableView.setItems(list);
                }
            }
            Utils.hideTextField(hisSearchName,hisSearchYear);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void modifySalaryCoach() {
        try {
            final String team = this.hisSearchName.getText();
            final var checkDate = Utils.convertStringDateToDate(this.hisSearchYear.getText());
            if(checkDate.isPresent()) {
                final Date dateStart = checkDate.get();
                final int salary = Integer.parseInt(this.hisSearchSalary.getText());
                this.historicCoachesTable.updateSalary(team, dateStart, salary);
                showHistoricCoaches();
            }
            Utils.hideTextField(hisSearchName, hisSearchYear, hisSearchSalary);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private void showHistoricCoaches(){
        List<HistoricCoach> historicCoaches = this.historicCoachesTable.findAll();
        ObservableList<HistoricCoach> list = FXCollections.observableArrayList(historicCoaches);
        this.hisTableView.setItems(list);
    }

    private void initializeTables() {
        tabHisCoach.setCellValueFactory(new PropertyValueFactory<>("cardCoach"));
        tabHisDateStartCoach.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        tabHisDateFinishCoach.setCellValueFactory(new PropertyValueFactory<>("dateFinish"));
        tabHisTeamCoach.setCellValueFactory(new PropertyValueFactory<>("nameTeam"));
        tabHisSalaryCoach.setCellValueFactory(new PropertyValueFactory<>("salary"));

    }

}
