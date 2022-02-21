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

    public Customer(int customerID, String customerName, String customerAddress, String customerpostalCode, String customerPhone, String countryName, String divisionName, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerpostalCode = customerpostalCode;
        this.customerPhone = customerPhone;
        this.countryName = countryName;
        this.divisionName = divisionName;
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

    public Customer(int customerID, String customerName, String customerAddress, String customerpostalCode, String customerPhone, String countryName, String divisionName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerpostalCode = customerpostalCode;
        this.customerPhone = customerPhone;
        this.countryName = countryName;
        this.divisionName = divisionName;
    }

    /**
     * @return customerID for customer
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID for customer
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return name for customer
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName for customer
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return address for customer
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * @param customerAddress for customer
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * @return postal code for customer
     */
    public String getCustomerpostalCode() {
        return customerpostalCode;
    }

    /**
     * @param customerpostalCode for customer
     */
    public void setCustomerpostalCode(String customerpostalCode) {
        this.customerpostalCode = customerpostalCode;
    }

    /**
     * @return phone for customer
     */

    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * @param customerPhone for customer
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * @return Created date for customer
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate for customer
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return created by for customer
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy for customer
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return LastUpdate for customer
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate for customer
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return Last updated by for customer
     */
    public String getLastUpdatedby() {
        return lastUpdatedby;
    }

    /**
     * @param lastUpdatedby for customer
     */
    public void setLastUpdatedby(String lastUpdatedby) {
        this.lastUpdatedby = lastUpdatedby;
    }

    /**
     * @return Division ID for customer
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @param divisionID for customer
     */
    public void setDivisionID(int divisionID) {
        divisionID = divisionID;
    }

    /**
     * @return country name for customer
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName for customer
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return Division name for customer
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @param divisionName for customer
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

}