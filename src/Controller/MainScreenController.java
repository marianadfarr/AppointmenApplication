package Controller;

import DAOAcess.DBAppointment;
import DAOAcess.DBCustomer;
import DBConnection.JDBC;
import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

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


    ObservableList<Appointment> AppointmentObservableList = FXCollections.observableArrayList();

    Stage stage;
    Parent scene;

    private static int AppointmentSelected; //AppointmentID to delete

    public static int getSelectedAppointment() {
        return AppointmentSelected;
    }

    private static String AppointmentType;

    public static String getAppointmentType() {
        return AppointmentType;
    }
    private static Appointment AppointmentToEdit;
    public static Appointment getAppointmentToEdit() {
        return AppointmentToEdit;
    }







    @FXML
    void OnActionCreateAppointment(ActionEvent event) throws IOException { //exception just in case this FXML file doesn't exist , input output error
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddAppointmentScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void OnActionCustomer(ActionEvent event) throws IOException { //exception just in case this FXML file doesn't exist , input output error
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    @FXML
    void OnActionCountries(ActionEvent event) throws IOException { //exception just in case this FXML file doesn't exist , input output error
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/countrytest.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    @FXML
    void MonthViewAppointments(ActionEvent event) {
        System.out.println("Placeholder");
    }


    @FXML
    void WeekViewAppointments(ActionEvent event) {
        System.out.println("Placeholder");

    }
    @FXML
    void OnActionDeleteAppointment(ActionEvent event) throws IOException, SQLException {
        AppointmentSelected = AppointmentTableView.getSelectionModel().getSelectedItem().getAppointmentID();
        AppointmentType= AppointmentTableView.getSelectionModel().getSelectedItem().getType();
//appoitnment to delete ID
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the appointment. Are you sure you want to proceed?"); //alert is an overloaded method
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DBAppointment.DeleteAppointment(AppointmentSelected);

        }

        //it will return a boolean if there's a button inside optional container and if it is the ok button
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

        Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "You have successfully deleted the appointment with ID  " + AppointmentSelected +  "  and Type  " + AppointmentType ); //alert is an overloaded method
        Optional<ButtonType> result1 = alert1.showAndWait();
    }

    @FXML
    void OnActionUpdateAppointment(ActionEvent event) throws IOException {
        AppointmentToEdit = AppointmentTableView.getSelectionModel().getSelectedItem();
        if (AppointmentToEdit == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No appointment selected."); //alert is an overloaded method
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/ModifyAppointmentScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
// sets all the objects returned from GetAllAppointments(), which is an OL of appointment objects.
        try {
            AppointmentTableView.setItems(DBAppointment.GetAllAppointments()); //set all appointments according to variable name of constructor
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    }
}
    //PropertyValueFactory<Appointment, Integer> apptIDFactory = new PropertyValueFactory<>("AppointmentID");
//        AppointmentIDCol.setCellValueFactory(apptIDFactory);
//
//        PropertyValueFactory<Appointment, String> apptTitleFactory = new PropertyValueFactory<>("Title");
//        titleCol.setCellValueFactory(apptTitleFactory);
//
//        PropertyValueFactory<Appointment, String> apptDescriptionFactory = new PropertyValueFactory<>("Description");
//        DescriptionCol.setCellValueFactory(apptDescriptionFactory);
//
//        PropertyValueFactory<Appointment, String> apptLocationFactory = new PropertyValueFactory<>("Location");
//        locationCol.setCellValueFactory(apptLocationFactory);
//
//        PropertyValueFactory<Appointment, Integer> apptContactFactory = new PropertyValueFactory<>("ContactID");
//        ContactCol.setCellValueFactory(apptContactFactory);
//
//        PropertyValueFactory<Appointment, String> apptTypeFactory = new PropertyValueFactory<>("Type");
//        TypeCol.setCellValueFactory(apptTypeFactory);
//
//        PropertyValueFactory<Appointment, String> apptStartFactory = new PropertyValueFactory<>("StartTime");
//        startCol.setCellValueFactory(apptStartFactory); //fixme being a string can cause issues
//
//        PropertyValueFactory<Appointment, String> apptEndFactory = new PropertyValueFactory<>("EndTime");
//        endCol.setCellValueFactory(apptEndFactory); //fixme being a string can cause issues
//
//        PropertyValueFactory<Appointment, Integer> apptCustomerID = new PropertyValueFactory<>("CustomerID");
//        customerCol.setCellValueFactory(apptCustomerID);
