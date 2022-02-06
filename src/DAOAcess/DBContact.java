package DAOAcess;

import DBConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContact {
//CRUD
    //do not create any

    public static ObservableList<String> getAllContactIDs() throws SQLException {

        ObservableList<String> allContactIDs = FXCollections.observableArrayList();

        String sql = "SELECT Contact_ID FROM Contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet contactIDResults = ps.executeQuery();

        while (contactIDResults.next()) {
            allContactIDs.add(contactIDResults.getString("Contact_ID"));
        }
        return allContactIDs;


    }

    public static ObservableList<String> getAllContactNames() throws SQLException {

        ObservableList<String> allContactNames = FXCollections.observableArrayList();

        String sql = "SELECT Contact_Name FROM Contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet Countryresults = ps.executeQuery();

        while (Countryresults.next()) {
            allContactNames.add(Countryresults.getString("Contact_Name"));
        }
        return allContactNames;
    }

    public static Integer findContactID(String contactName) throws SQLException {
        int ContactID = 0;
        String sql = "SELECT Contact_ID FROM Contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, contactName); //this is for the questionmark
        ResultSet contactResult = ps.executeQuery();
        //double check if this works fixme because i coded it


        while (contactResult.next()) {
            ContactID = contactResult.getInt("Contact_ID");
        }
        return ContactID;
    }
}

