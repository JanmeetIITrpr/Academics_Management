package org.example;

import java.sql.*;
import java.util.Scanner;


public class App {

      static Connection CONNECTION = SQL.getConnection();
      public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        int f=1;
        while (f==1) {
            System.out.println("Welcome to the IIT Ropar's academics management portal :)");
            System.out.println("Please enter your user role");
            System.out.println("0. to exit ");
            System.out.println("1. Student");
            System.out.println("2. Faculty");
            System.out.println("3. Academic Section");
            String role = input.nextLine();

            switch (role) {
                case "0":f=0;
                break;
                case "1":
                    studentmenu.handleStudentMenu();
                    break;
                case "2":
                    facultymenu.handleFacultyMenu();
                    break;
                case "3":
                    acadmenu.handleAdminMenu();
                    //System.out.println("fff");
                    break;
                default:
                    System.out.println("Invalid role entered. Please try again.");
                    break;
            }
        }
    }

    //testing stay logged in feature
//    public static String getLoggedInUserRole() {
//        String role;
//        while (true){
//            int fs=0,fi=0;
//            role = "";
//            try (Statement stmt = CONNECTION.createStatement()) {
//                String query = "SELECT * FROM student WHERE token = 'logged in';";
//                ResultSet rs = stmt.executeQuery(query);
//                while (rs.next()) {
//                    pupil.user = true;
//                    pupil.userId = rs.getString(1);
//                    pupil.batch_id = rs.getString(3);
//                    pupil.credits = rs.getInt(7);
//                    role = "1";
//                    fs++;
//                }
//                if(fs==0) {
//                    query = "SELECT * FROM faculty WHERE token = 'logged in';";
//                    rs = stmt.executeQuery(query);
//                    while (rs.next()) {
//                        faculty.user = true;
//                        faculty.user_id = rs.getString(1);
//                        role = "2";
//                        fi++;
//                    }
//                }
//
//                if(fs==0 && fi==0){
//                    System.out.println("Enter your role");
//                    System.out.println("0. to exit ");
//                    System.out.println("1. student");
//                    System.out.println("2. faculty");
//                    System.out.println("3. admin");
//
//                    role=input.nextLine();
//                    if(role.equals("0")) break ;
//                }
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            return role;
//        }
//        return role;
//    }

}



