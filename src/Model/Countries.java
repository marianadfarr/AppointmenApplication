package Model;

import java.sql.Date;
import java.sql.Timestamp;

public class Countries {
    private int countryID;
    private String countryName;
    private Date createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    public Countries(int countryID, String countryName, Date createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }


    public Countries( int countryID, String countryName){
            this.countryID = countryID;
            this.countryName = countryName;
        }

    public Countries(String countryName) {
        this.countryName= countryName;
    }

    public int getCountryID () {
            return countryID;
        }

        public void setCountryID ( int countryID){
            this.countryID = countryID;
        }

        public String getCountryName () {
            return countryName;
        }

        public void setCountryName (String countryName){
            this.countryName = countryName;
        }

        public Date getCreatedDate () {
            return createdDate;
        }

        public void setCreatedDate (Date createdDate){
            this.createdDate = createdDate;
        }

        public String getCreatedBy () {
            return createdBy;
        }

        public void setCreatedBy (String createdBy){
            this.createdBy = createdBy;
        }

        public Timestamp getLastUpdate () {
            return lastUpdate;
        }

        public void setLastUpdate (Timestamp lastUpdate){
            this.lastUpdate = lastUpdate;
        }

        public String getLastUpdatedBy () {
            return lastUpdatedBy;
        }

        public void setLastUpdatedBy (String lastUpdatedBy){
            this.lastUpdatedBy = lastUpdatedBy;
        }
        //Country_ID int AI PK
        //Country varchar(50)
        //Create_Date datetime
        //Created_By varchar(50)
        //Last_Update timestamp
        //Last_Updated_By varchar(50)
    }
