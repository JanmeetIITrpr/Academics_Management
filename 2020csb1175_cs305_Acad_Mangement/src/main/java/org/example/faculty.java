package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class faculty {
    static Connection CONNECTION = SQL.getConnection();
    //static Statement stmt = null;
    static Scanner input = new Scanner(System.in);
    public static String user_id="";
    public static String token="'logged in'";

    public static String courseId="";

    public static boolean user=true;
    public static String key="";
    public static boolean login(String email, String password) throws SQLException, IOException {
        //Scanner input = new Scanner(System.in);
        boolean loggedIn = false;

        while (!loggedIn) {
                PreparedStatement stmt = CONNECTION.prepareStatement(
                        "SELECT * FROM faculty WHERE email = ? AND password = ?");
                stmt.setString(1, email);
                stmt.setString(2, password);
                key=password;

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String userId = rs.getString(1);
                    user_id = rs.getString(1);
                    loggedIn = true;
                    user= true;
                    PreparedStatement updateTokenStmt = CONNECTION.prepareStatement(
                            "UPDATE faculty SET token = ? WHERE id = ?");
                    updateTokenStmt.setString(1, token);
                    updateTokenStmt.setString(2, userId);
                    updateTokenStmt.executeUpdate();
                    System.out.println("Logged in successfully!");
                    String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                    String logMessage = "Instructor " + userId + " logged in on " + time + "\n";
                    writeLogToFile(logMessage);
                } else {
                    System.out.println("Wrong email or password. Please try again.");
                    return loggedIn;
                }
        }
        return true;
    }

    public static void logout() throws IOException, SQLException {
        user = false;
        String query = "update faculty set token='logged out' where id=?";
            PreparedStatement pstmt = CONNECTION.prepareStatement(query);
            pstmt.setString(1, user_id);
            pstmt.executeUpdate();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String time = dtf.format(now);
                String logMsg = "instructor " + user_id + " logged out on " + time + "\n";
                writeLogToFile(logMsg);
    }

    private static void writeLogToFile(String logMsg) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("log.txt", true))) {
            out.write(logMsg);
        }
    }

    public static boolean viewProfile(String user_id) throws SQLException {
        String query = "SELECT * FROM faculty WHERE id = ?;";
            PreparedStatement statement = CONNECTION.prepareStatement(query);
            statement.setString(1, user_id);
            System.out.println(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String department = rs.getString("dep_id");
                String phoneNumber = rs.getString("phone_number");

                String responseQuery = String.format(
                        "id ---> %s\n      name ---> %s\n      email ---> %s\n      dep_id ---> %s\n      phone_number ---> %s",
                        id, name, email, department, phoneNumber
                );
                System.out.println(responseQuery);
//                System.out.println("Press any key to continue");
//                input.nextLine();
            }
            return true;
        }



    public static boolean updateProfile(String name,String phone_number,String password) throws SQLException {
            PreparedStatement pstmt = CONNECTION.prepareStatement("UPDATE faculty SET name = ?, phone_number = ?, password = ? WHERE id = ?");
            pstmt.setString(1, name);
            pstmt.setString(2, phone_number);
            pstmt.setString(3, password);
            pstmt.setString(4, user_id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Profile updated successfully.");
            } else {
                System.out.println("Failed to update profile.");
            }
                key=password;
        return true;
    }


    public static boolean addCourse(String courseId,double cgpaLimit) throws SQLException {
        if(course.facultyAdd(courseId,cgpaLimit)) return true;
        else return false;
        }

    public static boolean offeredCourses() throws SQLException {
            String query = "SELECT * FROM course_catalog";
            PreparedStatement stmt = CONNECTION.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    if (i == 1) {
                        sb.append("Course ID: ");
                    }
                    String columnValue = rs.getString(i);
                    sb.append(columnValue);
                    if (i < columnCount) {
                        sb.append(", ");
                    }
                }
                sb.append("\n");
            }

            System.out.println(sb.toString());
//        } catch (SQLException e) {
//            System.out.println("Semester has not started yet.");
//        }
        return true;
    }

    public static boolean displayMyCourses() throws SQLException {
        String query = "SELECT * FROM course_offering WHERE instructor_id=?";
            PreparedStatement statement = CONNECTION.prepareStatement(query);
            statement.setString(1, user_id);
            ResultSet resultSet = statement.executeQuery();

            StringBuilder responseBuilder = new StringBuilder();

            while (resultSet.next()) {
                String courseId = resultSet.getString("course_id");
                double cgpaLimit = resultSet.getDouble("cgpa_limit");

                responseBuilder.append(String.format("Course ID: %s, CGPA Limit: %.2f\n", courseId, cgpaLimit));
            }

            String response = responseBuilder.toString();

            if (response.length() > 0) {
                System.out.println(response);
            } else {
                System.out.println("You have not offered any courses yet.");
            }
            return true;
    }

    public static boolean removeCourse(String courseId) throws SQLException {
        while (true) {
            if (courseId.equals("0")) {
                break;
            }
            String query = "DELETE FROM course_offering WHERE course_id=? AND instructor_id=?";
                PreparedStatement statement = CONNECTION.prepareStatement(query);
                statement.setString(1, courseId);
                statement.setString(2, user_id);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Course removed successfully.");
                } else {
                    System.out.println("You have not offered this course yet.");
                }
                return true;
        }
        return true;
    }

    public static boolean showGrades() throws SQLException {
            PreparedStatement ps = CONNECTION.prepareStatement("SELECT * FROM grades;");
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String responseQuery = "";
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rsmd.getColumnName(i);

                    if (i == 1) {
                        responseQuery += columnName + " ---> ";
                    } else {
                        responseQuery += "      " + columnName + " ---> ";
                    }

                    responseQuery += rs.getString(i) + " ";
                }

                responseQuery += "\n";
            }

            System.out.println(responseQuery);
        return true;
    }

    public static List<String[]> requests;
    public static boolean enrollmentRequests(String user_id) throws SQLException {
        requests = getPendingRequestsForInstructor(user_id);
        if (requests.isEmpty()) {
            System.out.println("No enrollment requests yet.");
            return true;
        }

//        printRequests(requests);
//
//        handleApprovalOrRejection(requests);

        return true;
    }

//    private static void waitForInput() {
//        System.out.println("Press any key to continue");
//        input.nextLine();
//    }

    private static List<String[]> getPendingRequestsForInstructor(String instructorId) throws SQLException {
        String query = "SELECT * FROM registration_status WHERE instructor_id=? AND status='pending instructor approval';";
            PreparedStatement stmt = CONNECTION.prepareStatement(query);
            stmt.setString(1, instructorId);
            ResultSet rs = stmt.executeQuery();
            List<String[]> requests = new ArrayList<>();
            while (rs.next()) {
                String[] request = new String[2];
                request[0] = rs.getString("course_id");
                request[1] = rs.getString("student_id");
                requests.add(request);
            }
            return requests;
    }

    public static boolean printRequests(List<String[]> requests) {
        StringBuilder sb = new StringBuilder();
        for (String[] request : requests) {
            sb.append("course_id ---> ").append(request[0]);
            sb.append("      student_id ---> ").append(request[1]);
            sb.append("\n");
        }
        System.out.println(sb.toString());
        return true;
    }

    public static boolean handleApprovalOrRejection(List<String[]> requests, String studentId,String courseId,String decision) throws SQLException {
        //System.out.println("Approve or disapprove requests.");
        while (true) {
            updateStatus(courseId, studentId, decision.equals("1") ? "approved by the instructor" : "rejected by the instructor");
            return true;
        }
    }
    private static boolean updateStatus(String courseId, String studentId, String status) throws SQLException {
        String query = "UPDATE registration_status SET status=? WHERE course_id=? AND student_id=?;";
            PreparedStatement stmt = CONNECTION.prepareStatement(query);
            stmt.setString(1, status);
            stmt.setString(2, courseId);
            stmt.setString(3, studentId);
            stmt.executeUpdate();
            return true;
        }


    public static boolean submitGrades() throws IOException, SQLException {
        String csvFilePath = "src/main/resources/grades.csv";

        try (BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath))) {
            lineReader.readLine(); // skip header line
            int count = 0;
            while (true) {
                String lineText = lineReader.readLine();
                if (lineText == null) {
                    break;
                }
                String[] data = lineText.split(",");
                if (data.length != 5) {
                    System.out.println("Some lines were buggy");
                    continue;
                }
                String studentId = data[0];
                courseId = data[1];
                String grade = data[2];
                String semester = data[3];
                String academicYear = data[4];

                try (PreparedStatement statement = CONNECTION.prepareStatement(
                        "INSERT INTO grades (student_id, instructor_id, course_id, grade, semester, academic_year) " +
                                "VALUES (?, ?, ?, ?, ?, ?)")) {
                    statement.setString(1, studentId);
                    statement.setString(2, user_id);
                    statement.setString(3, courseId);
                    statement.setString(4, grade);
                    statement.setString(5, semester);
                    statement.setString(6, academicYear);
                    statement.execute();
                    count++;
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to insert grades into database", e);
                }
            }
            if (count == 0) {
                System.out.println("Please enter some data in the file");
                return true;
            }

            String query = "SELECT student_id FROM registration_status WHERE course_id = ?";
            PreparedStatement statement = CONNECTION.prepareStatement(query);
                statement.setString(1, courseId);
                ResultSet rs = statement.executeQuery();
                //disabled for testing to add multiple grades , uncomment when running App
//                while (rs.next()) {
//                    String studentId = rs.getString(1);
//                    query = "SELECT COUNT(*) FROM grades WHERE student_id = ? AND course_id = ?";
//                    try (PreparedStatement statement2 = CONNECTION.prepareStatement(query)) {
//                        statement2.setString(1, studentId);
//                        statement2.setString(2, courseId);
//                        ResultSet rs2 = statement2.executeQuery();
//                        rs2.next();
//                        int gradeCount = rs2.getInt(1);
//                        if (gradeCount == 0) {
//                            System.out.println("No grade has been submitted for student with ID " + studentId);
//                            query = "DELETE FROM grades WHERE instructor_id = ? AND course_id = ?";
//                            try (PreparedStatement statement3 = CONNECTION.prepareStatement(query)) {
//                                statement3.setString(1, user_id);
//                                statement3.setString(2, courseId);
//                                statement3.executeUpdate();
//                            }
//                        } else if (gradeCount > 1) {
//                            System.out.println("More than 1 grade has been submitted for student with ID " + studentId);
//                            query = "DELETE FROM grades WHERE instructor_id = ? AND course_id = ?";
//                            try (PreparedStatement statement3 = CONNECTION.prepareStatement(query)) {
//                                statement3.setString(1, user_id);
//                                statement3.setString(2, courseId);
//                                statement3.executeUpdate();
//                            }
//                        }
//                    }
//                }

            System.out.println("Grades submitted successfully");
        }
        return true;
    }
}


