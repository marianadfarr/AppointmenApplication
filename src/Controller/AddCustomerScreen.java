package Controller;

import DAOAcess.DBCountries;
import DAOAcess.DBCustomer;
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
 *This screen handles the logic for the Add Customer Screen page.
 */

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

    /**Saving a new customer object.
     * @param event Clicking the save button. Checks for blank or unselected inputs. If all fields have been entered,
     *             a new customer object is added to the Database. The user is alerted of the addition and is brought back to the customer screen.
     * @throws IOException if no such file exists.
     * @throws SQLException if database fails to save new customer.
     */
    @FXML
    void OnActionSave(ActionEvent event) throws IOException, SQLException {
        //get user input
        String name = AddName.getText();
        String address = AddAddress.getText();
        String postalCode = AddPostal.getText();
        String phone = AddPhone.getText();
        String country = CountryComboBox.getValue();
        String division = StatesComboBox.getValue();

        if (name.isBlank() || address.isBlank() || postalCode.isBlank() || phone.isBlank() || country==null || division == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please ensure all fields are filled in or selected");
            alert.showAndWait();
            return;

        }

        // If all items are valid, add customer to database
        Boolean CustomerAdded = DBCustomer.CreateCustomer(name, address, postalCode, phone, DBCustomer.getDivisionID(division));

        // Notify user we successfully added to database.
        if (CustomerAdded) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer was added. Going to all Customer list");
            Optional<ButtonType> result1 = alert.showAndWait();

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }


    /** Back button click.
     * @param event Clicking the back button brings user back to the Customer Screen.
     * @throws IOException if no such file exists
     */

    @FXML
    void OnActionBack(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Customer will not be saved. Proceed?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomerScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**Sets up all countries, and combo boxes' prompts.
     * Lambda expression below - listener for country changing which changes choices in division box.
     * @param url
     * @param resourceBundle
     */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CountryComboBox.setPromptText("Choose a Country");
        StatesComboBox.setPromptText("Choose a State/Province");
            try {
                CountryComboBox.setItems(DBCountries.getAllCountries());
            } catch (SQLException e) {
                e.printStackTrace();
            }

/**
 * Lambda expression
 * Add a listener for the combo box for country changing, which changes the choices in the division combo box accordingly.
 */
          CountryComboBox.valueProperty().addListener((observableValue, OldValue, NewValue) -> {
            if (NewValue == null) {
                StatesComboBox.setValue(null);


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
