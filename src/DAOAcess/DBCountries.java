package DAOAcess;

import DBConnection.JDBC;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;



/**
 *This gets data for the Countries on the database.
 * No need to create, update, or delete any countries.
 */
public class DBCountries {



    /**This populates all countries' name in the Country Combo Box.
     * @return all countries Observable List
     * @throws SQLException if DB connection fails
     */
                public static ObservableList<String> getAllCountries() throws SQLException {

                    ObservableList<String> allCountries = FXCollections.observableArrayList();

                    String sql = "SELECT Country FROM countries";
                    PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                    ResultSet Countryresults = ps.executeQuery();

                    while (Countryresults.next()) {
                        allCountries.add(Countryresults.getString("Country"));
                    }
                    return allCountries;


                }}

