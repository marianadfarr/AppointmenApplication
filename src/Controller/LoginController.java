package Controller;

import DAOAcess.DBAppointment;
import Model.Appointment;
import Model.Session;
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

import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * This controller handles the logic for the login page.
 */

public class LoginController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML
    private Button EnterButton;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Label PasswordLabel;

    @FXML
    private Label TimeZoneInsert;

    @FXML
    private Label TimeZoneLabel;

    @FXML
    private Label TitleLabel;

    @FXML
    private TextField UsernameTxt;

    @FXML
    private Label usernameLabel;

    /** Authenticate user by taking in username and password strings.
     * Log any attempts to log in, their username and whether login was successful or not, in login_activity.txt.
     * If login was successful, alert of upcoming appointments or lack-thereof.
     * Otherwise, inform user login was not successful in their machine's language.
     * @param event clicking the login button
     * @throws IOException if no such file exists
     * @throws SQLException if database failed to grab appointments within 15 minutes.
     */
    @FXML
    void OnActionAuthenticate(ActionEvent event) throws IOException, SQLException {
        String username = UsernameTxt.getText();
        String password = String.valueOf(PasswordField.getText());

        boolean login = Session.LogonAttempt(username, password);

        //Log any attempts to log in, their username and whether login was successful or not
       Audit.Logger.TrackLogin(username, login);

     //If login was successful, alert of upcoming appointments or lack-thereof.
        if (login) {
            ObservableList<Appointment> Apptin15min = DBAppointment.GetApptIn15();
            if (Apptin15min.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, " Welcome, Test User. You have no upcoming appointments within 15 min.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK);
            } else {
                for (Appointment appointment : Apptin15min) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have an appointment within 15 minutes of ID " +appointment.getAppointmentID() + " at " + appointment.getStartDatetime());
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK);

                }

            }
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
//If login is not successful, give error message in appropriate language.

        else{
                ResourceBundle rb = ResourceBundle.getBundle("Language", Locale.getDefault());
                ButtonType clickOkay = new ButtonType(rb.getString("EnterButton"), ButtonBar.ButtonData.OK_DONE);
                Alert loginFailed = new Alert(Alert.AlertType.WARNING, rb.getString("loginFailed"),
                        clickOkay);
                loginFailed.showAndWait();

            }
        }



    /**
     Set login page to appropriate language
     */
        @Override
        public void initialize (URL location, ResourceBundle resourceBundle){

        ResourceBundle rb = ResourceBundle.getBundle("Language", Locale.getDefault());
        TitleLabel.setText(rb.getString("TitleLabel"));
        usernameLabel.setText(rb.getString("usernameLabel"));
        PasswordLabel.setText(rb.getString("PasswordLabel"));
        EnterButton.setText(rb.getString("EnterButton"));
        TimeZoneInsert.setText(ZoneId.systemDefault().toString());
        TimeZoneLabel.setText(rb.getString("TimeZoneLabel"));




        }
    }
