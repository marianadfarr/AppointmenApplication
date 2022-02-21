package Controller;


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

import java.util.Optional;
import java.util.ResourceBundle;

/**
 *Logic for Customer Screen page.
 */

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
    /** This will select the customer to edit (object).
     */
    private static Customer CustomerToEdit;
    /**
     * This will return the customer to edit (object).
     */

    public static Customer getCustomerToEdit() {

        return CustomerToEdit;
    }

    /** This will select the Customer To Delete  based on the Customer's Customer ID
     *
     */
    private static int CustomerToDelete;


    /** Switches screen to create customer screen.
     * @param event Clicking the Create Customer Button leads us to the Add Customer screen
     * @throws IOException if no such file exists
     */
    @FXML
    void OnActionAddCustomer(ActionEvent event) throws IOException { //exception just in case this FXML file doesn't exist , input output error
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Selects a customer object based on user selection. Then directs user to Edit Customer screen if selection is not null.
     * @param event Clicking the Edit Customer button
     * @throws IOException if no such file exists
     */
    @FXML
    void OnActionEditCustomer(ActionEvent event) throws IOException {
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

    /** Delete customer button click
     * Deletes selected customer if no related appointments are found. Otherwise, throws an error message.
     * @param event Delete button
     * @throws IOException if no such file exists
     * @throws SQLException if DB failed to delete the customer.
     */
    @FXML
    void OnActionDeleteCustomer(ActionEvent event) throws IOException, SQLException {
        CustomerToDelete = CustomerTableView.getSelectionModel().getSelectedItem().getCustomerID();
        if (FindCustomerAppointments(CustomerToDelete)) { //if there are ANY appointments in the Customer Appointments list, return error
            Alert alert = new Alert(Alert.AlertType.ERROR, " You cannot delete a customer with related appointments. Delete all related appointments first & try again");
            Optional<ButtonType> result = alert.showAndWait();
        }

        else  {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, " This will delete the customer. Proceed?" );
            Optional<ButtonType> result = alert.showAndWait();
              DBCustomer.DeleteCustomer(CustomerToDelete);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }


    /**Find out if customer has any associated appointments.
     * @param CustomerID ID of customer.
     * @return True if they have appointments, false otherwise.
     * @throws SQLException if DB failed to grab appointments
     */
    public Boolean FindCustomerAppointments(Integer CustomerID) throws SQLException {
        ObservableList<Appointment> CustomerAppointments = DBCustomer.getAllAppointmentsForCustomer(CustomerID);
        if (CustomerAppointments.isEmpty()) {
            return false;
        } else {
            return true;
        }


    }

    /**User clicks the main menu button
     * @param event Clicking the Main menu button
     * @throws IOException if no such file exists
     */
    @FXML
    void OnActionMainMenu(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Sets all the objects returned from GetAllCustomers(), which is an OL of customer objects.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
