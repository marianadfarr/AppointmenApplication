package main;

import DAOAcess.DBCountries;
import DBConnection.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        stage.setTitle("AppointmentKeeper Application");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();

    }

    public static void main(String[] args) {
        JDBC.openConnection();//only call this once, use get connection for other
//        DBCountries.CheckDateConversion();
        launch(args);
    }


}

//    JDBC.closeConnection(); //only call this at the end