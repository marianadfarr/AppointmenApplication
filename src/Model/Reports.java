package Model;

import java.sql.Date;

public class Reports {

    //  the total number of customer appointments by type and month
    //•  a schedule for each contact in your organization that includes appointment ID, title, type and description, start date and time, end date and time,
    // and customer ID
    // an additional report of your choice that is different from the two other required reports in this prompt and from the user log-in date and time stamp that will be tracked in part C

//class used for creating reports

    private String month;
    private int initial;
    private int followUp;
    private int educational;
    private int companyMeetings;
    private int totalAppointments;
    private String contact;
    private int appointmentID;
    private String appointmentTitle;
//    private ??? type; fixme
    private String appointmentDescription;
    private Date startDate;
    private Date endDate;
    private int customerID;

    //constructor for first tab
    public Reports(String month, int initial, int followUp, int educational, int companyMeetings, int totalAppointments) {
        this.month = month;
        this.initial = initial;
        this.followUp = followUp;
        this.educational = educational;
        this.companyMeetings = companyMeetings;
        this.totalAppointments = totalAppointments;
    }
//constructor for second tab (fixme needs type)
    public Reports(String contact, int appointmentID, String appointmentTitle, String appointmentDescription, Date startDate, Date endDate, int customerID) {
        this.contact = contact;
        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDescription = appointmentDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerID = customerID;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getInitial() {
        return initial;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public int getFollowUp() {
        return followUp;
    }

    public void setFollowUp(int followUp) {
        this.followUp = followUp;
    }

    public int getEducational() {
        return educational;
    }

    public void setEducational(int educational) {
        this.educational = educational;
    }

    public int getCompanyMeetings() {
        return companyMeetings;
    }

    public void setCompanyMeetings(int companyMeetings) {
        this.companyMeetings = companyMeetings;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(int totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
}


