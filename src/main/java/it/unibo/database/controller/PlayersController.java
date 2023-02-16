package it.unibo.database.controller;

import it.unibo.database.connection.MySQLConnection;
import it.unibo.database.model.Player;
import it.unibo.database.table.PlayersTable;
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

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class PlayersController {

    private PlayersTable playersTable;

    @FXML
    private TableView<Player> playerTableView;

    @FXML
    public TextField playerSalary;

    @FXML
    private TextField playerCF;

    @FXML
    private TextField playerDate;

    @FXML
    private TextField playerID;

    @FXML
    private TextField playerName;

    @FXML
    private TextField playerNationality;

    @FXML
    private TextField playerDateStart;

    @FXML
    private TextField playerPresence;

    @FXML
    private ComboBox<String> playerRole;

    @FXML
    private TextField playerSurname;

    @FXML
    private TextField playerTeam;

    @FXML
    public TableColumn<Player, Integer> tabPlayerSalary;

    @FXML
    private TableColumn<Player, String> tabPlayerRole;

    @FXML
    private TableColumn<Player, String> tabPlayerCF;

    @FXML
    private TableColumn<Player, Integer> tabPlayerCard;

    @FXML
    private TableColumn<Player, Date> tabPlayerDate;

    @FXML
    private TableColumn<Player, String> tabPlayerName;

    @FXML
    private TableColumn<Player, String> tabPlayerNationality;

    @FXML
    private TableColumn<Player, Date> tabPlayerPeriod;

    @FXML
    private TableColumn<Player, Integer> tabPlayerPresence;

    @FXML
    private TableColumn<Player, String> tabPlayerSurname;

    @FXML
    private TableColumn<Player, String> tabPlayerTeam;

    @FXML
    void initialize(){
        Platform.runLater(() -> {
            final Connection connection = MySQLConnection.getConnection();
            this.playersTable = new PlayersTable(connection);
            this.playersTable.createTable();

            ObservableList<String> list = FXCollections.observableArrayList("P", "D", "C", "A");
            this.playerRole.setItems(list);

            initializeTable();
            showPlayers();
        });
    }

    @FXML
    void addPlayer() {
        try {
            final String name = playerName.getText();
            final String surname = playerSurname.getText();
            final String cf = playerCF.getText();
            final Date birth = Utils.convertStringDateToDate(playerDate.getText()).orElse(new Date());
            final String nationality = playerNationality.getText();
            final String role = playerRole.getSelectionModel().getSelectedItem();
            final String presences = playerPresence.getText();
            final String team = playerTeam.getText();
            final Date date = Utils.convertStringDateToDate(playerDateStart.getText()).orElse(new Date());
            final int salary = Integer.parseInt(this.playerSalary.getText());
            this.playersTable.inserts(name, surname, cf, birth, nationality , role, Integer.parseInt(presences), team, date, salary);
            showPlayers();
            Utils.hideTextField(playerName, playerSurname, playerCF, playerDate, playerNationality, playerPresence, playerTeam,playerDateStart);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    void deletePlayer() {
        try {
            final int card = Integer.parseInt(playerID.getText());
            playersTable.delete(card);
            showPlayers();
            Utils.hideTextField(playerID);
        } catch (Exception e){
            System.out.println(e);
        }
    }

    private void showPlayers() {
        List<Player> players = this.playersTable.findAll();
        ObservableList<Player> list = FXCollections.observableArrayList(players);
        this.playerTableView.setItems(list);
    }
    private void initializeTable() {
        tabPlayerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tabPlayerSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        tabPlayerCF.setCellValueFactory(new PropertyValueFactory<>("CF"));
        tabPlayerDate.setCellValueFactory(new PropertyValueFactory<>("birth"));
        tabPlayerNationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        tabPlayerCard.setCellValueFactory(new PropertyValueFactory<>("card"));
        tabPlayerRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        tabPlayerPresence.setCellValueFactory(new PropertyValueFactory<>("presences"));
        tabPlayerTeam.setCellValueFactory(new PropertyValueFactory<>("team"));
        tabPlayerPeriod.setCellValueFactory(new PropertyValueFactory<>("date"));
        tabPlayerSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }
}
