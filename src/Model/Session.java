
package Model;

import DBConnection.JDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Session {

    private static User loggedOnUser;
    private static Locale userLocale;
    private  static ZoneId userTimeZone;

    /**
     * attemptLogon
     * Takes input username and password and checks them against DB to logon
     *
     * @param userName     user input username
     * @param userPassword user input password
     * @return Boolean indicating a successful logon
     * @throws SQLException
     */
    public static boolean LogonAttempt(String userName, String userPassword) throws SQLException {
        Connection conn = JDBC.getConnection();
        PreparedStatement sqlCommand = conn.prepareStatement("SELECT * FROM users WHERE " +
                "User_Name = ? AND Password = ?");
        sqlCommand.setString(1, userName);
        sqlCommand.setString(2, userPassword);
        ResultSet loginresult = sqlCommand.executeQuery();
        if (!loginresult.next()) {

            return false;

            //Log failed login attempt

        } else {
            loggedOnUser = new User(loginresult.getString("User_Name"), loginresult.getInt("User_ID"));
            userLocale = Locale.getDefault();
            userTimeZone = ZoneId.systemDefault();
            System.out.println("Logged in");
            return true;

        }


    }

    /**
     * Getter - user Object
     *
     * @return logged on user object
     */
    public static User getLoggedOnUser() {
        return loggedOnUser;
    }

    /**
     * Getter - user Locale
     *
     * @return locale of logged on user
     */
    public static Locale getUserLocale() {
        return userLocale;

    }

    /**
     * Getter - user Time Zone
     *
     * @return logged on user time zone
     */
    public static ZoneId getUserTimeZone() {
        return userTimeZone;
    }
}
