package Controller;

import DAOAcess.DBAppointment;
import DAOAcess.DBCustomer;
import Model.Appointment;
import Model.Customer;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;


public class CustomerScreenController implements Initializable {
    @FXML
    private TableView<Customer> CustomerTableView;

    @FXML
    private TableColumn<Customer, String> AddressCol;

    @FXML
    private TableColumn<Customer, String> CountryCol;

    @FXML
    private TableColumn<Customer, Integer> CustomerIDCol;

    @FXML
    private TableColumn<Customer, String> CustomerNameCol;

    @FXML
    private TableColumn<Customer, String> PhoneNumberCol;

    @FXML
    private TableColumn<Customer, String> PostalCodeCol;

    @FXML
    private TableColumn<Customer, String > StateCol;

    Stage stage;
    Parent scene;

    private static Customer CustomerToEdit;

    public static Customer getCustomerToEdit() {

        return CustomerToEdit;
    }
    private static int CustomerToDelete;

    public static int getCustomerToDelete() {
        return CustomerToDelete;
    }

    @FXML
    void OnActionAddCustomer(ActionEvent event) throws IOException { //exception just in case this FXML file doesn't exist , input output error
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void OnActionEditCustomer(ActionEvent event) throws IOException {

        //exception just in case this FXML file doesn't exist , input output error
       CustomerToEdit = CustomerTableView.getSelectionModel().getSelectedItem();

        if (CustomerToEdit == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Select a customer to modify");
            alert.showAndWait();
            return;

        }
        else {


        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/ModifyCustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        }
    }
    @FXML
    void OnActionDeleteCustomer(ActionEvent event) throws IOException, SQLException {
        CustomerToDelete = CustomerTableView.getSelectionModel().getSelectedItem().getCustomerID();
        if (FindCustomerAppointments(CustomerToDelete)) { //if there are ANY appointments in the Customer Appointments list, return error
            Alert alert = new Alert(Alert.AlertType.ERROR, " You cannot delete a customer with associated appointments."); //alert is an overloaded method
            Optional<ButtonType> result = alert.showAndWait();
        }

        else  {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " This will delete the customer. Proceed?" ); //alert is an overloaded method
            Optional<ButtonType> result = alert.showAndWait();
              DBCustomer.DeleteCustomer(CustomerToDelete);
                //it will return a boolean if there's a button inside optional container and if it is the ok button
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }


    public Boolean FindCustomerAppointments(Integer CustomerID) throws SQLException {
        ObservableList<Appointment> CustomerAppointments = DBCustomer.getAllAppointmentsForCustomer(CustomerID);
        // for each possible appointment for that custumer on the same date, evaluate:
        // if conflictApptStart is before the new appointment we want to make  AND conflictApptEnd is after newApptStart(starts before ends after)
        // if conflictApptStart is before newApptEnd (if appointment starts before another ends) AND conflictApptStart after newApptStart (startime anywhere in appt)
        // if endtime is before end and endtime is after start (endtime falls anywhere in appt)
        if (CustomerAppointments.isEmpty()) {
            return false;
        } else {
            return true;
        }


    }
    @FXML
    void OnActionMainMenu(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
// sets all the objects returned from GetAllCUstomers(), which is an OL of customer objects.
       CustomerTableView.setItems(DBCustomer.GetAllCustomers()); //set all customers according to variable name of constructor
        CustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        CustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        AddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        PostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("customerpostalCode"));
        PhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        CountryCol.setCellValueFactory(new PropertyValueFactory<>("countryName")); //has to match getter in capitalization
        StateCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

    }
}
