package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class Sqlconnection {
    public static Connection connect() {
        Connection con=null;
        String url="jdbc:mysql://localhost:3306/sakila";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection(url,"root","DartrixDDR4L");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
