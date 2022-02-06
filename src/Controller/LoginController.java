package Controller;

import Model.Session;
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
import java.util.ResourceBundle;
import java.util.logging.Logger;


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

    @FXML
    void OnActionAuthenticate(ActionEvent event) throws IOException, SQLException {
        String userName = UsernameTxt.getText();
        String password = String.valueOf(PasswordField.getText());


        boolean login = Session.LogonAttempt(userName, password);
        //  Logger.auditLogin(userName, login); fixme add


        if (login) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            ResourceBundle rb = ResourceBundle.getBundle("Language", Locale.getDefault());
            ButtonType clickOkay = new ButtonType(rb.getString("EnterButton"), ButtonBar.ButtonData.OK_DONE);
            Alert failedLogin = new Alert(Alert.AlertType.WARNING, rb.getString("loginFailed"),
                    clickOkay);
            failedLogin.showAndWait();

        }
    }

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
