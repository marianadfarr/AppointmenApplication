package Audit;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logger class for login attempts
 */

public class Logger {

    private static final String  AuditFile= "login_activity.txt"; //File name for our audit

    /**
     * * TrackLogin
     *   Log login activity in the text file login_activity.txt
     * @param username user's username
     * @param logon if login was successful or not
     * @throws IOException if input is incorrect
     */
        public static void TrackLogin (String username, Boolean logon) throws IOException {
            try {
                DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                FileWriter FWO = new FileWriter(AuditFile, true);
                PrintWriter logger = new PrintWriter(FWO);
                logger.append( "Time of logon attempt: " + ZonedDateTime.now(ZoneOffset.UTC).format(DTF) + "  UTC.\n  " +
                        "Login attempt by user: " +  username + "\n  Login successful? (T/F): " + logon.toString().toUpperCase() + "\n" );
                logger.flush();
                logger.close(); //close the file
                System.out.println("Login Recorded");
            }
            catch (IOException error) {
                error.printStackTrace();
            }

        }
    }
