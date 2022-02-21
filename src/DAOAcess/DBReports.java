package DAOAcess;

import DBConnection.JDBC;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


/**
 *This gets data for the Reports on the DB
 */
public class DBReports {

    /**
     *For the required report - Contact schedule.
     * Return all appointments for a given contactID.
     * @param ContactID Contact ID for one person
     * @return ContactAppointments ObservableList
     * @throws SQLException if no such contact exists
     */
    public static ObservableList<Appointment> getAllContactAppointments(
            Integer ContactID) throws SQLException {

        ObservableList<Appointment> ContactAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Appointments JOIN Customers WHERE Appointments.Customer_ID = Customers.Customer_ID AND Contact_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, ContactID);

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

            Appointment newAppt = new Appointment(appointmentID, title, description, location, type, startDateTime, endDateTime, createdDate,
                    createdBy, lastUpdateDateTime, lastUpdatedBy, customerID, userID, contactID
            );
            ContactAppointments.add(newAppt);
        }

        return ContactAppointments;

    }


    /** Report of choice: given a country ID; return count of how many customers are from that country.
     * @param countryID one of 3 available country IDs.
     * @return count of customers from that country.
     * @throws SQLException if no such country exists.
     */

    public static int CustomersByCountryCount(int countryID) throws SQLException {
       int count = 0;
        String sql = "SELECT count(Customer_ID) FROM customers JOIN first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID AND Country_ID= ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet customerResult = ps.executeQuery();
        while (customerResult.next()) {
            count = customerResult.getInt("count(Customer_ID)");

        }
        return count;

    }


    /** For the required report - Appointment count by Month and Type
     * @param month Month
     * @param Type Type of appointment.
     * @return count of appointments per month by type
     * @throws SQLException if no such month or type exist(s).
     */
    public static int AppointmentsByTypeandMonth(int month, String Type) throws SQLException {
        int count = 0;
        String sql = "SELECT count(Appointment_ID) FROM appointments WHERE MONTH(appointments.Start) = ? AND  appointments.type = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, month);
        ps.setString(2,Type);
        ResultSet customerResult = ps.executeQuery();
        while (customerResult.next()) {
            count = customerResult.getInt("count(Appointment_ID)");

        }
        return count;

    }
    public static ObservableList<String>  GetAllAppointmentTypes() throws SQLException {
        ObservableList<String> allAppointmentTypes =FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT Type FROM Appointments";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet TypeResults = ps.executeQuery();

        while (TypeResults.next()) {
            allAppointmentTypes.add(TypeResults.getString("Type"));
        }
        return allAppointmentTypes;

    }
}
