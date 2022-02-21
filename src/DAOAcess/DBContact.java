package DAOAcess;

import DBConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *This gets data for the Contacts on the database.
 * We do not need to create, update, or delete any countries.
 */
public class DBContact {


    /**This gets data for the Contacts on the database.
     * @return allContactNames observable list of contact names
     * @throws SQLException if DB could not connect
     */
    public static ObservableList<String> getAllContactNames() throws SQLException {

        ObservableList<String> allContactNames = FXCollections.observableArrayList();

        String sql = "SELECT Contact_Name FROM Contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet contactNames = ps.executeQuery();

        while (contactNames.next()) {
            allContactNames.add(contactNames.getString("Contact_Name"));
        }
        return allContactNames;
    }


    /**Given a contact name, find its corresponding ID
     * @param contactName Contact Name
     * @return ContactID Corresponding contact ID
     * @throws SQLException if no such Contact name exists in database.
     */
    public static Integer findContactID(String contactName) throws SQLException {
        int ContactID = 0;
        String sql = "SELECT Contact_ID FROM Contacts WHERE Contact_Name = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, contactName);
        ResultSet contactResult = ps.executeQuery();

        while (contactResult.next()) {
            ContactID = contactResult.getInt("Contact_ID");
        }
        return ContactID;
    }
}

