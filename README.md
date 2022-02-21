# Appointment Application
Purpose: to create a GUI- based desktop application for a company that has users in Phoenix, Arizona; White Plains, New York; Montreal, Canada; and London, England.  This application will allow users to sign in in their machine’s language (either English or French). Any attempts to log in, whether failed or successful, will be logged in the file Login_Activity.txt. The user will receive a notification whether they have an upcoming appointment within 15 minutes or not. Users will be able to create, delete, read and update appointments for customers. They will also be able to create, read, update, and delete customers and to grab reports from the data. Appointment times will be validated against the database no matter which time zone the user is in, and will be checked for validity of business hours (in Eastern Standard Time - 8 am to 10 pm). Appointments will also not be allowed to be booked for a customer if that customer is already booked within that time frame on that same day. Customer records will not be able to be deleted unless their related appointments are deleted first.


IDE: IntelliJ IDEA Community Edition 2021.1.1

Java:   Java SE 17.0.1

JavaFX: javafx-sdk-17.0.1

MySQL: mysql-connector-java-8.0.25\mysql-connector-java-8.0.25.jar

Student application version 3.0


How to run the program:
Open the folder in IntelliJ IDEA. Update the path to JDBC, JavaFX SDK,  and JDK. Press play.

Lambda Expressions:
In the Add Customer screen and Update Customer screen, lambda expression allows users to see only related first-level divisions in the combo box after choosing a country in the country combo box.
In the Add Appointment screen and  Update Appointment screen, the Date picker is disabled for any days before today to prevent users from making appointments in the past.


Additional report created:
I created a report showing a count of how many customers were located in the three countries provided in the database. This gives insight to the company as to where the majority of their customer base is located. Keeping track of these numbers over time can track the changing demographic  of the customers. 


-Mariana Farr, 02-21-2022
