package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class batch {
    static final Connection connection = SQL.getConnection();

    public static boolean add(String batchId, String year, String dep_id) {
        try {
            //Connection conn = SQL.getConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO batch(id, year, dep_id) VALUES (?, ?, ?)");
            stmt.setString(1, batchId);
            stmt.setString(2, year);
            stmt.setString(3, dep_id);
            stmt.executeUpdate();
            System.out.println("Batch added successfully");
            return true;
        } catch (SQLException e) {
            System.out.println("Error while adding batch: " + e.getMessage());
            return false;
        }
    }


    public static boolean delete(String batchId) throws SQLException {
//        try {
            //Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM batch WHERE id = ?");
            stmt.setString(1, batchId);
            stmt.executeUpdate();
            System.out.println("Batch deleted successfully");
            return true;
//        } catch (SQLException e) {
//            System.out.println("Error while deleting batch: " + e.getMessage());
//            return false;
//        }
    }
}
