package Controller;

import DAOAcess.DBCountries;
import DAOAcess.DBCustomer;
import Model.Countries;
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


    @FXML
    void OnActionSaveCustomer(ActionEvent event) throws SQLException, IOException {//exception just in case this FXML file doesn't exist , input output error

        String name = ModifyName.getText();
        String address = ModifyAddress.getText();
        String postalCode = ModifyPostal.getText();
        String phone = ModifyPhone.getText();
        String country = ModifyCountry.getValue();
        String division = ModifyDivision.getValue();
        Integer customerID = Integer.parseInt(ModifyID.getText());

        if (name.isBlank() || address.isBlank() || postalCode.isBlank() ||
                phone.isBlank() || country.isBlank() || division.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure all fields are completed before saving customer");
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



    } //back button
    @FXML
    void OnActionMainScreen(ActionEvent event) throws IOException { //exception just in case this FXML file doesn't exist , input output error
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Any changes made will be lost. Proceed?"); //alert is an overloaded method
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene)); //load the scene to the stage
            stage.show(); //this will bring us to the appropriate screen (main Menu
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ModifyCountry.setPromptText("Choose a Country");
            ModifyDivision.setPromptText("Choose a State/Province"); //fixme prompt text not showing
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

        ModifyCountry.valueProperty().addListener((obs, OldValue, NewValue) -> {
            if (NewValue == null) {
                ModifyDivision.getItems().clear();
                ModifyDivision.setPromptText("Choose a State/Province");

            } else {
                try {
                    ModifyDivision.setPromptText("Choose a State/Province");
                    ModifyDivision.setItems(DBCustomer.getDivisions(ModifyCountry.getValue()));
                    ModifyDivision.setPromptText("Choose a State/Province");
                } catch (SQLException e) {
                    e.printStackTrace();

                }
            }
        });}}