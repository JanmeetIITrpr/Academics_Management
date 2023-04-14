package org.example;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class course {

    static final Connection connection = SQL.getConnection();
    static Scanner input = new Scanner(System.in);

    public static boolean add(String courseId, String courseName, String depId, int l, int t, int p, int c, List<String> prereq) {
        String insertCourseQuery = "INSERT INTO course(id, name, dep_id, l, t, p, c) VALUES (?, ?, ?, ?, ?, ?, ?);";
        String insertPrereqQuery = "INSERT INTO prerequisites(course_id, prerequisite_id) VALUES (?, ?);";

        try (PreparedStatement stmt = connection.prepareStatement(insertCourseQuery);
             PreparedStatement prereqStmt = connection.prepareStatement(insertPrereqQuery)) {
            // Set parameters for insert course query
            stmt.setString(1, courseId);
            stmt.setString(2, courseName);
            stmt.setString(3, depId);
            stmt.setInt(4, l);
            stmt.setInt(5, t);
            stmt.setInt(6, p);
            stmt.setInt(7, c);
            // Execute insert course query
            stmt.executeUpdate();

            // Set parameters for insert prerequisites query
            for (String pre : prereq) {
                prereqStmt.setString(1, courseId);
                prereqStmt.setString(2, pre);
                // Execute insert prerequisites query
                prereqStmt.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            System.out.println("Error adding course: " + e.getMessage());
            return true;
        }
    }

    public static boolean delete(String courseId) {
        String deleteCourseQuery = "DELETE FROM course WHERE id = ?;";
        String deletePrereqQuery = "DELETE FROM prerequisites WHERE course_id = ? OR prerequisite_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(deleteCourseQuery);
             PreparedStatement prereqStmt = connection.prepareStatement(deletePrereqQuery)) {
            // Set parameters for course deletion query
            stmt.setString(1, courseId);
            // Execute course deletion query
            stmt.executeUpdate();

             //Set parameters for prerequisites deletion query
            prereqStmt.setString(1, courseId);
            prereqStmt.setString(2, courseId);
            // Execute prerequisites deletion query
            prereqStmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting course: " + e.getMessage());
            return true;
        }
        return true;
    }

    public static boolean view() {
        String query = "SELECT id, name, dep_id, l, t, p, c FROM course";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int numColumns = rsmd.getColumnCount();
            StringBuilder responseQuery = new StringBuilder();
            while (rs.next()) {
                for (int i = 1; i <= numColumns; i++) {
                    switch (i) {
                        case 1:
                            responseQuery.append("course_id ---> ");
                            break;
                        case 2:
                            responseQuery.append("name ---> ");
                            break;
                        case 3:
                            responseQuery.append("dep_id ---> ");
                            break;
                        case 4:
                            responseQuery.append("l ---> ");
                            break;
                        case 5:
                            responseQuery.append("t ---> ");
                            break;
                        case 6:
                            responseQuery.append("p ---> ");
                            break;
                        case 7:
                            responseQuery.append("c ---> ");
                            break;
                        default:
                            // do nothing
                    }
                    String columnValue = rs.getString(i);
                    responseQuery.append(columnValue).append(" ");
                }
                responseQuery.append("\n");
            }
            System.out.println(responseQuery.toString());
//            System.out.println("Press any key to continue");
//            input.nextLine();
            //return true;
        } catch (SQLException e) {
            System.out.println("No courses to show");
            //throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean facultyAdd(String courseId,double cgpaLimit) throws SQLException {
        while (true) {

            if (courseId.equals("0")) break;


            String catalogQuery = "SELECT * FROM course_catalog WHERE course_id=?";
            PreparedStatement catalogStmt = connection.prepareStatement(catalogQuery);
            catalogStmt.setString(1, courseId);

            ResultSet catalogRs = catalogStmt.executeQuery();

            while (!catalogRs.next()) {
                System.out.println("Course not found in the course catalog.");
                return false;
            }

            String offeringQuery = "SELECT instructor_id FROM course_offering WHERE course_id=?";
            PreparedStatement offeringStmt = connection.prepareStatement(offeringQuery);
            offeringStmt.setString(1, courseId);

            ResultSet offeringRs = offeringStmt.executeQuery();

            if (offeringRs.next()) {
                System.out.println("This course is already offered by instructor " + offeringRs.getString(1) + ".");
                return true;
            }


            String insertQuery = "INSERT INTO course_offering(course_id, cgpa_limit, instructor_id) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
            insertStmt.setString(1, courseId);
            insertStmt.setDouble(2, cgpaLimit);
            insertStmt.setString(3, faculty.user_id);

            int rowsAffected = insertStmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Course added successfully.");
            } else {
                System.out.println("Course could not be added.");
                return false;
            }

        }
        return true;
    }
}




