package org.example;

import java.sql.*;
import java.util.Scanner;

public class sem {

    static final Connection connection = SQL.getConnection();
    static Scanner input = new Scanner(System.in);
    public static boolean start(String academicYear, String semester) throws SQLException {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM semester;");
            rs.next();
            int rowCount = rs.getInt(1);
            if (rowCount > 0) {
                System.out.println("A semester is already running.");
//                System.out.println("Press any key to continue.");
//                input.nextLine();
            }


//        String catalogue_query = "CREATE TABLE course_catalog(\n" +
//                "course_id VARCHAR(10),\n" +
//                "PRIMARY KEY(course_id),\n" +
//                "FOREIGN KEY (course_id) references course (id)\n" +
//                ");";
//        String offer_query = "CREATE TABLE course_offering(\n" +
//                "course_id VARCHAR(10),\n" +
//                "cgpa_limit INTEGER,\n" +
//                "instructor_id VARCHAR(10),\n" +
//                "PRIMARY KEY(course_id),\n" +
//                "FOREIGN KEY (course_id) references course_catalog (course_id),\n" +
//                "FOREIGN KEY (instructor_id) references faculty (id)\n" +
//                ");";
//        String sem_query = "CREATE TABLE semester(\n" +
//                "academic_year VARCHAR(10),\n" +
//                "semester VARCHAR(10)\n" +
//                ");";
//        String reg_query = "CREATE TABLE registration_status(\n" +
//                "course_id VARCHAR(10),\n" +
//                "student_id VARCHAR(10),\n" +
//                "instructor_id VARCHAR(10),\n" +
//                "status VARCHAR(100),\n" +
//                "FOREIGN KEY (course_id) references course_offering (course_id),\n" +
//                "FOREIGN KEY (student_id) references student (id),\n" +
//                "FOREIGN KEY (instructor_id) references faculty (id)\n" +
//                ");";
//        String up_query = "UPDATE student SET credits = 0";

            //Statement stmt = connection.createStatement();
//                stmt.execute(catalogue_query);
//                stmt.execute(offer_query);
//                stmt.execute(sem_query);
//                stmt.execute(reg_query);

                String query = "INSERT INTO semester(academic_year,semester) VALUES (?, ?);";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, academicYear);
                pstmt.setString(2, semester);
                pstmt.executeUpdate();
                //return true;


        System.out.println(academicYear + "-" + semester + " started successfully.");
        System.out.println("Please add courses to the current semester's course catalog.");
//        System.out.println("Press any key to continue.");
//        input.nextLine();
        return true;
    }

    public static String AY = "";
    public static String SEM = "";
    public static String view()
    {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM semester");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                AY = rs.getString(1);
                SEM = rs.getString(2);
            }
            ps.close();
            rs.close();
            return "Year: "+ AY +" Semester: " + SEM;
        } catch (SQLException e) {
            return "No semester is running.";
        }
    }

    public static boolean end() {
        if(sem.view().equals("No semester is running.")) return false;
        else{
            try {
                PreparedStatement ps1 = connection.prepareStatement("TRUNCATE TABLE course_catalog CASCADE");
                PreparedStatement ps2 = connection.prepareStatement("TRUNCATE TABLE course_offering CASCADE");
                PreparedStatement ps3 = connection.prepareStatement("TRUNCATE TABLE registration_status CASCADE");
                PreparedStatement ps4 = connection.prepareStatement("TRUNCATE TABLE semester CASCADE");
                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();
                ps4.executeUpdate();
                ps1.close();
                ps2.close();
                ps3.close();
                ps4.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println(AY + "-" + SEM + " ended successfully.");
//            System.out.println("Press any key to continue.");
//            input.nextLine();
            return true;
        }

    }

}
