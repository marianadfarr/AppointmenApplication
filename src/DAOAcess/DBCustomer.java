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

/**
 *This gets data for the Customers on the database.
 */
public class DBCustomer {
    /** Returns all customers objects in the database.
     * @return all customer list from the database.
     */
    public static ObservableList<Customer> GetAllCustomers() {
        ObservableList<Customer> AllCustomersList = observableArrayList();
        try {
            String sql = "SELECT Customers.Customer_ID, Customers.Customer_Name, Customers.Address, Customers.Postal_Code, Customers.Phone," +
                    "first_level_divisions.Division, Countries.Country FROM Customers JOIN first_level_divisions  ON Customers.Division_ID = first_level_divisions.Division_ID" +
                    " JOIN Countries ON first_level_divisions.Country_ID = Countries.Country_ID";


            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet allCustResults = ps.executeQuery();
            while (allCustResults.next()) {
                int customerID = allCustResults.getInt("Customer_ID");
                String customerName = allCustResults.getString("Customer_Name");
                String customerAddress = allCustResults.getString("Address");
                String customerpostalCode = allCustResults.getString("Postal_Code");
                String customerPhone = allCustResults.getString("Phone");
                String countryName = allCustResults.getString("Country");
                String divisionName = allCustResults.getString("Division");

                Customer customer = new Customer(customerID, customerName, customerAddress, customerpostalCode, customerPhone, countryName, divisionName);

                AllCustomersList.add(customer);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return AllCustomersList;
    }

    /** Creates a new customer object
     * @param name customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phoneNumber customer phone number
     * @param DivisionID customer's division ID
     * @return true if created in DB, false otherwise.
     * @throws SQLException if customer fails to be added
     */
    public static Boolean CreateCustomer(String name, String address, String postalCode,
                                         String phoneNumber, int DivisionID) throws SQLException {
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String sqlcommand = "INSERT INTO customers (Customer_ID,Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES(NULL,?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement sql = JDBC.getConnection().prepareStatement(sqlcommand,Statement.RETURN_GENERATED_KEYS);

        sql.setString(1, name);
        sql.setString(2, address);
        sql.setString(3, postalCode);
        sql.setString(4, phoneNumber);
        sql.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(DTF));
        sql.setString(6, Session.getCurrentUser().getUserName());
        sql.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(DTF));
        sql.setString(8, Session.getCurrentUser().getUserName());
        sql.setInt(9, DivisionID);

        try {
            sql.execute();
            ResultSet resultset = sql.getGeneratedKeys();
            resultset.next();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /** Updates an existing customer object
     * @param divisionName Name of division
     * @param customerName Name of customer
     * @param customerAddress Customer address
     * @param customerpostalCode customer Postal Code
     * @param customerPhone customer Phone
     * @param customerID customer ID
     * @return true if updated in DB, false otherwise
     * @throws SQLException if customer was not updated in DB
     */
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
        ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(formatter));
        ps.setString(6, Session.getCurrentUser().getUserName());
        ps.setInt(7, DBCustomer.getDivisionID(divisionName));
        ps.setInt(8, customerID);

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

    /**Takes a division name and returns the DivisionID
     * @param division Division name
     * @return divisionID corresponding Division ID
     * @throws SQLException if no such division name is found in DB
     */

    public static Integer getDivisionID(String division) throws SQLException {
        Integer divisionID = 0;
        String sql = "SELECT Division, Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, division);

        ResultSet result = ps.executeQuery();

        while (result.next()) {
            divisionID = result.getInt("Division_ID");
        }

        return divisionID;

    }

    /**Takes a country and returns its associated first-level-divisions in an Observable List
     * @param targetCountry Country
     * @return targetDivisions, divisions associated with that country
     * @throws SQLException if no such country is found.
     */
    public static ObservableList<String> getDivisions(String targetCountry) throws SQLException {

        ObservableList<String> targetDivisions = FXCollections.observableArrayList();

        String sql = "SELECT Countries.Country, Countries.Country_ID," +
                "first_level_divisions.Country_ID," +
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


    /**Gets all customerIDs to populate the customerID combo box
     * @return allCustomerIDs Observable List
     * @throws SQLException if database fails to retrieve list
     */
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

    /** Gets all countries in the database to populate combo box
     * @return AllCountries
     * @throws SQLException if database fails to retrieve countries.
     */
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

    /** Get an observable list of all customer appointments for one customer, if any.
     This is to validate not deleting a customer before all of their appointments are deleted.
     * @param CustomerID  specific customer ID
     * @return CustomerAppointments ObservableList
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllAppointmentsForCustomer(Integer CustomerID) throws SQLException {

        ObservableList<Appointment> CustomerApppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Appointments JOIN contacts ON Appointments.Contact_ID = " +
                "Contacts.Contact_ID WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, CustomerID);

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

            Appointment newAppt = new Appointment(
                    appointmentID, title, description, location, type, startDateTime, endDateTime, createdDate,
                    createdBy, lastUpdateDateTime, lastUpdatedBy, customerID, userID, contactID, contactName
            );
            CustomerApppointments.add(newAppt);
        }
        return CustomerApppointments;
    }

    /** Given a customer ID, delete the customer object.
     * @param customerID
     * @return true if deleted, false otherwise.
     * @throws SQLException if no such customer ID exists.
     */
    public static Boolean DeleteCustomer(Integer customerID) throws SQLException {
        String sql = "DELETE FROM Customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customerID);
        try {
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }
}



