package Controller;

import DAOAcess.DBAppointment;

import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Handles the logic for the main page of the application.
 */
import static DAOAcess.DBAppointment.GetAllAppointments;

public class MainScreenController implements Initializable {

    @FXML
    private TableView<Appointment> AppointmentTableView;

    @FXML
    private TableColumn<Appointment, Integer> ContactCol;

    @FXML
    private TableColumn<Appointment, String> DescriptionCol;

    @FXML
    private TableColumn<Appointment, Integer> IDCol;

    @FXML
    private TableColumn<Appointment, String> TypeCol;

    @FXML
    private TableColumn<Appointment, Integer> customerCol;

    @FXML
    private TableColumn<Appointment, String> endCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, String> startCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, Integer> userIDcol;

    @FXML
    private ToggleGroup ToggleGroup;

    @FXML
    private RadioButton AllApptToggle;

    @FXML
    private RadioButton MonthViewToggle;

    @FXML
    private RadioButton WeekViewToggle;


    Stage stage;
    Parent scene;
    /** Appointment Selected (to modify/delete
     *
     */
    private static Appointment AppointmentSelected;

    /**Returns appointment selected (to modify/delete)
     * @return AppointmentSelected
     */
    public static Appointment getAppointmentSelected() {

        return AppointmentSelected;
    }

    /**Shows all Appointments in Table View
     * @param event  Radio button selection returns all appointments
     * @throws SQLException if DB did not grab appointments
     */
    @FXML
    void OnActionAllAppointments(ActionEvent event) throws SQLException {
        AppointmentTableView.setItems(GetAllAppointments());

    }

    /**
     * Shows this month's appointments only.
     * @param event Radio button selection returns only this month's appointments
     * @throws SQLException if database did not grab appointments
     */
    @FXML
    void OnActionMonthAppointments(ActionEvent event) throws SQLException {
        AppointmentTableView.setItems(DBAppointment.GetAllMonthAppointments());

    }

    /** Shows this week's appointments only.
     * @param event Radio button selection returns only this week's appointments
     * @throws SQLException if database did not grab appointments.
     */

    @FXML
    void OnActionWeekAppointments(ActionEvent event) throws SQLException {
        AppointmentTableView.setItems(DBAppointment.GetAllWeekAppointments());
    }

    /**Create appointment - switches screen to add appointment.
     * @param event Clicking on the create appointment button leads us to the "Add Appointment" screen
     * @throws IOException if no such page exists.
     */

    @FXML
    void OnActionCreateAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddAppointmentScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Switch to the customer screen
     * @param event Clicking on the Customers button leads us to the all customer screen
     * @throws IOException if no such page exists
     */
    @FXML
    void OnActionCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Updating a selected appointment
     * @param event clicking on the update button
     * @throws IOException if no appointment was selected beforehand
     */

    @FXML
    void OnActionUpdateAppointment(ActionEvent event) throws IOException {
        AppointmentSelected = AppointmentTableView.getSelectionModel().getSelectedItem();
        if (AppointmentSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointment selected."); //alert is an overloaded method
            Optional<ButtonType> result = alert.showAndWait();
            return;
        } else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/ModifyAppointmentScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }

    /**
     * Delete a selected appointment
     * @param event clicking on the delete appointment button.
     * @throws IOException if no appointment was selected beforehand.
     * @throws SQLException if no appointment was deleted.
     */
    @FXML
    void OnActionDeleteAppointment(ActionEvent event) throws IOException, SQLException {
        AppointmentSelected = AppointmentTableView.getSelectionModel().getSelectedItem();
        if (AppointmentSelected == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointment selected."); //alert is an overloaded method
            Optional<ButtonType> result = alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the appointment. Are you sure you want to proceed?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            DBAppointment.DeleteAppointment(AppointmentSelected.getAppointmentID());

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "You have successfully deleted the appointment with ID:  " + AppointmentSelected.getAppointmentID()+ "  and Type: " + AppointmentSelected.getType());
            Optional<ButtonType> result1 = alert1.showAndWait();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }


    }

    /** Brings user to the Reports page
     * @param event clicking the Reports button, leads to the reports page.
     * @throws IOException if no such page exists.
     */
    @FXML
    void OnActionReports(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /** Sets all objects returned from GetAllAppointments(),which is an Observable List of appointment objects.
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AppointmentTableView.setItems(GetAllAppointments());//set all appointments according to variable name of constructor
            IDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            ContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("startDatetime")); //has to match getter in capitalization
            endCol.setCellValueFactory(new PropertyValueFactory<>("endDatetime"));
            customerCol.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
            userIDcol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



