package Controller;

import DAOAcess.DBReports;
import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

import static DAOAcess.DBAppointment.GetAllAppointments;

/**
 * Logic for the Reports screen
 */
public class ReportsController implements Initializable {
    Stage stage;
    Parent scene;


    @FXML
    private RadioButton DanielButton;


    @FXML
    private RadioButton AnikaButton;

    @FXML
    private TableColumn<Appointment, Integer> ApptIDCol;


    @FXML
    private ToggleGroup ContactGroup;

    @FXML
    private TableColumn<Appointment, Integer> CustomerIDCol;


    @FXML
    private TableView<Appointment> ContactScheduleTV;
    @FXML
    private TableColumn<Appointment, String> DescriptionCol;

    @FXML
    private TableColumn<Appointment, String> EndDateCol;


    @FXML
    private RadioButton LiButton;

    @FXML
    private Button MainMenuButton;

    @FXML
    private AnchorPane Reports;


    @FXML
    private TableColumn<Appointment, String> StartDateCol;

    @FXML
    private TableColumn<Appointment, String> TitleCol;

    @FXML
    private TableColumn<Appointment, String> TypeCOl;
    @FXML
    private TextField UKCount;

    @FXML
    private TextField USCount;

    @FXML
    private TextField CanadaCount;

    @FXML
    private TextArea TypeAndMonthArea;

    /**
     * Filters appointments by Anika (contact)
     *
     * @param event Radio button selection shows only Anika's appointments
     * @throws SQLException if database could not find contact
     */
    @FXML
    void OnActionAnikaSchedule(ActionEvent event) throws SQLException {
        ContactScheduleTV.setItems(DBReports.getAllContactAppointments(1));
    }

    /**
     * Filters appointments by Daniel (contact).
     * @param event Radio button selection shows only Daniel's appointments
     * @throws SQLException if database could not find contact
     */
    @FXML
    void OnActionDanielSchedule(ActionEvent event) throws SQLException {
        ContactScheduleTV.setItems(DBReports.getAllContactAppointments(2));

    }

    /**
     * Filters appointments by Li (contact).
     * @param event Radio button selection shows only Li's appointments
     * @throws SQLException if database could not find contact
     */
    @FXML
    void OnActionLiLeeSchedule(ActionEvent event) throws SQLException {
        ContactScheduleTV.setItems(DBReports.getAllContactAppointments(3));

    }

    /**
     * Brings user back to main menu
     * @param event clicking the main menu screen brings us back to the Main Screen
     * @throws IOException if no such file exists
     */
    @FXML
    void OnActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /** Sets up all data for our reports- one schedule per contact, a count of customers per country, and a count of appointment by type and month.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //contact schedule value factory
            ContactScheduleTV.setItems(GetAllAppointments());//set all appointments first
            ApptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            TitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            TypeCOl.setCellValueFactory(new PropertyValueFactory<>("type")); //for contact schedule
            DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            StartDateCol.setCellValueFactory(new PropertyValueFactory<>("startDatetime")); //has to match getter in capitalization
            EndDateCol.setCellValueFactory(new PropertyValueFactory<>("endDatetime"));
            CustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
            //Country count report
            USCount.setText(String.valueOf(DBReports.CustomersByCountryCount(1)));
            UKCount.setText(String.valueOf(DBReports.CustomersByCountryCount(2)));
            CanadaCount.setText(String.valueOf(DBReports.CustomersByCountryCount(3)));

            //Month and Type Report - text report
            String[] Months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            ObservableList Types = (DBReports.GetAllAppointmentTypes());
            for (int month = 0; month < Months.length; month++) {
                for (Object Type : Types) {
                    TypeAndMonthArea.appendText("Month " + Months[month] + ". Appointment Type: " + Type + ". Count: " + DBReports.AppointmentsByTypeandMonth(month + 1, (String) Type));
                    TypeAndMonthArea.appendText("\n");
                }
            }

            TypeAndMonthArea.appendText("\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


