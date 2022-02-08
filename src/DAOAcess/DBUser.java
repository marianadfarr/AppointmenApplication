package DAOAcess;

import DBConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUser {
    //do not create any
//not sure if needed
    //Create
    //Read
    //Update
    //delete

    //get all user IDs
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
