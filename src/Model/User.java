package Model;

import java.sql.Date;
import java.sql.Timestamp;

public class User {

    private int userID;
    private String userName;
    private String password;
    private Date createdDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedby;

    public User(int userID, String userName, String password, Date createdDate, String createdBy, Timestamp lastUpdate, String lastUpdatedby) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedby = lastUpdatedby;
    }

    public User(String userName, int userId) {
        this.userName = userName;
        this.userID= userId;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


//    User_ID int AI PK
//    User_Name varchar(50)
//    Password text
//    Create_Date datetime
//    Created_By varchar(50)
//    Last_Update timestamp
//    Last_Updated_By varchar(50)
}
