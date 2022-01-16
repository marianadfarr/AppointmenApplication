package Main;


import helper.JDBC;

public class main {
public static void main (String args[]){
    JDBC.openConnection(); //only call this once, use get connection for other
    JDBC.closeConnection(); //only call this at the end

    }
}
