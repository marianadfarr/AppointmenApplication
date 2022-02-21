
package Model;

import DBConnection.JDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.sql.SQLException;

import java.time.ZoneId;
import java.util.*;

public class Session {

    private static User currentUser;
    private static Locale userLocale;
    private  static ZoneId userTimeZone;


    /**
     Takes input username and password and checks them against DB to logon
     * @param username username
     * @param userpassword user password
     * @return boolean true/false if logged in or not
     * @throws SQLException DB connection fails
     *
     *
     *     */

    public static boolean LogonAttempt(String username, String userpassword) throws SQLException {
        Connection connection = JDBC.getConnection();
        PreparedStatement sqlCommand = connection.prepareStatement("SELECT * FROM Users WHERE User_Name = ? AND Password = ?");
        sqlCommand.setString(1, username);
        sqlCommand.setString(2, userpassword);
        ResultSet loginresult = sqlCommand.executeQuery();
        if (!loginresult.next()) {

            return false;


        } else {
            currentUser = new User(loginresult.getString("User_Name"), loginresult.getInt("User_ID"));
            userLocale = Locale.getDefault();
            userTimeZone = ZoneId.systemDefault();
            System.out.println("Logged in");
            return true;

        }


    }

    /**
     *
     * @return user's time zone
     */
    public static ZoneId getUserTimeZone() {

        return userTimeZone;
    }

    /**
     *
     * @return current user
     */

    public static User getCurrentUser() {
        ;
        return currentUser;
    }


    /**
     *
     * @return user Locale
     */
    public static Locale getUserLocale() {
        return userLocale;

    }



}
