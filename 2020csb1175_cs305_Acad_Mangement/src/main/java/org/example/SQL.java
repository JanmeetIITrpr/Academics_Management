package org.example;

import java.sql.*;
public class SQL
{
    static Connection con = null;
    static String connectionUrl = "jdbc:postgresql://localhost:5432/aims";
    static String connectionUser = "postgres";
    static String connectionPassword = "12345678";

    public static Connection getConnection () {

        if (con != null) {
            return con;
        }
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
            System.out.println("Connected to PSQL database successfully");
        }
        catch(Exception e){
            return null;//not connected
        }

        return con;
    }
}