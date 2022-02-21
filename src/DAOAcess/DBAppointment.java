package DAOAcess;

import DBConnection.JDBC;
import Model.Appointment;
import Model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


import static javafx.collections.FXCollections.observableArrayList;

/**
 * *This gets data for the Appointments on the database.
 */
public class DBAppointment {
    /**
     *
     * @param title Title of appointment
     * @param description description of appointment
     * @param location location of appointment
     * @param type type of appointment
     * @param StartDateTime start timestamp of appointment
     * @param EndDateTime end date timestamp of appointment
     * @param createdBy created by - user who created appointment
     * @param Updatedby updated by-  user who last updated appointment
     * @param customerID - ID of customer
     * @param userID - user ID
     * @param contactID - contact ID
     * @return boolean of success or failure
     * @throws SQLException if appointment was not added to database.
     * This has more columns than what we can see in main screen/appointment view.
     */

    public static Boolean CreateAppointment(String title, String description, String location, String type, Timestamp StartDateTime, Timestamp EndDateTime, String createdBy, String Updatedby, int customerID, int userID, int contactID) throws SQLException {

        String sqlsca = "INSERT into Appointments VALUES(NULL, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?)";
        PreparedStatement psti = JDBC.getConnection().prepareStatement(sqlsca, Statement.RETURN_GENERATED_KEYS);
        //format start and end times
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        psti.setString(1, title);
        psti.setString(2, description);
        psti.setString(3, location);
        psti.setString(4, type);
        psti.setTimestamp(5, StartDateTime); //setting timestamps
        psti.setTimestamp(6, EndDateTime);
        psti.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(DTF)); //createdtime- make NOW into UTC, not an input
        psti.setString(8, createdBy);
        psti.setString(9, ZonedDateTime.now(ZoneOffset.UTC).format(DTF)); //Lastupdated- make NOW into UTC, not an input
        psti.setString(10, Updatedby);
        psti.setInt(11, customerID);
        psti.setInt(12, userID);
        psti.setInt(13, contactID);

        try {
            psti.execute();
            ResultSet resultset = psti.getGeneratedKeys();
            resultset.next();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    /** Gets all appointments from the database
     * @return AllAppointments observable list
     * @throws SQLException if database failed to get appointments
     */
    public static ObservableList<Appointment> GetAllAppointments() throws SQLException {
        ObservableList<Appointment> AllAppointmentsList = observableArrayList();
        try {
            String sql = "SELECT * FROM appointments JOIN contacts WHERE appointments.Contact_ID = contacts.Contact_ID"; // makes query for DB
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //db connection,prepared statement
            ResultSet allApptResults = ps.executeQuery(); // gets result set, on execute query
            while (allApptResults.next()) { //loops through result set using next
                int appointmentID = allApptResults.getInt("Appointment_ID");
                String title = allApptResults.getString("Title");
                String description = allApptResults.getString("Description");
                String location = allApptResults.getString("Location");
                String type = allApptResults.getString("Type");
                //We don't need the following 4 fields displayed. But they are part of the object
                Timestamp createdDatetime = allApptResults.getTimestamp("Create_Date");
                String createdBy = allApptResults.getString("Created_By");
                Timestamp lastUpdated = allApptResults.getTimestamp("Last_Update");
                String updatedBy = allApptResults.getString("Last_Updated_By");
                //End fields that will not show up on tableview
                String contactName = allApptResults.getString("Contact_Name");
                int contactID = allApptResults.getInt("Contact_ID");
                int customerID = allApptResults.getInt("Customer_ID");
                int userID = allApptResults.getInt("User_ID");
                Timestamp startTime = allApptResults.getTimestamp("Start");
                Timestamp endTime = allApptResults.getTimestamp("End");


                Appointment newAppointment = new Appointment(appointmentID, title, description, location, type, startTime, endTime, createdDatetime, createdBy, lastUpdated, updatedBy, customerID, userID, contactID, contactName);


                AllAppointmentsList.add(newAppointment);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return AllAppointmentsList;
    }

    /** Gets current month's appointments
     * @return All Month Appointments Observable list, for the current month.
     * @throws SQLException if DB fails to get appointments
     */
    public static ObservableList<Appointment> GetAllMonthAppointments() throws SQLException {
        ObservableList<Appointment> AllMonthAppts = observableArrayList(); // Create list to add each object to
        try {

            String sql = "SELECT * FROM Appointments join Contacts WHERE appointments.Contact_ID = contacts.Contact_ID AND Start >= '2022-02-01 00:00:01' AND End <= '2022-02-28 23:59:59'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet MonthApptResults = ps.executeQuery();
            while (MonthApptResults.next()) {
                int appointmentID = MonthApptResults.getInt("Appointment_ID");
                String title = MonthApptResults.getString("Title");
                String description = MonthApptResults.getString("Description");
                String location = MonthApptResults.getString("Location");
                String type = MonthApptResults.getString("Type");
                //We do not need the following 4 fields displayed, but they are part of the object
                Timestamp createdDatetime = MonthApptResults.getTimestamp("Create_Date");
                String createdBy = MonthApptResults.getString("Created_By");
                Timestamp lastUpdated = MonthApptResults.getTimestamp("Last_Update"); //Timestamps automatically show system time.
                String updatedBy = MonthApptResults.getString("Last_Updated_By");
                //End fields that will not show up on tableview
                String contactName = MonthApptResults.getString("Contact_Name");
                int contactID = MonthApptResults.getInt("Contact_ID");
                int customerID = MonthApptResults.getInt("Customer_ID");
                int userID = MonthApptResults.getInt("User_ID");
                Timestamp startTime = MonthApptResults.getTimestamp("Start");
                Timestamp endTime = MonthApptResults.getTimestamp("End");


                //Takes all data for one loop and makes new object as per constructor defined in model class.
                Appointment newAppointment = new Appointment(appointmentID, title, description, location, type, startTime, endTime, createdDatetime, createdBy, lastUpdated, updatedBy, customerID, userID, contactID, contactName);
//
                //add new object to list

                AllMonthAppts.add(newAppointment);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //return all appointment list
        return AllMonthAppts;

    }

    /** Gets current week appointments
     * @return All Week Appointments OL - for current week
     * @throws SQLException if DB fails to get appointments
     */
    public static ObservableList<Appointment> GetAllWeekAppointments() throws SQLException {
        ObservableList<Appointment> AllWeekAppts = observableArrayList();
        try {

            String sql = "SELECT * FROM Appointments join Contacts WHERE appointments.Contact_ID = contacts.Contact_ID AND Start >= '2022-02-13 00:00:01' AND End <= '2022-02-20 23:59:59'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet MonthApptResults = ps.executeQuery();
            while (MonthApptResults.next()) {
                int appointmentID = MonthApptResults.getInt("Appointment_ID");
                String title = MonthApptResults.getString("Title");
                String description = MonthApptResults.getString("Description");
                String location = MonthApptResults.getString("Location");
                String type = MonthApptResults.getString("Type");
                //We do not need the following 4 fields displayed on table view, but they are part of the object
                Timestamp createdDatetime = MonthApptResults.getTimestamp("Create_Date");
                String createdBy = MonthApptResults.getString("Created_By");
                Timestamp lastUpdated = MonthApptResults.getTimestamp("Last_Update");
                String updatedBy = MonthApptResults.getString("Last_Updated_By");
                //end fields that will not show up on tableview
                String contactName = MonthApptResults.getString("Contact_Name");
                int contactID = MonthApptResults.getInt("Contact_ID");
                int customerID = MonthApptResults.getInt("Customer_ID");
                int userID = MonthApptResults.getInt("User_ID");
                Timestamp startTime = MonthApptResults.getTimestamp("Start");
                Timestamp endTime = MonthApptResults.getTimestamp("End");


                Appointment newAppointment = new Appointment(appointmentID, title, description, location, type, startTime, endTime, createdDatetime, createdBy, lastUpdated, updatedBy, customerID, userID, contactID, contactName);
//

                AllWeekAppts.add(newAppointment);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return AllWeekAppts;

    }


    /**Select all appointments from specific customers
     * Finds all appointments for a specific customer
     * Use list to find any conflict in appointment times.
     * This is to find out any overlaps in appointments.
     * @param CustomerID ID of customer
     * @return filtered appointment OL.
     * @throws SQLException if no such customer was found
     */
    public static ObservableList<Appointment> getCustomerAppointments(
             Integer CustomerID) throws SQLException {

        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
        String sql = "Select * FROM Appointments JOIN Contacts ON appointments.Contact_ID= Contacts.Contact_ID " +
                "WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        //get all customer appointments, compare timestamps later.
        ps.setInt(1, CustomerID);

        ResultSet results = ps.executeQuery();

        while (results.next()) {

            Integer appointmentID = results.getInt("Appointment_ID");
            String title = results.getString("Title");
            String description = results.getString("Description");
            String location = results.getString("Location");
            String type = results.getString("Type");
            Timestamp startDateTime = results.getTimestamp("Start");
            Timestamp endDateTime = results.getTimestamp("End");
            Timestamp createdDate = results.getTimestamp("Create_Date");
            String createdBy = results.getString("Created_by");
            Timestamp lastUpdateDateTime = results.getTimestamp("Last_Update");
            String lastUpdatedBy = results.getString("Last_Updated_By");
            Integer customerID = results.getInt("Customer_ID");
            Integer userID = results.getInt("User_ID");
            Integer contactID = results.getInt("Contact_ID");
            String contactName = results.getString("Contact_Name");

            Appointment newAppt = new Appointment(
                    appointmentID, title, description, location, type, startDateTime, endDateTime, createdDate,
                    createdBy, lastUpdateDateTime, lastUpdatedBy, customerID, userID, contactID, contactName
            );
            filteredAppointments.add(newAppt);
        }

        return filteredAppointments;

    }

    /**Deletes appointment from database given a unique Appointment ID
      @param AppointmentID appointment ID for deletion
     * @return true if successful, false otherwise
     * @throws SQLException if appointment was not deleted
     */
    public static Boolean DeleteAppointment(Integer AppointmentID) throws SQLException {
        String sql = "DELETE FROM Appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, AppointmentID);

        try {
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**Updates appointment
     * @param title Title of appointment
     * @param description description of appointment
     * @param location location of appointment
     * @param type type of appointment
     * @param startdatetime start timestamp of appointment
     * @param enddatetime end date timestamp of appointment

     * @param Updatedby updated by-  user who last updated appointment
     * @param customerID - ID of customer
     * @param userID - user ID
     * @param contactID - contact ID
     * @return  true if successful, false otherwise
     * @throws SQLException if appointment was not updated in database
     */

    public static Boolean updateAppointment(int ApptID, String title, String description, String location, String type, Timestamp startdatetime, Timestamp enddatetime, String Updatedby, int customerID, int userID, int contactID) throws SQLException {
        String sqlCommand = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=?," +
                "Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        //for ZoneDateTime
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startdatetime);
        ps.setTimestamp(6, enddatetime);
        ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(dateformatter));
        ps.setString(8, Updatedby);
        ps.setInt(9, customerID);
        ps.setInt(10, userID);
        ps.setInt(11, contactID);
        ps.setInt(12, ApptID);
        try {
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
            ps.close();
            return false;
        }

    }

    /**Gets any appointment objects from current user (Test) within 15 minutes of their login.
     * @return  Appointmentin15 Observable List
     * @throws SQLException if no such user was found
     */

    public static ObservableList<Appointment> GetApptIn15() throws SQLException {
        ObservableList<Appointment> AppointmentIn15 = observableArrayList();

        // prepare LocalDateTime to check against database appointment start times.
        LocalDateTime TimeNow = LocalDateTime.now(); //user's LocalDateTime
        ZonedDateTime UserTimeZone = TimeNow.atZone(Session.getUserTimeZone()); //User's time in their time zone
        ZonedDateTime UTCNow = UserTimeZone.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime UTCin15 = UTCNow.plusMinutes(15);


        // Format and assign input strings for prepared statement.
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String Start = UTCNow.format(DTF);
        String End = UTCin15.format(DTF);
        Integer UserID = Session.getCurrentUser().getUserID();

        String sql = "SELECT * FROM Appointments JOIN Contacts ON Appointments.Contact_ID= " +
                "Contacts.Contact_ID WHERE Start BETWEEN ? AND ? AND User_ID = ?;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);


        ps.setString(1, Start);
        ps.setString(2, End);
        ps.setInt(3, UserID);

        ResultSet results = ps.executeQuery();
        try {
            while (results.next()) {

                Integer appointmentID = results.getInt("Appointment_ID");
                String title = results.getString("Title");
                String description = results.getString("Description");
                String location = results.getString("Location");
                String type = results.getString("Type");
                Timestamp startDateTime = results.getTimestamp("Start"); //automatically gets it in local time
                Timestamp endDateTime = results.getTimestamp("End");
                Timestamp createdDate = results.getTimestamp("Create_Date");
                String createdBy = results.getString("Created_by");
                Timestamp lastUpdateDateTime = results.getTimestamp("Last_Update");
                String lastUpdatedBy = results.getString("Last_Updated_By");
                Integer customerID = results.getInt("Customer_ID");
                Integer userID = results.getInt("User_ID");
                Integer contactID = results.getInt("Contact_ID");
                String contactName = results.getString("Contact_Name");


                Appointment Appt = new Appointment(
                        appointmentID, title, description, location, type, startDateTime, endDateTime, createdDate,
                        createdBy, lastUpdateDateTime, lastUpdatedBy, customerID, userID, contactID, contactName
                );

                AppointmentIn15.add(Appt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return AppointmentIn15;
    }

}