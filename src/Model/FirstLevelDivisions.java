package Model;

import java.sql.Date;
import java.sql.Timestamp;

public class FirstLevelDivisions {
    private int DivisionID;
    private String divisionName;
    private Date createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedby;
    private int countryID;

    public FirstLevelDivisions(int divisionID, String divisionName, Date createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedby, int countryID) {
        DivisionID = divisionID;
        this.divisionName = divisionName;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedby = lastUpdatedby;
        this.countryID = countryID;
    }

    /**
     *
     * @return division ID
     */
    public int getDivisionID() {
        return DivisionID;
    }

    /**
     *
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        DivisionID = divisionID;
    }

    /**
     *
     * @return division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     *
     * @param divisionName
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     *
     * @return created date
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return last update
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return last updated by
     */
    public String getLastUpdatedby() {
        return lastUpdatedby;
    }

    /**
     *
     * @param lastUpdatedby
     */
    public void setLastUpdatedby(String lastUpdatedby) {
        this.lastUpdatedby = lastUpdatedby;
    }

    /**
     *
     * @return country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     *
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

}