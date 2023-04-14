package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class curriculum {
    static final Connection connection = SQL.getConnection();
    static Scanner input = new Scanner(System.in);
    public static boolean add(String courseId, String courseType, String batchId) {
        String insertCurriculumQuery = "INSERT INTO ug_curriculum(course_id, batch_id, course_type) VALUES (?, ?, ?);";

        try (PreparedStatement stmt = connection.prepareStatement(insertCurriculumQuery)) {
            // Set parameters for insert curriculum query
            stmt.setString(1, courseId);
            stmt.setString(2, batchId);
            stmt.setString(3, courseType);
            // Execute insert curriculum query
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Error adding curriculum: " + e.getMessage());
            return false;
        }
    }

    public static boolean delete(String courseId, String batchId) {
        String deleteCurriculumQuery = "DELETE FROM ug_curriculum WHERE course_id = ? AND batch_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(deleteCurriculumQuery)) {
            // Set parameters for delete curriculum query
            stmt.setString(1, courseId);
            stmt.setString(2, batchId);
            // Execute delete curriculum query
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("Error deleting from curriculum: " + e.getMessage());
            return false;
        }
    }
}
