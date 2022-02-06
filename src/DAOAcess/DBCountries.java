package DAOAcess;

import DBConnection.JDBC;
import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import static javafx.collections.FXCollections.observableArrayList;
//do not create any
public class DBCountries {
//    Country_ID int AI PK
//    Country varchar(50)
//    Create_Date datetime
//    Created_By varchar(50)
//    Last_Update timestamp
//    Last_Updated_By varchar(50)

    //CRUD
//fixme we dont use below. in actual application
    public static ObservableList<Countries> GetAllCountries() {
        ObservableList<Countries> AllCountriesList = observableArrayList();
        try {
            //try because we do not want to throw exceptions in the data access code
            String sql = "SELECT * from Countries"; // makes query
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //db connection,prepared statement
            ResultSet countryResults = ps.executeQuery(); // gets result set, on execute query
            while (countryResults.next()) { //loops through result set using next
//                int countryID = countryResults.getInt("Country_ID"); //gets id for country
                String CountryName = countryResults.getString("Country"); //gets name for country
                //make new country object as per constructor
                Countries c = new Countries(CountryName);
                //add new object to list
                AllCountriesList.add(c);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace(); //print error
        }
        return AllCountriesList; //return all countries
    }

// below we can check out the created date from countries (prints to console)
    //this time that gets printed out is for the local time, not UTC, driver automatically converts
            public static void CheckDateConversion() {
                System.out.println("Testing Date");
                String sql = "Select Create_Date from countries";
                try {
                    PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
                    ResultSet cd = preparedStatement.executeQuery();
                    while (cd.next()) {
                        Timestamp timestamp = cd.getTimestamp("Create_Date");
                        System.out.println("CD" + timestamp.toLocalDateTime().toString());
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();

                }

            }
                // this populates all countries' name in the Country Combo Box.
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

