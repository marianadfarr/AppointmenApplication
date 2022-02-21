package Controller;


import DAOAcess.DBCustomer;
import Model.Customer;
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
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Provides logic to the Edit Customer screen page.
 */
public class ModifyCustomerScreenController implements Initializable {
    Stage stage;
    Parent scene;

    private Customer CustomerToEdit;
    @FXML
    private Button CancelButton;

    @FXML
    private TextField ModifyAddress;

    @FXML
    private ComboBox<String> ModifyCountry;

    @FXML
    private ComboBox<String> ModifyDivision;

    @FXML
    private TextField ModifyID;

    @FXML
    private TextField ModifyName;

    @FXML
    private TextField ModifyPhone;

    @FXML
    private TextField ModifyPostal;

    @FXML
    private Button SaveButton;

    /**
     * Saves a new customer to the database.
     * @param event clicking the save button.
     * Checks for blank or unselected inputs, and if all have been entered, a customer object is added to the database.
     * User is then directed to the customer page.
     *
     *
     * @throws SQLException if customer was not saved to database
     * @throws IOException if no such file exists
     */
    @FXML
    void OnActionSaveCustomer(ActionEvent event) throws SQLException, IOException {

        String name = ModifyName.getText();
        String address = ModifyAddress.getText();
        String postalCode = ModifyPostal.getText();
        String phone = ModifyPhone.getText();
        String country = ModifyCountry.getValue();
        String division = ModifyDivision.getValue();
        Integer customerID = Integer.parseInt(ModifyID.getText());

        if (name.isBlank() || address.isBlank() || postalCode.isBlank() || phone.isBlank() ||country==null || division == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure all fields are completed or selected before saving customer");
            alert.showAndWait();
            return;

        }


        Boolean customerUpdated = DBCustomer.updateCustomer(division, name, address, postalCode, phone, customerID);

        if (customerUpdated) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Customer was not updated");
            alert.showAndWait();
        }



    }

    /**Directs user to the main screen.
     * @param event Clicking the main menu screen.
     * User is alerted any changes made will be lost.
     * After they confirm, user is directed to the customer screen.
     * @throws IOException if no such files exists
     */
    @FXML
    void OnActionMainScreen(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Any changes made will be lost. Proceed?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Sets all data from the selected user onto the screen.
     Lambda expression: Add a listener for the combo box for country changing,
     which changes the choices in the division combo box accordingly.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ModifyCountry.setPromptText("Choose a Country");
            ModifyDivision.setPromptText("Choose a State/Province");
            CustomerToEdit = CustomerScreenController.getCustomerToEdit();
            ModifyID.setText(String.valueOf(CustomerToEdit.getCustomerID()));
            ModifyName.setText(CustomerToEdit.getCustomerName());
            ModifyPhone.setText(CustomerToEdit.getCustomerPhone());
            ModifyPostal.setText(CustomerToEdit.getCustomerpostalCode());
            ModifyAddress.setText(CustomerToEdit.getCustomerAddress());
            ModifyCountry.setItems(DBCustomer.GetAllCountries());
            ModifyCountry.getSelectionModel().select(CustomerToEdit.getCountryName());
            ModifyDivision.setItems(DBCustomer.getDivisions(ModifyCountry.getValue())); //perfect
            ModifyDivision.getSelectionModel().select(CustomerToEdit.getDivisionName());
        }


            catch (SQLException e) {
                e.printStackTrace();
            }
/**
Lambda expression here- listener for the combo box for country changing,
 which changes the choices in the division combo box accordingly.
 */
        ModifyCountry.valueProperty().addListener((observableValue, OldValue, NewValue) -> {
            if (NewValue == null) {
                ModifyDivision.setValue(null);
                ModifyDivision.setPromptText("Choose a State/Province");

            } else {
                try {
                    ModifyDivision.setPromptText("Choose a State/Province");
                    ModifyDivision.setItems(DBCustomer.getDivisions(ModifyCountry.getValue()));

                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
        });}}


