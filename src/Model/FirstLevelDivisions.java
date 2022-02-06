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

    public int getDivisionID() {
        return DivisionID;
    }

    public void setDivisionID(int divisionID) {
        DivisionID = divisionID;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
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

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }


    //  //first_level_divisions
    //    //Columns:
    //    //Division_ID int AI PK
    //    //Division varchar(50)
    //    //Create_Date datetime
    //    //Created_By varchar(50)
    //    //Last_Update timestamp
    //    //Last_Updated_By varchar(50)
    //    //Country_ID int
}
