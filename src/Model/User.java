package Model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 */
public class User {

    private int userID;
    private String userName;
    private String password;
    private Date createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedby;

    /**
     *
     * @param userID User ID
     * @param userName User Name
     * @param password User Password
     * @param createdDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedby
     */
    public User(int userID, String userName, String password, Date createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedby) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedby = lastUpdatedby;
    }

    /**

     * @param userName user name
     * @param userId user ID
     */
    public User(String userName, int userId) {
        this.userName = userName;
        this.userID = userId;
    }

    /**
     *
     * @return user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     *
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return password for user
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password password for user
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @param createdDate created date
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
     * @param createdBy created by
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
     * @param lastUpdate last update timestamp
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return last update
     */
    public String getLastUpdatedby() {
        return lastUpdatedby;
    }

    /**
     *
     * @param lastUpdatedby last updated by
     */
    public void setLastUpdatedby(String lastUpdatedby) {
        this.lastUpdatedby = lastUpdatedby;
    }

}