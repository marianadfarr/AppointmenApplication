package DAOAcess;
import DBConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This gets data for the user on the DB
 */
public class DBUser {

    /**Find all User IDs from database to populate combo boxes
     * @return all user IDs for combo boxes
     * @throws SQLException if database fails to load.
     */
    public static ObservableList<Integer> getAllUserIDs() throws SQLException {
        ObservableList<Integer> AllUserIDs = FXCollections.observableArrayList();

        String sql = "SELECT User_ID FROM Users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet UserResults = ps.executeQuery();

        while (UserResults.next()) {
            AllUserIDs.add(UserResults.getInt("User_ID"));
        }
        return AllUserIDs;

    }
}
