package Controller;

import DAOAcess.DBCountries;
import Model.Countries;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class countriestest implements Initializable {
    @FXML
    private TableView<Countries> DataTable;

    @FXML
    private TableColumn<Countries, String> NameCol;

    @FXML
    private TableColumn<Countries, Integer> idCol;

    @FXML
    public void showMe(ActionEvent event) { //enhanced for loop
        ObservableList<Countries> CountryList = DBCountries.GetAllCountries();
        for (Countries c : CountryList) {
            System.out.println(" Country ID " + c.getCountryID() + " Country Name " + c.getCountryName());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}