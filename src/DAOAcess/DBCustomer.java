package DAOAcess;

import DBConnection.JDBC;
import Model.Appointment;
import Model.Customer;
import Model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static javafx.collections.FXCollections.observableArrayList;

public class DBCustomer {
    public static ObservableList<Customer> GetAllCustomers() {
        ObservableList<Customer> AllCustomersList = observableArrayList(); // create list to add each object to
        try {
            //try because we do not want to throw exceptions in the data access code //below is the FIELD LIST on the DB
            // makes query for DB //need to be able to get country too
            String sql = "SELECT Customers.Customer_ID, Customers.Customer_Name, Customers.Address, Customers.Postal_Code, Customers.Phone," +
                    "first_level_divisions.Division, Countries.Country FROM Customers JOIN first_level_divisions  ON Customers.Division_ID = first_level_divisions.Division_ID" +
                    " JOIN Countries ON first_level_divisions.Country_ID = Countries.Country_ID";


            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //db connection,prepared statement
            ResultSet allCustResults = ps.executeQuery(); // gets result set, on execute query
            while (allCustResults.next()) { //loops through result set using next
                int customerID = allCustResults.getInt("Customer_ID"); //gets id for app from DB- col label must match DB col
                String customerName = allCustResults.getString("Customer_Name"); //gets title from DB
                String customerAddress = allCustResults.getString("Address");
                String customerpostalCode = allCustResults.getString("Postal_Code");
                String customerPhone = allCustResults.getString("Phone");
                String countryName = allCustResults.getString("Country");
                String divisionName = allCustResults.getString("Division");


                //takes all data for one loop and makes new object as per constructor defined in model class

                Customer customer = new Customer(customerID, customerName, customerAddress, customerpostalCode, customerPhone, countryName, divisionName);

                AllCustomersList.add(customer);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace(); //print errors if any.
        }
        return AllCustomersList; //return all customer list
    }

    public static Boolean CreateCustomer(String name, String address, String postalCode,
                                         String phoneNumber, int DivisionID) throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sqlcommand = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Division_ID) \n" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        //db connection,prepared statement
        PreparedStatement sql = JDBC.getConnection().prepareStatement(sqlcommand);

        sql.setString(1, name);
        sql.setString(2, address);
        sql.setString(3, postalCode);
        sql.setString(4, phoneNumber);
        sql.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        sql.setString(6, Session.getLoggedOnUser().getUserName());
        sql.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        sql.setString(8, Session.getLoggedOnUser().getUserName());
        sql.setInt(9, DivisionID);
        //only need to do division ID because we're adding to database
        // Execute query
        try {
            sql.executeUpdate();
            return true;
        } catch (SQLException e) {
            //TODO- log error??
            e.printStackTrace();
            return false;
        }

    }

    public static Boolean updateCustomer(String divisionName, String customerName, String customerAddress,
                                         String customerpostalCode, String customerPhone, Integer customerID) throws SQLException {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sqlCommand = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=?," +
                " Last_Updated_By=?, Division_ID=? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sqlCommand);


        ps.setString(1, customerName);
        ps.setString(2, customerAddress);
        ps.setString(3, customerpostalCode);
        ps.setString(4, customerPhone);
        ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(formatter).toString());
        ps.setString(6, Session.getLoggedOnUser().getUserName());
        ps.setInt(7, DBCustomer.getDivisionID(divisionName));
        ps.setInt(8, customerID);
//all fields for thr question mark
        // Execute query
        try {
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            //TODO- log error
            e.printStackTrace();
            ps.close();
            return false;
        }

    }


    public static Integer getDivisionID(String division) throws SQLException {
        Integer divisionID = 0;
        String sql = "SELECT Division, Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, division); //this is for the questionmark

        ResultSet result = ps.executeQuery();

        while (result.next()) {
            divisionID = result.getInt("Division_ID");
        }

        return divisionID;

    }

    public static ObservableList<String> getDivisions(String targetCountry) throws SQLException {

        ObservableList<String> targetDivisions = FXCollections.observableArrayList();

        String sql = "SELECT Countries.Country, Countries.Country_ID," +
                "first_level_divisions.Country_ID," + //i edited the sql
                "first_level_divisions.Division FROM Countries RIGHT OUTER JOIN" +
                " first_level_divisions ON Countries.Country_ID = first_level_divisions.Country_ID WHERE Countries.Country= ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, targetCountry);  //this is for the question mark
        ResultSet Divisionresults = ps.executeQuery();

        while (Divisionresults.next()) {
            targetDivisions.add(Divisionresults.getString("Division"));
        }

        return targetDivisions;


    }


    //get all customer iDs
    public static ObservableList<Integer> getAllCustomerIDs() throws SQLException {

        ObservableList<Integer> allCustomerIDs = FXCollections.observableArrayList();

        String sql = "SELECT Customer_ID FROM Customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet CustomerResults = ps.executeQuery();

        while (CustomerResults.next()) {
            allCustomerIDs.add(CustomerResults.getInt("Customer_ID"));
        }
        return allCustomerIDs;

    }

    public static ObservableList<String> GetAllCountries() throws SQLException {

        ObservableList<String> AllCountries = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT Country FROM countries";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet results = ps.executeQuery();

        while (results.next()) {
            AllCountries.add(results.getString("Country"));
        }

        return AllCountries;

    }
    public static ObservableList<Appointment> getAllAppointmentsForCustomer(Integer CustomerID) throws SQLException {
        //get an observable list of all customer appointments for one customer, if any.
        ObservableList<Appointment> CustomerApppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Appointments JOIN contacts ON Appointments.Contact_ID = " +
                "Contacts.Contact_ID WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, CustomerID); //first question mark

        ResultSet results = ps.executeQuery();

        while (results.next()) {
            // get data from the returned rows
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

            // populate into an appt object
            Appointment newAppt = new Appointment(
                    appointmentID, title, description, location, type, startDateTime, endDateTime, createdDate,
                    createdBy, lastUpdateDateTime, lastUpdatedBy, customerID, userID, contactID, contactName
            );
            CustomerApppointments.add(newAppt);
        }
        return CustomerApppointments;
    }

    public static Boolean DeleteCustomer(Integer customerID) throws SQLException {
        String sql = "DELETE FROM Customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID); //this is for the questionmark
        try {
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            //TODO- log error
            e.printStackTrace();
            return false;
        }
    }
}

    //Create
    //Read
    //Update
    //delete

