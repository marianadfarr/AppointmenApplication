package Model;


import java.sql.Timestamp;


public class Appointment {
    private int appointmentID; //primary key
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp startDatetime;
    private Timestamp endDatetime;
    private Timestamp createdDatetime;
    private String createdBy;
    private Timestamp lastUpdate;
    private String updatedBy;
    private int customerID;
    private int userID;
    private int contactID;
    private String contactName; //foreign key

    /**
     * Creates appointment objects
     */

    public Appointment(int appointmentID, String title, String description, String location, String type, Timestamp startDatetime, Timestamp endDatetime, Timestamp createdDatetime, String createdBy, Timestamp lastUpdate, String updatedBy, int customerID, int userID, int contactID, String contactName) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.createdDatetime = createdDatetime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.contactName = contactName;
    }

    public Appointment(int appointmentID, String title, String description, String location, String type, Timestamp startDatetime, Timestamp endDatetime, Timestamp createdDatetime, String createdBy, Timestamp lastUpdate, String updatedBy, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.createdDatetime = createdDatetime;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.updatedBy = updatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }


    /**
     *
     * @return appointmentID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     *
     * @param appointmentID appointment ID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     *
     * @return title for appointment
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title title of appointment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return description for appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description for appointment
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return location for appointment
     */

    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location for appointment
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return type for appointment
     */

    public String getType() {
        return type;
    }

    /**
     *
     * @param type for appointment
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return createdDatetime for appointment
     */

    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    /**
     *
     * @param createdDatetime for appointment
     */
    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    /**
     *
     * @return createdBy for appointment
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     *
     * @param createdBy for appointment
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return lastUpdate for appointment
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     *
     * @param lastUpdate for appointment
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return updatedBy for appointment
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     *
     * @param updatedBy for appointment
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     *
     * @return customerID for appointment
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @param customerID for appointment
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     *
     * @return  userID for appointment
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @param userID   for appointment
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     *
     * @return contactID for appointment
     */
    public int getContactID() {
        return contactID;
    }

    /**
     *
     * @param contactID for appointment
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     *
     * @return contactName for appointment
     */
    public String getContactName() {
        return contactName;
    }

    /**
     *
     * @param contactName for appointment
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     *
     * @return startDatetime for appointment
     */
    public Timestamp getStartDatetime() {
        return startDatetime;
    }

    /**
     *
     * @param startDatetime for appointment
     */
    public void setStartDatetime(Timestamp startDatetime) {
        this.startDatetime = startDatetime;
    }

    /**
     *
     * @return endDatetime for appointment
     */
    public Timestamp getEndDatetime() {
        return endDatetime;
    }

    /**
     *
     * @param endDatetime for appointment
     */

    public void setEndDatetime(Timestamp endDatetime) {
        this.endDatetime = endDatetime;
    }


}

