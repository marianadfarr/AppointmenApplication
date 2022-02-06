package Controller;

import DAOAcess.DBCountries;
import DAOAcess.DBCustomer;
import Model.Countries;
import Model.FirstLevelDivisions;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class AddCustomerScreen implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private ComboBox<String> CountryComboBox;

    @FXML
    private ComboBox<String> StatesComboBox;

    @FXML
    private TextField AddAddress;

    @FXML
    private TextField AddName;

    @FXML
    private TextField AddPhone;

    @FXML
    private TextField AddPostal;


    @FXML
    void OnActionSave(ActionEvent event) throws IOException, SQLException {
        //exception just in case this FXML file doesn't exist , input output error

        // INPUT VALIDATION - check for nulls
        String name = AddName.getText();
        String address = AddAddress.getText();
        String postalCode = AddPostal.getText();
        String phone = AddPhone.getText();
        String country = CountryComboBox.getValue();
        String division = StatesComboBox.getValue();

        if (name.isBlank() || address.isBlank() || postalCode.isBlank() || phone.isBlank() || country.isBlank() || division.isBlank()) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure all fields are completed before saving customer");
            alert.showAndWait();
            return;

        }

        // Add customer to DB
        Boolean CustomerAdded = DBCustomer.CreateCustomer(name, address, postalCode, phone, DBCustomer.getDivisionID(division));

        // notify user we successfully added to DB, or if there was an error.
        if (CustomerAdded) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer was added. Going to all Customer list");
            Optional<ButtonType> result = alert.showAndWait();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Customer was not saved. Try again"); //alert is an overloaded method
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    @FXML
        //fixme or do away with this
    void OnActionClearAll(ActionEvent event) throws IOException, SQLException {
        CountryComboBox.getSelectionModel().clearSelection();
        StatesComboBox.getSelectionModel().clearSelection();
        AddName.clear();
        AddAddress.clear();
        AddPostal.clear();
        AddPhone.clear();
        CountryComboBox.setPromptText("Choose a Country");
        StatesComboBox.setPromptText("Choose a State/Province");


    }


    @FXML
    void OnActionBack(ActionEvent event) throws IOException { //exception just in case this FXML file doesn't exist , input output error
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Customer will not be saved. Proceed?"); //alert is an overloaded method
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene)); //load the scene to the stage
            stage.show(); //this will bring us to the appropriate screen (main Menu
        }
    }








    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CountryComboBox.setPromptText("Choose a Country");
        StatesComboBox.setPromptText("Choose a State/Province");
        //set all Countries in combo box. set divisions and states depending on country selection
            try {
                CountryComboBox.setItems(DBCountries.getAllCountries());
                StatesComboBox.setPromptText("Choose a State/Province");//set countries
            } catch (SQLException e) {
                e.printStackTrace();
            }


          CountryComboBox.valueProperty().addListener((obs, OldValue, NewValue) -> {
            if (NewValue == null) {//fixme prompt text only works first time? add to outside the initializer
                StatesComboBox.setPromptText("Choose a State/Province");
                StatesComboBox.getItems().clear();



            } else {
                try {
                    StatesComboBox.setPromptText("Choose a State/Province");
                    StatesComboBox.setItems(DBCustomer.getDivisions(CountryComboBox.getValue()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        )
          ;
    }
}
