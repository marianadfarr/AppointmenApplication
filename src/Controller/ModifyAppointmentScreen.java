package Controller;
import DAOAcess.DBAppointment;
import DAOAcess.DBContact;
import DAOAcess.DBCustomer;
import DAOAcess.DBUser;
import Model.Appointment;
import Model.Session;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Handles logic for the "Modify Appointment" screen
 */
public class ModifyAppointmentScreen implements Initializable {

    Stage stage;
    Parent scene;


    private Appointment AppointmentToEdit;


    @FXML
    private ComboBox<Integer> UserIDComboBox;

    @FXML
    private ComboBox<String> ContactComboBox;

    @FXML
    private ComboBox<Integer> CustomerIDComboBox;

    @FXML
    private javafx.scene.control.DatePicker DatePicker;

    @FXML
    private TextField Description;

    @FXML
    private TextField ID;

    @FXML
    private TextField Location;


    @FXML
    private TextField Title;

    @FXML
    private TextField Type;


    @FXML
    private TextField endTime;

    @FXML
    private TextField startTime;

    /** Brings user back to the Main Screen
     * @param event clicking on back button brings user back to Main Screen
     * @throws IOException if no such page exists
     */
    @FXML
    void OnActionMainScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Updates an Appointment object
     * @param event OnActionSave saves a new appointment object after verifying input for validity.
    Checks the start time is before the end time and end time is after the start time.
    Checks that business hours are valid (8 am to 10 pm EST).
    Checks customers do not have overlaps in appointments.
    Check that dates that have been entered, and times are formatted correctly, and converts times into timestamps.
    Throws error message if any inputs are invalid.
    Adds appointment to the database.
    Returns success message if appointment is updated, and redirects to main page.
     * @throws IOException if main screen does not exist.
     * @throws SQLException if DB did not update appointment.
     */
    @FXML
    void OnActionSave(ActionEvent event) throws IOException, SQLException {
        Integer appointmentID = Integer.valueOf(ID.getText());
        String title = Title.getText();
        String description = Description.getText();
        String location = Location.getText();
        String contactName = ContactComboBox.getValue();
        String type = Type.getText();
        Integer CustomerID = CustomerIDComboBox.getValue();
        Integer userID = UserIDComboBox.getValue();
        LocalDate Date = DatePicker.getValue();
        LocalDateTime endDateTime = null;
        LocalDateTime startDateTime = null;


        Integer contactID = DBContact.findContactID(contactName);
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("HH:mm");
        if (Date == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must pick a date from the calendar");
            alert.showAndWait();
            return;

        }
        try {
            startDateTime = LocalDateTime.of(DatePicker.getValue(),
                    LocalTime.parse(startTime.getText(), DTF));
        } catch (DateTimeParseException error) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Start time. Please use the format HH:MM");
            alert.showAndWait();
            return;

        }

        try {
            endDateTime = LocalDateTime.of(DatePicker.getValue(),
                    LocalTime.parse(endTime.getText(), DTF));

        } catch (DateTimeParseException error) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid End time. Please use the format HH:MM");
            alert.showAndWait();
            return;
        }

        if (title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank() || contactName == null || userID == null || Date == null || endDateTime == null || startDateTime == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must be filled in.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK);
            return;
        }

        Boolean AppointmentHours = ValidateAppointmentHours(startDateTime, endDateTime);
        Boolean BusinessHours = CheckBusinessHours(startDateTime, endDateTime, Date); //
        Boolean AppointmentOverlap = FindAppointmentOverlap(CustomerID, startDateTime, endDateTime);



        if (!AppointmentHours) {
            ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            Alert invalid = new Alert(Alert.AlertType.ERROR, "Make sure the start time is before the end time, and the end time is after start time.", clickOkay);
            invalid.showAndWait();
            return;

        }
        if (!BusinessHours) {
            ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            Alert invalid = new Alert(Alert.AlertType.ERROR, "Invalid Business Hours - Appointment must be between 8am to 10pm EST", clickOkay);
            invalid.showAndWait();
            return;

        }
        if (AppointmentOverlap) {
            ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            Alert invalid = new Alert(Alert.AlertType.ERROR, "Appointment Overlap Found for Customer. Please choose a new time", clickOkay);
            invalid.showAndWait();
            return;

        }

        else {
            Timestamp StartTimeStamp= Timestamp.valueOf(startDateTime);
            Timestamp EndDateTimeStamp= Timestamp.valueOf(endDateTime);
            String User = Session.getCurrentUser().getUserName();

            Boolean AppointmentUpdated = DBAppointment.updateAppointment(appointmentID, title, description, location, type, StartTimeStamp,
                    EndDateTimeStamp, User, CustomerID, userID, contactID);
            if (AppointmentUpdated) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have successfully updated the appointment");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) ;
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        }
    }

    /** Validates appointment start time is before end time, and end time is before start time.
     * @param startDateTime Appointment start time
     * @param endDateTime  Appointment end time
     * @return true if the appointment times are valid, false otherwise.
     */
    public Boolean ValidateAppointmentHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime.isAfter(endDateTime) || (endDateTime.isBefore(startDateTime))) {
            return false;
        } else {
            return true;
        }
    }

    /** Checks that appointment falls within valid business hours.
     * @param startDateTime appointment start time
     * @param endDateTime appointment end time.
     * @param Date appointment date.
     * @return true if appointment hours are within business hours, false otherwise.
     */
    public Boolean CheckBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate Date) {

        ZonedDateTime startZDT = ZonedDateTime.of(startDateTime, Session.getUserTimeZone());
        ZonedDateTime endZDT = ZonedDateTime.of(endDateTime, Session.getUserTimeZone());
        ZonedDateTime startOfBusinessHours = ZonedDateTime.of(Date, LocalTime.of(8, 0),
                ZoneId.of("America/New_York"));
        ZonedDateTime endBusinessHours = ZonedDateTime.of(Date, LocalTime.of(22, 0),
                ZoneId.of("America/New_York"));

        if (startZDT.isBefore(startOfBusinessHours) || startZDT.isAfter(endBusinessHours) ||
                endZDT.isAfter(endBusinessHours) || endZDT.isBefore(startOfBusinessHours)) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean FindAppointmentOverlap(Integer CustomerID, LocalDateTime startDateTime,
                                          LocalDateTime endDateTime) throws SQLException {


        ObservableList<Appointment> AppointmentConflicts = DBAppointment.getCustomerAppointments(
                CustomerID);
        if (AppointmentConflicts.isEmpty()) {
            return false;
        } else {
            //timestamp of new appointment
            Timestamp startTimeStamp = Timestamp.valueOf(startDateTime);
            Timestamp endDateTimeStamp = Timestamp.valueOf(endDateTime);
            //For every appointment object in the possible appointment conflicts list
            for (Appointment appointment : AppointmentConflicts) {
                //account for the appointment with the same ID. We do not have to end loop here, however.
                if (appointment.getAppointmentID() == AppointmentToEdit.getAppointmentID())
                {
                   continue;
                }
                    //Get the start time and end time to those conflict appointments
                    Timestamp ConApptStart = appointment.getStartDatetime();
                    Timestamp ConApptEnd = appointment.getEndDatetime();
                //If conflict appointment starts before new appointment AND also ends after end of new appointment
                if (startTimeStamp.before(ConApptStart) && endDateTimeStamp.after(ConApptEnd)) {
                    return true;
                }
                // If conflict appointment starts before the end of new appointment AND starts after the beginning of new appointment
                if (startTimeStamp.after(ConApptStart) && startTimeStamp.before(ConApptEnd)) {
                    return true;
                }
                // If conflict appointment end time is before the new appointment end, and is also after new appointment start.
                if (endDateTimeStamp.after(ConApptStart) && endDateTimeStamp.before(ConApptEnd)) {
                    return true;
                }
                if (startTimeStamp.after(ConApptStart) && endDateTimeStamp.before(ConApptEnd)) {
                    return true;
                }
                if (ConApptEnd.equals(endDateTimeStamp) || ConApptStart.equals(startTimeStamp)){
                    return true;
                }


            }
        }
        return false;
    }

    /**Set all pertinent information for the appointment chosen for modification.
     * Contains Lambda expression to disable Date picker for dates before today.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AppointmentToEdit = MainScreenController.getAppointmentSelected();
            CustomerIDComboBox.setItems(DBCustomer.getAllCustomerIDs());
            CustomerIDComboBox.getSelectionModel().select(Integer.valueOf(String.valueOf(AppointmentToEdit.getCustomerID())));
            UserIDComboBox.setItems(DBUser.getAllUserIDs());
            UserIDComboBox.getSelectionModel().select(Integer.valueOf(String.valueOf(AppointmentToEdit.getUserID())));
            ID.setText(String.valueOf(AppointmentToEdit.getAppointmentID())); //wrap in a String to get the right number printed
            Title.setText(AppointmentToEdit.getTitle());
            Description.setText(AppointmentToEdit.getDescription());
            Location.setText(AppointmentToEdit.getLocation());
            DatePicker.setValue(AppointmentToEdit.getStartDatetime().toLocalDateTime().toLocalDate()); //Get only local date.
            ContactComboBox.setItems(DBContact.getAllContactNames());
            ContactComboBox.getSelectionModel().select(AppointmentToEdit.getContactName());
            Type.setText(AppointmentToEdit.getType());
            DateTimeFormatter DTF = DateTimeFormatter.ofPattern("HH:mm");
            String StartTime = String.valueOf(AppointmentToEdit.getStartDatetime().toLocalDateTime().toLocalTime()).formatted(DTF);
            String EndTime = String.valueOf(AppointmentToEdit.getEndDatetime().toLocalDateTime().toLocalTime()).formatted(DTF);
            startTime.setText(StartTime); //only the start time, without date
            endTime.setText(EndTime); //only the end time, without date.
    } catch (SQLException error) {
        error.printStackTrace();
    }

            DatePicker.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate Date, boolean empty) {
                    super.updateItem(Date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || Date.compareTo(today) < 0);



                }
            });}}