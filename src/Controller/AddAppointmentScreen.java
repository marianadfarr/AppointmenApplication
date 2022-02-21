package Controller;

import DAOAcess.DBAppointment;
import DAOAcess.DBContact;
import DAOAcess.DBCustomer;
import DAOAcess.DBUser;
import Model.*;
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
import Model.Session;

/**
 *This is the controller page for adding new appointments.
 */
public class AddAppointmentScreen implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private TextField Description;

    @FXML
    private TextField ID;

    @FXML
    private TextField Location;

    @FXML
    private Button SaveButton;

    @FXML
    private TextField Title;


    @FXML
    private DatePicker DatePicker;
    @FXML
    private TextField endTime;

    @FXML
    private TextField startTime;

    @FXML
    private Button CancelButton;

    @FXML
    private TextField Type;


    @FXML
    private ComboBox<String> ContactComboBox;

    @FXML
    private ComboBox<Integer> CustomerIDComboBox;

    @FXML
    private ComboBox<Integer> UserIDComboBox;


    /**
     * Saves the appointment object.
     *
     * @param event OnActionSave saves a new appointment object after verifying input for validity.
     *              Checks the start time is before the end time and end time is after the start time.
     *              Checks that business hours are valid (8 am to 10 pm EST).
     *              Checks customers do not have overlaps in appointments.
     *              Check that dates that have been entered, and times are formatted correctly, and converts times into timestamps.
     *              Throws error message if any inputs are invalid.
     *              Adds appointment to the database.
     *              Returns success message if appointment is added, and redirect to main page.
     * @throws IOException  if Main Screen cannot be loaded.
     * @throws SQLException if appointment is not saved to database.
     */
    @FXML
    void OnActionSave(ActionEvent event) throws IOException, SQLException {


        String title = Title.getText();
        String description = Description.getText();
        String location = Location.getText();
        String contactName = ContactComboBox.getValue();
        String type = Type.getText();
        Integer CustomerID = CustomerIDComboBox.getValue();
        Integer userID = UserIDComboBox.getValue();
        LocalDate Date = DatePicker.getValue();
        LocalDateTime endDateTime = null; //To be set with DatePicker value and Time text
        LocalDateTime startDateTime = null; //To be set with DatePicker value and Time text

        // Takes Contact name from Combobox. Finds corresponding contact_ID so this can be added to the database.
        Integer contactID = DBContact.findContactID(contactName);
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("HH:mm"); //Formats the time only.
        //Date picker cannot be empty
        if (Date == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must pick a date from the calendar");
            alert.showAndWait();
            return;

        }

        try {  //Takes a date and time creates a LocalDateTime
            startDateTime = LocalDateTime.of(DatePicker.getValue(),
                    LocalTime.parse(startTime.getText(), DTF)); //Formatted with Hours/Minutes

        } catch (DateTimeParseException error) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Start time. Please use the format HH:MM");
            alert.showAndWait();
            return;

        }
        //Takes a date and time creates a LocalDateTime
        try {
            endDateTime = LocalDateTime.of(DatePicker.getValue(),
                    LocalTime.parse(endTime.getText(), DTF));
        } catch (DateTimeParseException error) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid End time. Please use the format HH:MM");
            alert.showAndWait();
            return;
        }

        // Make sure all fields have been entered, no blank fields are allowed.
        if (title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank() || contactName == null || userID == null) {
            ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            Alert invalidInput = new Alert(Alert.AlertType.ERROR, "Please ensure a value has been entered in all fields.", clickOkay);
            invalidInput.showAndWait();
            return;
        }


        //Check  the start is before the end and end after start.
        Boolean AppointmentHours = ValidateAppointmentHours(startDateTime, endDateTime);
        // Check that business hours are valid.
        Boolean BusinessHours = CheckBusinessHours(startDateTime, endDateTime, Date);
        //Check customers do not have overlaps in appointments
        Boolean AppointmentOverlap = FindAppointmentOverlap(CustomerID, startDateTime, endDateTime);

        // Throw error message if any inputs are invalid
        if (!AppointmentHours) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Make sure the start time is before the end time, and the end time is after start time.");
            alert.showAndWait();
            return;
        } else if (!BusinessHours) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Business Hours - Appointment must be between 8am to 10pm EST");
            alert.showAndWait();
            return;

        } else if (AppointmentOverlap) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "There is a customer overlap. Try again. ");
            alert.showAndWait();
            return;

        } else {
            //turn Startdatetime and Enddatetime into  timestamps to go into Database.
            Timestamp StartTimeStamp = Timestamp.valueOf(startDateTime);
            Timestamp EndDateTimeStamp = Timestamp.valueOf(endDateTime);
            String User = Session.getCurrentUser().getUserName(); //Get the name of current user to put into Created by and Updated by fields

//          Add appointment to database - title, description, including last updated by and created by (both logged on user)
            Boolean AppointmentAdded = DBAppointment.CreateAppointment(title, description, location, type, StartTimeStamp,
                    EndDateTimeStamp, User, User, CustomerID, userID, contactID);
            if (AppointmentAdded) {
                //Switch to appointment view after successfully addition
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have successfully added the appointment"); //alert is an overloaded method
                Optional<ButtonType> result1 = alert.showAndWait();
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR, "Appointment not added. Try again. ");
                alert2.showAndWait();
            }

        }
    }


    /**
     * CheckBusiness Hours checks if entered End times and Start times are valid per 8 am - 10 pm EST, including weekend
     *
     * @param startDateTime start of our appointment
     * @param endDateTime   end of our appointment
     * @param Date          Date of our appointment
     * @return true if business hours are valid, false if they are not.
     */

    public Boolean CheckBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate Date) {


//get ZoneDateTime of the StartDate and EndDate
        ZonedDateTime startZDT = ZonedDateTime.of(startDateTime, Session.getUserTimeZone());
        ZonedDateTime endZDT = ZonedDateTime.of(endDateTime, Session.getUserTimeZone());
//get ZDT for 8 am to 10 pm EST
        ZonedDateTime startOfBusinessHours = ZonedDateTime.of(Date, LocalTime.of(8, 0),
                ZoneId.of("America/New_York"));
        ZonedDateTime endBusinessHours = ZonedDateTime.of(Date, LocalTime.of(22, 0),
                ZoneId.of("America/New_York"));

        // Validate hours

        if (startZDT.isBefore(startOfBusinessHours) || startZDT.isAfter(endBusinessHours) ||
                endZDT.isAfter(endBusinessHours) || endZDT.isBefore(startOfBusinessHours)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if start time is before end time.
     *
     * @param startDateTime start time of appointment
     * @param endDateTime   end time of appointment
     * @return true if times are valid, false otherwise
     */
    public Boolean ValidateAppointmentHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime.isAfter(endDateTime) || (endDateTime.isBefore(startDateTime))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Get list of specific customer appointments that might have conflicts because they are on the same date.
     * Then, if they have appointments on the same day, compare start and end times to find overlaps.
     *
     * @param CustomerID    ID of customer to check
     * @param startDateTime start time of customer appointment
     * @param endDateTime   end time of customer appointment
     * @return True if there is an overlap, false otherwise.
     * @throws SQLException if database fails to grab appointment
     */
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

    /**
     *
     * @param event click the Main screen button
     * @throws IOException if no such file exists
     */
    @FXML
    void OnActionMainScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Set all ContactID, Customer ID, and User ID data in combo boxes.
     * Set combo box to disable any days before today.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ContactComboBox.setItems(DBContact.getAllContactNames());
            CustomerIDComboBox.setItems(DBCustomer.getAllCustomerIDs());
            UserIDComboBox.setItems(DBUser.getAllUserIDs());





        } catch (SQLException e) {
            e.printStackTrace();
        }

        /**Lambda expression to disable Date picker for dates before today.
         */
        DatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate Date, boolean empty) {
                super.updateItem(Date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || Date.compareTo(today) < 0);



    }
});}}