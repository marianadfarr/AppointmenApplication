package Controller;

import DAOAcess.DBAppointment;
import DAOAcess.DBContact;
import DAOAcess.DBCustomer;
import DAOAcess.DBUser;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.ResourceBundle;
import Model.Session;

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
    private TextField Type;

    @FXML
    private DatePicker DatePicker;
    @FXML
    private TextField endTime;

    @FXML
    private TextField startTime;

    @FXML
    private Button CancelButton;


    @FXML
    private ComboBox<String> ContactComboBox;

    @FXML
    private ComboBox<String> CustomerIDComboBox;

    @FXML
    private ComboBox<String> UserIDComboBox;


    Random rand = new Random();

//    Customer selectedCustomer = new Customer(); //customer to add


    @FXML
    void OnActionSave(ActionEvent event) throws IOException, SQLException {

        Boolean validStart = true;
        Boolean validEnd = true;
        Boolean AppointmentOverlap = true;
        Boolean BusinessHours = true;
        String errorMessage = "";

        int AppointmentID = rand.nextInt(5, 500); //assigned a random number between 5 and 400.
        String title = Title.getText();
        String description = Description.getText();
        String location = Location.getText();
        String contactName= ContactComboBox.getValue(); //you need to put contact object no?
        String type = Type.getText();
        Integer CustomerID = Integer.valueOf(CustomerIDComboBox.getSelectionModel().getSelectedItem());
        Integer userID = Integer.valueOf(UserIDComboBox.getSelectionModel().getSelectedItem());
        LocalDate Date = DatePicker.getValue();
        LocalDateTime endDateTime = null;
        LocalDateTime startDateTime = null;
        ZonedDateTime zonedEndDateTime = null;
        ZonedDateTime zonedStartDateTime = null;

        // take Contact name from combo box. and find the contact_ID so this can be added in SQL DB
        Integer contactID = DBContact.findContactID(contactName);

        DateTimeFormatter ApptDTFormatter = DateTimeFormatter.ofPattern("HH:mm:ss"); //formatter for DateTime - hour, min & seconds

        try {  //Takes a date and time creates a local datetime
            startDateTime = LocalDateTime.of(DatePicker.getValue(),
                    LocalTime.parse(startTime.getText(), ApptDTFormatter)); //this is a localdatetime
            validStart = true;
        } catch (DateTimeParseException error) {
            validStart = false;
            errorMessage += "Invalid Start time. Please use the format HH:MM:SS.";
        }
//takes a date and time, creates a localdatetime
        try {
            endDateTime = LocalDateTime.of(DatePicker.getValue(), //this is a local date time
                    LocalTime.parse(endTime.getText(), ApptDTFormatter));
            validEnd = true;
        } catch (DateTimeParseException error) {
            validEnd = false;
            errorMessage += "Invalid End time. Please ensure proper format HH:MM:SS";
        }


        // INPUT VALIDATION: check that business hours are valid and there is no double booked customers.
        BusinessHours = ValidateBusinessHours(startDateTime, endDateTime, Date);
        AppointmentOverlap = FindAppointmentOverlap(CustomerID, startDateTime, endDateTime, Date);

        // INPUT VALIDATION: set corresponding error for user
        if (!BusinessHours) {
            errorMessage += "Invalid Business Hours - Appointment must be between 8am to 10pm EST";
        }
        if (AppointmentOverlap) {
            errorMessage += "There is a customer overlap. Try again. ";
        }

        // INPUT VALIDATION: Ensure all fields have been entered
        if (title.isBlank() || description.isBlank() || location.isBlank() || contactName == null || type.isBlank() ||
                CustomerID == null || userID == null || Date == null || endDateTime == null ||
                startDateTime == null) {

            errorMessage += "Please ensure a value has been entered in all fields.";


        }

        // INPUT VALIDATION - if any requirements are false, show error and end method.
        if (AppointmentOverlap || !BusinessHours || !validStart || !validEnd) {
            ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            Alert invalidInput = new Alert(Alert.AlertType.WARNING, errorMessage, clickOkay); //puts all error messages
            invalidInput.showAndWait();
            return;

        }
        else {
            // if input is valid we insert into DB and display success and clear.
            // prep start and endTime by turning them into a zonedDateTime so we can enter in the DB in UTC.
            zonedStartDateTime = ZonedDateTime.of(startDateTime, Session.getUserTimeZone()); //should we use timestamp?
            zonedEndDateTime = ZonedDateTime.of(endDateTime, Session.getUserTimeZone());
            String loggedOnUser = Session.getLoggedOnUser().getUserName(); //get the name of current user to put into created by and updatedby fields
        // Add appt to DB - title, description, including last updatedby and created by (logged on user)
        Boolean AppointmentAdded = DBAppointment.CreateAppointment(title, description, location, type, zonedStartDateTime,
                zonedEndDateTime,loggedOnUser, loggedOnUser, CustomerID, userID, contactID);
            // notify user we successfully added to DB, or if there was an error.
        if (AppointmentAdded) {
  //switch to appointment view
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            System.out.println("error msg");
        }

    }
    }




//checks if entered datetimes and starttimes are valid per 8 am - 10 pm EST, not inlcuding weekends

public Boolean ValidateBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate Date) {

        // Turn into zonedDateTimeObject, so we can evaluate whatever time was entered in user time zone against EST

        ZonedDateTime startZonedDateTime = ZonedDateTime.of(startDateTime, Session.getUserTimeZone());
        ZonedDateTime endZonedDateTime = ZonedDateTime.of(endDateTime, Session.getUserTimeZone());

        ZonedDateTime startBusinessHours = ZonedDateTime.of(Date, LocalTime.of(8, 0),
                ZoneId.of("America/New_York"));
        ZonedDateTime endBusinessHours = ZonedDateTime.of(Date, LocalTime.of(22, 0),
                ZoneId.of("America/New_York"));

        // If startTime is before or after business hours
        // If end time is before or after business hours
        // if startTime is after endTime - these should cover all possible times entered and validate input.
        if (startZonedDateTime.isBefore(startBusinessHours) || startZonedDateTime.isAfter(endBusinessHours) ||
                endZonedDateTime.isBefore(startBusinessHours) || endZonedDateTime.isAfter(endBusinessHours) ||
                startZonedDateTime.isAfter(endZonedDateTime)) {
            return false;

        } else {
            return true;
        }

    }

    // Get list of customer appointments that might have conflicts because they are on the same date.
    public Boolean FindAppointmentOverlap(Integer inputCustomerID, LocalDateTime startDateTime,
                                          LocalDateTime endDateTime, LocalDate Date) throws SQLException {


        ObservableList<Appointment> AppointmentConflicts = DBAppointment.getCustomerFilteredAppointments(Date,
                inputCustomerID);
        // for each possible appointment for that custumer on the same date, evaluate:
        // if conflictApptStart is before the new appointment we want to make  AND conflictApptEnd is after newApptStart(starts before ends after)
        // if conflictApptStart is before newApptEnd (if appointment starts before another ends) AND conflictApptStart after newApptStart (startime anywhere in appt)
        // if endtime is before end and endtime is after start (endtime falls anywhere in appt)
        if (AppointmentConflicts.isEmpty()) {
            return false;
        } else {
            //for every appointment object in the possible appointment conflicts list
            for (Appointment appointment : AppointmentConflicts) {
             //get the start time and end time to those conlifct appointments
                LocalDateTime conflictStart = appointment.getStartDatetime().toLocalDateTime();
                LocalDateTime conflictEnd = appointment.getEndDatetime().toLocalDateTime();

                // Conflict starts before and Conflict ends any time after new appt ends - overlap
                if (conflictStart.isBefore(startDateTime) & conflictEnd.isAfter(endDateTime)) {
                    return true;
                }
                // ConflictAppt start time falls anywhere in the new appt
                if (conflictStart.isBefore(endDateTime) & conflictStart.isAfter(startDateTime)) {
                    return true;
                }
                // ConflictAppt end time falls anywhere in the new appt
                if (conflictEnd.isBefore(endDateTime) & conflictEnd.isAfter(startDateTime)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;

    }

    @FXML
    void OnActionMainScreen(ActionEvent event) throws IOException { //exception just in case this FXML file doesn't exist , input output error
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try { //set all ContactID, Customer ID, and User ID data in combo boxes.
            ContactComboBox.setItems(DBContact.getAllContactNames()); //we actually need contact name
            CustomerIDComboBox.setItems(DBCustomer.getAllCustomerIDs()); //this is fine
            UserIDComboBox.setItems(DBUser.getAllUserIDs());
//            DatePicker.setDisable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}