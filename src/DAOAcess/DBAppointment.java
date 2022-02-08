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

public class DBAppointment {

    public DBAppointment() throws SQLException {
    }

    public static ObservableList<Appointment> GetAllAppointments() throws SQLException {
        ObservableList<Appointment> AllAppointmentsList = observableArrayList(); // create list to add each object to
        try {
            //try because we do not want to throw exceptions in the data access code //below is the FIELD LIST on the DB
            String sql = "SELECT * FROM appointments JOIN contacts WHERE appointments.Contact_ID = contacts.Contact_ID"; // makes query for DB
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //db connection,prepared statement
            ResultSet allApptResults = ps.executeQuery(); // gets result set, on execute query
            while (allApptResults.next()) { //loops through result set using next
                int appointmentID = allApptResults.getInt("Appointment_ID"); //gets id for app from DB- col label must match DB col
                String title = allApptResults.getString("Title"); //gets title from DB
                String description = allApptResults.getString("Description");
                String location = allApptResults.getString("Location");
                String type = allApptResults.getString("Type");
                //we dont need the following 4 fields displayed. But they are part of the object
                Timestamp createdDatetime = allApptResults.getTimestamp("Create_Date");
                String createdBy = allApptResults.getString("Created_By");
                Timestamp lastUpdated = allApptResults.getTimestamp("Last_Update"); //timestamps automatically show system time.
                String updatedBy = allApptResults.getString("Last_Updated_By");
                //end fields that will not show up on tableview
                String contactName = allApptResults.getString("Contact_Name");
                int contactID = allApptResults.getInt("Contact_ID");
                int customerID = allApptResults.getInt("Customer_ID");
                int userID = allApptResults.getInt("User_ID");
                Timestamp startTime = allApptResults.getTimestamp("Start"); //displays the time from UTC to machine time automatically.
                Timestamp endTime = allApptResults.getTimestamp("End");


                //takes all data for one loop and makes new object as per constructor defined in model cln
                Appointment newAppointment = new Appointment(appointmentID, title, description, location, type, startTime, endTime, createdDatetime, createdBy, lastUpdated, updatedBy, customerID, userID, contactID, contactName);
//
                //add new object to list

                AllAppointmentsList.add(newAppointment);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace(); //print errors if any.
        }
        //return all appt list
        return AllAppointmentsList;
    }

    //injects data to sql. This has more columns than what we can see in main screen/appointment view.
    public static Boolean CreateAppointment(String title, String description, String location, String type, Timestamp StartDateTime,Timestamp EndDateTime, String createdBy, String Updatedby, int customerID, int userID, int contactID) throws SQLException {

        String sqlsca = "INSERT into Appointments VALUES(NULL, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?)"; //using comboboxes
        PreparedStatement psti = JDBC.getConnection().prepareStatement(sqlsca, Statement.RETURN_GENERATED_KEYS); //db connection,prepared statement,
        //format start and end times
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        //formatting our dates accordingly
//        String Start = StartDateTime.format(String.valueOf(formatter));
//        String End= EndDateTime.format(String.valueOf(formatter)); //string version of end date time.
        psti.setString(1, title);
        psti.setString(2, description);
        psti.setString(3, location);
        psti.setString(4, type);
        psti.setTimestamp(5, StartDateTime); //setting timestamps
        psti.setTimestamp(6, EndDateTime);
        psti.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(formatter)); //createdtime- make NOW into UTC, not an inpput
        psti.setString(8, createdBy);
        psti.setString(9, ZonedDateTime.now(ZoneOffset.UTC).format(formatter)); //Lastupdated- make NOW into UTC, not an input
        psti.setString(10, Updatedby);
        psti.setInt(11, customerID);
        psti.setInt(12, userID);
        psti.setInt(13, contactID);

        try {
            psti.execute();
            ResultSet resultset = psti.getGeneratedKeys(); //for ID
            resultset.next();
//
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace(); //print errors if any.
            return false;
        }

    }

    //finds all appointments for a spefici customer on a specific day. used to find any appt conflicts
    //this is to find out any overlaps in appointments
    public static ObservableList<Appointment> getCustomerFilteredAppointments(
            LocalDate Date, Integer CustomerID) throws SQLException {

        //select all appointments from testcustomers where the date is the same as the date chosen in date picker.
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Appointments JOIN contacts ON Appointments.Contact_ID = " +
                "Contacts.Contact_ID WHERE DATEDIFF(Appointments.Start, ?) = 0 AND Customer_ID = ?;";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);


        ps.setString(1, Date.toString()); //first question mark
        ps.setInt(2, CustomerID); //second question mark

        ResultSet results = ps.executeQuery();

        while (results.next()) {
            // get data from the returned rows
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

            // populate into an appt object
            Appointment newAppt = new Appointment(
                    appointmentID, title, description, location, type, startDateTime, endDateTime, createdDate,
                    createdBy, lastUpdateDateTime, lastUpdatedBy, customerID, userID, contactID, contactName
            );
            filteredAppointments.add(newAppt);
        }

        return filteredAppointments;

    }


    public static Boolean DeleteAppointment(Integer AppointmentID) throws SQLException {
        String sql = "DELETE FROM Appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, AppointmentID); //this is for the questionmark

        try {
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static Boolean updateAppointment(int ApptID, String title, String description, String location, String type, Timestamp startdatetime, Timestamp enddatetime,  String Updatedby, int customerID, int userID, int contactID) throws SQLException {
        String sqlCommand = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=?,Last_Updated_By=?, " +
                "Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);
        //fixme doesnt the start and end  have to be in hh:mm:ss
       DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String StartString = startdatetime.format(dateformatter); //string version of start date time
//        String EndString = enddatetime.format(dateformatter); //string version of end date time.
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startdatetime); //sets start time in string form but will be utc in DB
        ps.setTimestamp(6, enddatetime);
        ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(dateformatter)); //createdtime- make NOW into UTC, not an inpput
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
}

