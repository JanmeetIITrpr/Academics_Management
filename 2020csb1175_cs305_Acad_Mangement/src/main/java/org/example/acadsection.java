package org.example;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class acadsection {
    static Connection connection = SQL.getConnection();
    //static Statement stmt = null;
    static Scanner input = new Scanner(System.in);

    public  String username="",password="";
    public static boolean user=true;

    public static boolean login(String username,String password) throws IOException {
        while(true){
            if (isValidCredentials(username, password)) {
                    writeLoginLog();
                    System.out.println("logged in successfully");
                    user=true;
            } else {
                System.out.println("wrong credentials");
                return false;
            }
            return true;
        }
    }

    private static boolean isValidCredentials(@NotNull String username, String password) {
        return username.equals("admin") && password.equals("iitropar");
    }

    private static void writeLoginLog() throws IOException {
        FileWriter fileWriter = new FileWriter("log.txt", true);
        BufferedWriter out = new BufferedWriter(fileWriter);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        String query = "admin logged in on " + time + "\n";
        //String query2 = "admin logged out on " + time + "\n";
        //if(login()) out.write(query);
        out.write(query);
        //else out.write(query2);
        out.close();
    }


    public static void logout() throws IOException {
        writeLogoutLog();
        System.out.println("logged out successfully");
        user = false;
    }
    private static void writeLogoutLog() throws IOException {
        FileWriter fileWriter = new FileWriter("log.txt", true);
        BufferedWriter out = new BufferedWriter(fileWriter);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        String query = "admin logged out on " + time + "\n";
        out.write(query);
        out.close();
    }

    public static boolean showGrades() throws SQLException {
        String query = "SELECT student_id, course_id, Grade, semester, academic_year FROM grades";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    String columnValue = rs.getString(i);
                    sb.append(columnName).append(" ---> ").append(columnValue).append("    ");
                }
                sb.append(System.lineSeparator());
            }
            System.out.println(sb.toString());
//            System.out.println("Press any key to continue");
//            input.nextLine();
            return true;
        }



    public static boolean updateCourseCatalog(String courseCode) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM semester");
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            System.out.println("Semester is not yet started.");
            return false;
        }
        if (courseCode.equals("0")) {
            return true;
        }

            ps = connection.prepareStatement("INSERT INTO course_catalog (course_id) VALUES (?)");
            ps.setString(1, courseCode);
            ps.executeUpdate();
            System.out.println("Course added successfully.");
            return true;
        }

    public static boolean deleteFromCourseCatalog(String courseCode) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM semester");
        ResultSet rs = ps.executeQuery();
        //Semester running check-use while App
//        if (!rs.next()) {
//            System.out.println("Semester is not yet started.");
//            return false;
//        }
        while (true) {
//            System.out.println("Enter course code or enter 0 to exit.");
//            String c = input.nextLine();
//            if (c.equals("0")) {
//                break;
//            }
//            else c=courseCode;
                ps = connection.prepareStatement("DELETE FROM course_catalog WHERE course_id=?");
                ps.setString(1, courseCode);
                ps.executeUpdate();
                System.out.println("Course deleted successfully.");
                return true;
            }
    }

    public static boolean addBatch(String batchId, String year, String dep_id) {
        if(batch.add(batchId,year,dep_id)) return true;
        else return false;
    }

    public boolean deleteBatch(String batchId) throws SQLException {
        if(batch.delete(batchId)) return true;
        else return false;
    }

    public static boolean addCurriculum(String courseId, String courseType, String batchId) {
        if(curriculum.add(courseId,courseType,batchId)) return true;
        else return false;
    }

    public boolean deleteFromCurriculum(String courseId, String batchId) {
        if(curriculum.delete(courseId,batchId)) return true;
        else return false;
    }

    public static boolean addCourse(String courseId, String courseName, String depId, int l, int t, int p, int c, List<String> prereq) {
        if(course.add(courseId, courseName, depId, l, t, p, c, prereq))
            return true;
        else return false;
    }

    public boolean deleteCourse(String courseId) {
        if(course.delete(courseId)) return true;
        else return false;
    }
    public static String addUser(String role, List<String> info) throws SQLException {
        return users.add(role,info);
    }

    public static boolean deleteUser(String role, String id) {
      if(users.delete(role,id)) return true;
      else return false;
    }

    public static boolean startSemester(String academicYear, String semester) throws SQLException {
        if(sem.start(academicYear,semester)) return true;
        else return false;
    }

    public static String viewSemester()
    {
        return sem.view();
    }

    public static boolean endSemester() {
        if(sem.end()) return true;
        else return false;
    }


    public static boolean submitTranscript(String studentId) throws FileNotFoundException, SQLException {
        if(transcript.submit(studentId)) return true;
        else return false;
    }


    public static boolean viewTranscript(String studentId) {
        if(transcript.view(studentId)) return true;
        else return false;
    }


    public static boolean viewCourses() {
        if(course.view()) return true;
        else return false;
    }

    public static boolean viewUsers(Integer choice) throws SQLException {
        if(users.view(choice)) return true;
        else return false;
    }

}
