package org.example;

import java.util.HashSet;
import java.util.Scanner;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Set;

public class pupil {
    private static final Connection connection = SQL.getConnection();

    static Statement statement = null;
    private static final Scanner scanner = new Scanner(System.in);

    public static String userId = "";
    public static String batch_id = "";
    public static int credits = 0;
    public static boolean user = false;
    public static double mycgpa=0.0;

//    private static final String LOGIN_SUCCESS_MSG = "Logged in successfully.";
//    private static final String INVALID_CREDENTIALS_MSG = "Invalid email or password.";
    private static final String LOG_FILE_NAME = "log.txt";
    //private static final String LOG_MSG_FORMAT = "student %s logged in on %s\n";

    private static PreparedStatement updateTokenStatement = null;
    private static PreparedStatement selectStudentByIdStatement = null;
    private static String ui = "-1";
    public static boolean userLoggedIn = false;

    public static boolean login(String email,String password) throws SQLException, IOException {
                PreparedStatement loginStatement = connection.prepareStatement(
                        "SELECT * FROM student WHERE email = ? AND password = ?"
                );
                loginStatement.setString(1, email);
                loginStatement.setString(2, password);
                ResultSet rs = loginStatement.executeQuery();

                if (rs.next()) {
                    ui = rs.getString("id");
                    userId=ui;
                    userLoggedIn = true;
                    user=true;
                    updateTokenStatement = connection.prepareStatement(
                            "UPDATE student SET token = 'logged in' WHERE id = ?"
                    );
                    updateTokenStatement.setString(1, ui);

                    String logMessage = "student " + userId + " logged in on " + LocalDateTime.now();
                    writeToLog(logMessage);

                    System.out.println("Logged in successfully!");
                } else {
                    System.out.println("Wrong credentials.");
//                    System.out.println("Press any key to continue.");
//                    scanner.nextLine();
                    return false;
                }
        //}
        return true;
    }

    public static void logout() throws SQLException, IOException {
            updateTokenStatement.setString(1, "logged out");
            updateTokenStatement.executeUpdate();

            String logMessage = "student " + userId + " logged out on " + LocalDateTime.now();
            writeToLog(logMessage);

            ui = "-1";
            userLoggedIn = false;
            user=false;

            System.out.println("Logged out successfully!");
    }

    public static boolean viewProfile(String userId) throws SQLException {
            if (selectStudentByIdStatement == null) {
                selectStudentByIdStatement = connection.prepareStatement(
                        "SELECT id, name, batch_id, email, phone_number, credits FROM student WHERE id = ?"
                );
            }
            selectStudentByIdStatement.setString(1, userId);
            ResultSet rs = selectStudentByIdStatement.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                batch_id = rs.getString("batch_id");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                int credits = rs.getInt("credits");

                String responseQuery = String.format(
                        "id ---> %s\nname ---> %s\nbatch_id ---> %s\nemail ---> %s\nphone_number ---> %s\ncredits ---> %d",
                        id, name, batch_id, email, phoneNumber, credits
                );
                System.out.println(responseQuery);
            }

        return true;
    }
    public static boolean updateProfile(String name,String phoneNumber,String password) throws SQLException {
        // check if user is logged in
        if(!user) return false;

        String query = "UPDATE student SET name = ?, phone_number = ?, password = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, userId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Profile updated successfully!");
            } else {
                System.out.println("No profile updated!");
            }
        return true;
    }

    public static boolean addCourse(String courseId) throws SQLException {
//        if (!userLoggedIn) {
//            return false;
//        }
        while (true) {
            if (courseId.equals("0")) {
                return true;
            }
                String courseOfferingQuery = "SELECT cgpa_limit, instructor_id FROM course_offering WHERE course_id = ?";
                PreparedStatement courseOfferingStatement = connection.prepareStatement(courseOfferingQuery);
                courseOfferingStatement.setString(1, courseId);
                ResultSet courseOfferingResultSet = courseOfferingStatement.executeQuery();

                if (!courseOfferingResultSet.next()) {
                    System.out.println("No such course is offered.");
                    //scanner.nextLine();
                    return true;
                }

                double cgpaLimit = courseOfferingResultSet.getDouble("cgpa_limit");
                String instructorId = courseOfferingResultSet.getString("instructor_id");

                if (isCgpaLessThanTwo() && getCgpa(userId) < cgpaLimit) {
                    System.out.println("You cannot take this course as your CGPA is less than required " + cgpaLimit + ".");
                    return true;
                }

                String courseQuery = "SELECT c FROM course WHERE id = ?";
                PreparedStatement courseStatement = connection.prepareStatement(courseQuery);
                courseStatement.setString(1, courseId);
                ResultSet courseResultSet = courseStatement.executeQuery();

                if (courseResultSet.next()) {
                    int credits = courseResultSet.getInt("c");

                    if (credits + getCreditsTakenThisSemester() > 24) {
                        System.out.println("Your credit limit has exceeded for this semester.");
                        return true;
                    }
                }

                String prerequisitesQuery = "SELECT prerequisites.course_id FROM prerequisites LEFT JOIN grades ON prerequisites.prerequisite_id = grades.course_id AND grades.student_id = ? WHERE prerequisites.course_id = ?";
                PreparedStatement prerequisitesStatement = connection.prepareStatement(prerequisitesQuery);
                prerequisitesStatement.setString(1, userId);
                prerequisitesStatement.setString(2, courseId);
                ResultSet prerequisitesResultSet = prerequisitesStatement.executeQuery();

                if (prerequisitesResultSet.next()) {
                    System.out.println("You must complete the course " + prerequisitesResultSet.getString("course_id") + " first to register this course.");
                    //scanner.nextLine();
                    //continue;
                }

                String registrationStatusQuery = "INSERT INTO registration_status (course_id, student_id, instructor_id, status) VALUES (?, ?, ?, 'pending instructor approval')";
                PreparedStatement registrationStatusStatement = connection.prepareStatement(registrationStatusQuery);
                registrationStatusStatement.setString(1, courseId);
                registrationStatusStatement.setString(2, userId);
                registrationStatusStatement.setString(3, instructorId);
                registrationStatusStatement.executeUpdate();

                System.out.println("Course added successfully.");

            return true;
        }
        //return true;
    }


    public static boolean offeredCourses() throws SQLException {
        String query = "SELECT co.course_id, co.cgpa_limit, uc.course_type, co.instructor_id FROM ug_curriculum uc JOIN course_offering co ON uc.course_id = co.course_id WHERE uc.batch_id = ?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, batch_id);
            ResultSet rs = ps.executeQuery();
            StringBuilder responseQuery = new StringBuilder();
            while (rs.next()) {
                responseQuery.append("course_id ---> ").append(rs.getString("course_id")).append(" ")
                        .append("cgpa_limit ").append(rs.getDouble("cgpa_limit")).append(" ")
                        .append("course_type ---> ").append(rs.getString("course_type")).append(" ")
                        .append("instructor_id ---> ").append(rs.getString("instructor_id")).append("\n");
            }
            System.out.println(responseQuery);
//            System.out.println("Press any key to continue");
//            scanner.nextLine();
        return true;
    }

    public static boolean mycourses() throws SQLException {
        String query = "SELECT course_id, instructor_id, status FROM registration_status WHERE student_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
                ResultSet rs = statement.executeQuery();
                if (!rs.isBeforeFirst()) System.out.println("You have no courses.");


                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                StringBuilder responseQuery = new StringBuilder();
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i == 1) {
                            responseQuery.append("Course ID: ");
                        } else if (i == 2) {
                            responseQuery.append("Instructor ID: ");
                        } else if (i == 3) {
                            responseQuery.append("Status: ");
                        }
                        responseQuery.append(rs.getString(i)).append(" ");
                    }
                    responseQuery.append("\n");
                }
                System.out.println(responseQuery);
        return true;
    }

    public static boolean deleteCourse(String course_id) throws SQLException {
        while (true) {
            if (course_id.equals("0")) {
                return true;
            }
            String query = "DELETE FROM registration_status WHERE course_id=? AND student_id=?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, course_id);
                preparedStatement.setString(2, userId);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Course " + course_id + " has been successfully deleted.");
                } else {
                    System.out.println("You have not registered for course " + course_id + " yet.");
                }
            return true;
        }
    }


    public static boolean showGrades() throws SQLException {
        String query = "SELECT * FROM grades WHERE student_id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            String responseQuery = "";
            int f = 0;
            while (rs.next()) {
                f++;
                for (int i = 1; i <= columnsNumber; i++) {
                    switch (i) {
                        case 1:
                            responseQuery += "student_id ---> ";
                            break;
                        case 2:
                            responseQuery += "instructor_id ---> ";
                            break;
                        case 3:
                            responseQuery += "course_id ---> ";
                            break;
                        case 4:
                            responseQuery += "grade ---> ";
                            break;
                        case 5:
                            responseQuery += "semester ---> ";
                            break;
                        case 6:
                            responseQuery += "academic_year ---> ";
                            break;
                        default:
                            break;
                    }
                    String columnValue = rs.getString(i);
                    responseQuery += columnValue + "          ";
                }
                responseQuery += "\n\n";
            }
            if (f == 0) {
                System.out.println("No grades to show yet");
            } else {
                System.out.println(responseQuery);
            }
        return true;
//        System.out.println("Press any key to continue");
//        scanner.nextLine();
    }


    public static boolean gimmeCg(String userId) throws SQLException {
        mycgpa=getCgpa(userId);
        return true;
    }
    private static double getCgpa(String userId) throws SQLException {
        String query = "SELECT g.course_id, g.grade, c.c FROM grades g JOIN course c ON g.course_id=c.id WHERE g.student_id=?";
        double cgpa = 0.0;
        //double credits = 0;
        double qualityPoints = 0.0;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet rs = statement.executeQuery();
            double netcredits=0.0;
            while (rs.next()) {
                int credits = rs.getInt(3);
                String grade = rs.getString(2);
                netcredits+=credits;
                switch (grade) {
                    case "A":
                        qualityPoints += 10*credits;
                        break;
                    case "A-":
                        qualityPoints += 9*credits ;
                        break;
                    case "B":
                        qualityPoints += 8*credits  ;
                        break;
                    case "B-":
                        qualityPoints += 7*credits  ;
                        break;
                    case "C":
                        qualityPoints += 6*credits  ;
                        break;
                    case "C-":
                        qualityPoints += 5*credits ;
                        break;
                    case "D":
                        qualityPoints += 4*credits ;
                        break;
                    case "E":
                        qualityPoints += 2*credits ;
                        break;
                    case "F":
                        qualityPoints += 0*credits;
                        break;
                }
                //System.out.println(credits +" " + qualityPoints);

            }
            if (netcredits > 0) {
                cgpa = qualityPoints / netcredits;
            }
        return cgpa;
    }




    public static boolean checkGraduationEligibility() throws SQLException {
        String coreCourseQuery = "SELECT course_id FROM ug_curriculum WHERE course_type = 'core'";
        String studentGradeQuery = "SELECT course_id FROM grades WHERE student_id = ? AND grade <> 'F'";
        int numberOfCoreCourses = 0, numberOfFailedCourses = 0;

            PreparedStatement coreCourseStmt = connection.prepareStatement(coreCourseQuery);
            ResultSet coreCourseRs = coreCourseStmt.executeQuery();
            PreparedStatement studentGradeStmt = connection.prepareStatement(studentGradeQuery);
            studentGradeStmt.setString(1, userId);
            ResultSet studentGradeRs = studentGradeStmt.executeQuery();

            Set<String> coreCourses = new HashSet<>();
            while (coreCourseRs.next()) {
                coreCourses.add(coreCourseRs.getString("course_id"));
            }

            while (studentGradeRs.next()) {
                if (coreCourses.contains(studentGradeRs.getString("course_id"))) {
                    numberOfCoreCourses++;
                } else {
                    numberOfFailedCourses++;
                }
            }

            coreCourseStmt.close();
            studentGradeStmt.close();

            if (numberOfCoreCourses == coreCourses.size() && numberOfFailedCourses == 0) {
                System.out.println("Congratulations! You are eligible for graduation.");
            } else {
                System.out.println("Sorry, you are not eligible for graduation.");
            }
            return true;
        }

    private static boolean isCgpaLessThanTwo () throws SQLException {
        String query="select count(*) from grades where student_id='"+userId+"' GROUP BY academic_year,semester;";
        int f=0;

            statement = connection.createStatement();
            ResultSet rs= statement.executeQuery(query);
            while(rs.next()) {
                f++;
            }
        if(f<2)return true;
        else return false;
    }


    private static int getCreditsTakenThisSemester() throws SQLException {
        String query = "SELECT SUM(c) FROM registration_status JOIN course ON registration_status.course_id=course.id WHERE student_id='" + userId + "' AND status='approved';";
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return 0;
        }
    }
    private static void writeToLog(String message) throws IOException {
            BufferedWriter out = new BufferedWriter(new FileWriter(LOG_FILE_NAME, true));
            out.write(message + "\n");
            out.close();
    }

}
