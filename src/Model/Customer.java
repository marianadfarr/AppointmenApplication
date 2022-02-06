package Model;

import java.sql.Date;
import java.sql.Timestamp;

public class Customer {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerpostalCode;
    private String customerPhone;
    private Date createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedby;
    private int divisionID;
    private String divisionName;
    private String countryName;

    public Customer(int customerID, String customerName, String customerAddress, String customerpostalCode, String customerPhone,  String  countryName, String divisionName, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerpostalCode = customerpostalCode;
        this.customerPhone = customerPhone;
        this.countryName=countryName;
        this.divisionName=divisionName;
        this.divisionID = divisionID;

    }

    public Customer(int customerID, String customerName, String customerAddress, String customerpostalCode, String customerPhone, Date createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedby, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerpostalCode = customerpostalCode;
        this.customerPhone = customerPhone;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedby = lastUpdatedby;
        this.divisionID = divisionID;

    }

    public Customer(int customerID, String customerName, String customerAddress, String customerpostalCode, String customerPhone,  String countryName,String divisionName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerpostalCode = customerpostalCode;
        this.customerPhone = customerPhone;
        this.divisionName= divisionName;
        this.countryName= countryName;
    }


    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerpostalCode() {
        return customerpostalCode;
    }

    public void setCustomerpostalCode(String customerpostalCode) {
        this.customerpostalCode = customerpostalCode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedby() {
        return lastUpdatedby;
    }

    public void setLastUpdatedby(String lastUpdatedby) {
        this.lastUpdatedby = lastUpdatedby;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        divisionID = divisionID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

//
//    Customer_ID int AI PK
//    Customer_Name varchar(50)
//    Address varchar(100)
//    Postal_Code varchar(50)
//    Phone varchar(50)
//    Create_Date datetime
//    Created_By varchar(50)
//    Last_Update timestamp
//    Last_Updated_By varchar(50)
//    Division_ID int
}
