package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class studentmenu {
    public static boolean handleStudentMenu() throws SQLException, IOException {
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.println("Enter your email:");
            String email = input.nextLine();
            System.out.println("Enter your password:");
            String password = input.nextLine();
            //faculty.login(email,password);
            boolean isLoggedIn = pupil.login(email,password);
            if (isLoggedIn) {
                break;
            } else {
                System.out.println("Invalid credentials. Please try again.\n");
            }
        }
        //System.out.println(pupil.user);
        while (pupil.user) {
            System.out.println("As a student, you have the following options,Enter");
            System.out.println("0. to logout");
            System.out.println("1. to view profile");
            System.out.println("2. to update profile");
            System.out.println("3. to view the offered courses");
            System.out.println("4. to add course");
            System.out.println("5. to delete Course");
            System.out.println("6. to view your courses");
            System.out.println("7. to view grades");
            System.out.println("8. to view your cgpa");
            System.out.println("9. to check graduation");
            String choice = input.nextLine();
            switch (choice) {
                case "0":
                    pupil.logout();
                    break;
                case "1":
                    String identity=pupil.userId;
                    pupil.viewProfile(identity);
                    break;
                case "2":
                    System.out.println("Enter name to update: ");
                    String name = input.nextLine();
                    System.out.println("Enter phone number to update: ");
                    String phoneNumber = input.nextLine();
                    System.out.println("Enter password to update: ");
                    String password = input.nextLine();
                    pupil.updateProfile(name,phoneNumber,password);
                    break;
                case "3":
                    pupil.offeredCourses();
                    break;
                case "4":
                    System.out.println("Enter the course ID or 0 to exit:");
                    String courseId = input.nextLine();
                    pupil.addCourse(courseId);
                    break;
                case "5":
                    System.out.println("Enter course_id to delete or 0 to exit");
                    String course_id = input.nextLine();
                    pupil.deleteCourse(course_id);
                    break;
                case "6":
                    pupil.mycourses();
                    break;
                case "7":
                    pupil.showGrades();
                    break;
                case "8":
                    // cgpa = pupil.getCgpa();
                    System.out.println(pupil.mycgpa);
                    System.out.println("press any key to continue");
                    input.nextLine();
                    break;
                case "9":
                    pupil.checkGraduationEligibility();
                    break;
                default:
                    System.out.println("Please enter a valid choice.");
                    break;
            }
        }
        return true;
    }
}
