package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class facultymenu {
    public static boolean handleFacultyMenu() throws SQLException, IOException {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("Enter your email:");
            String email = input.nextLine();
            System.out.println("Enter your password:");
            String password = input.nextLine();
            //faculty.login(email,password);

            boolean isLoggedIn = faculty.login(email, password);

            if (isLoggedIn) {
                break;
            } else {
                System.out.println("Invalid credentials. Please try again.\n");
            }
        }

        while (faculty.user) {
            System.out.println("Press");
            System.out.println("0. to logout");
            System.out.println("1. to view profile");
            System.out.println("2. to update profile");
            System.out.println("3. to view the course catalog");
            System.out.println("4. to add course");
            System.out.println("5. to delete Course");
            System.out.println("6. to view your courses");
            System.out.println("7. to view grades of all students");
            System.out.println("8. to approve or disapprove enrollments");
            System.out.println("9. to submit grades");
            String choice = input.nextLine();
            switch (choice) {
                case "0":
                    faculty.logout();
                    break;
                case "1":
                    faculty.viewProfile(faculty.user_id);
                    break;
                case "2":
                    String name = "";
                    String password = "";
                    String phone_number = "";

                    System.out.println("Enter name to update: ");
                    name = input.nextLine();

                    System.out.println("Enter phone number to update: ");
                    phone_number = input.nextLine();

                    System.out.println("Enter password to update: ");
                    password = input.nextLine();
                    faculty.updateProfile(name, phone_number, password);
                    break;
                case "3":
                    faculty.offeredCourses();
                    break;
                case "4":
                    System.out.println("Enter the course ID or 0 to exit:");
                    String cid = input.nextLine();
                    System.out.println("Set the CGPA limit for this course:");
                    double cgpaLimit = input.nextDouble();
                    faculty.addCourse(cid, cgpaLimit);
                    break;
                case "5":
                    System.out.println("Enter the course ID to remove or 0 to exit:");
                    String courseId = input.nextLine();
                    faculty.removeCourse(courseId);
                    break;
                case "6":
                    faculty.displayMyCourses();
                    break;
                case "7":
                    faculty.showGrades();
                    break;
                case "8":
                    if (faculty.enrollmentRequests(faculty.user_id)) {
                        faculty.printRequests(faculty.requests);
                        System.out.println("Press any key to continue");
                        input.nextLine();
                        System.out.println("Approve or disapprove requests.");
                        while (true) {
                            System.out.println("Enter course_id or 0 to exit.");
                            String cId = input.nextLine();
                            if (cId.equals("0")) {
                                return true;
                            }
                            System.out.println("Enter student_id.");
                            String sId = input.nextLine();
                            String decision = "";
                            do {
                                System.out.println("Press 1 to approve or 2 to reject.");
                                decision = input.nextLine();
                            } while (!(decision.equals("1") || decision.equals("2")));

                            faculty.handleApprovalOrRejection(faculty.requests, sId, cId, decision);
                        }
                    }
                    break;
                case "9":
                    faculty.submitGrades();
                    break;
                default:
                    System.out.println("please follow the instructions");
                    break;
            }
        }
        return true;
    }
}
