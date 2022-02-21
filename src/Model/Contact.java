package Model;

public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     *
     * @return contactID for contact
     */
    public int getContactID() {
        return contactID;
    }

    /**
     *
     * @param contactID  for contact
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     *
     * @return  contactName for contact
     */
    public String getContactName() {
        return contactName;
    }

    /**
     *
     * @param contactName contact name.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     *
     * @return email for contact
     */

    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email  for contact
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
