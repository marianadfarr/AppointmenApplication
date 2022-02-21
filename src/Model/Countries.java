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


    /**
     *
     * @param countryName for country
     */
    public Countries(String countryName) {
        this.countryName= countryName;
    }


    /**
     *
     * @return countryID for country
     */
    public int getCountryID () {
            return countryID;
        }

    /**
     *
     * @param countryID for country
     */
        public void setCountryID ( int countryID){
            this.countryID = countryID;
        }

    /**
     *
     * @return Name for country
     */
        public String getCountryName () {
            return countryName;
        }

    /**
     *
     * @param countryName for country
     */
        public void setCountryName (String countryName){
            this.countryName = countryName;
        }

    /**
     *
     * @return createdDate for country
     */
        public Date getCreatedDate () {
            return createdDate;
        }

    /**
     *
     * @param createdDate for country
     */
        public void setCreatedDate (Date createdDate){
            this.createdDate = createdDate;
        }

    /**
     *
     * @return CreatedBy for country
     */
        public String getCreatedBy () {
            return createdBy;
        }

    /**
     *
     * @param createdBy for country
     */
        public void setCreatedBy (String createdBy){
            this.createdBy = createdBy;
        }

    /**
     *
     * @return LastUpdate for country
     */
        public Timestamp getLastUpdate () {
            return lastUpdate;
        }

    /**
     *
     * @param lastUpdate for country
     */
        public void setLastUpdate (Timestamp lastUpdate){
            this.lastUpdate = lastUpdate;
        }

    /**
     *
     * @return last updated by for country
     */
        public String getLastUpdatedBy () {
            return lastUpdatedBy;
        }

    /**
     *
     * @param lastUpdatedBy for country
     */
        public void setLastUpdatedBy (String lastUpdatedBy){
            this.lastUpdatedBy = lastUpdatedBy;
        }

    }
