package org.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class acadmenu {
    public static boolean handleAdminMenu() throws IOException, SQLException {
        // Create a new instance of admin
        acadsection acadsection = new acadsection();
        Scanner input = new Scanner(System.in);

        // Prompt user to enter their credentials to log in
        while (true) {
            System.out.print("Enter your username: ");
            acadsection.username = input.nextLine();

            System.out.print("Enter your password: ");
            acadsection.password = input.nextLine();

            boolean isLoggedIn = acadsection.login(acadsection.username,acadsection.password);

            if (isLoggedIn) {
                break;
            } else {
                System.out.println("Invalid credentials. Please try again.\n");
            }
        }

// User is now logged in
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("0. Logout");
            System.out.println("1. Add a new batch");
            System.out.println("2. Add a new course");
            System.out.println("3. Add or update a curriculum");
            System.out.println("4. Start semester");
            System.out.println("5. End semester");
            System.out.println("6. Add course to course catalog");
            System.out.println("7. View grades of all students");
            System.out.println("8. Add users");
            System.out.println("9. Generate student transcripts");
            System.out.println("10. View transcript");
            System.out.println("11. View courses");
            System.out.println("12. View users");

            String choice = input.nextLine();

            switch (choice) {
                case "0":
                    acadsection.logout();
                    break;

                case "1":
                    while (true) {
                        System.out.print("Enter batch id: ");
                        String batchId = input.nextLine();

                        System.out.print("Enter year: ");
                        String year = input.nextLine();

                        System.out.print("Enter department id: ");
                        String departmentId = input.nextLine();

                        acadsection.addBatch(batchId, year, departmentId);

                        System.out.print("Press 0 to exit or 1 to continue: ");
                        String exitChoice = input.nextLine();
                        if (exitChoice.equals("0")) {
                            break;
                        }
                    }
                    break;

                case "2":
                    while (true) {
                        System.out.print("Enter course id: ");
                        String courseId = input.nextLine();

                        System.out.print("Enter course name: ");
                        String courseName = input.nextLine();

                        System.out.print("Enter department id: ");
                        String departmentId = input.nextLine();

                        System.out.print("Enter number of lectures per week: ");
                        int numLectures = Integer.parseInt(input.nextLine());

                        System.out.print("Enter number of tutorials per week: ");
                        int numTutorials = Integer.parseInt(input.nextLine());

                        System.out.print("Enter number of course practicals per week: ");
                        int numPracticals = Integer.parseInt(input.nextLine());

                        System.out.print("Enter course credits: ");
                        int credits = Integer.parseInt(input.nextLine());

                        List<String> prerequisites = new ArrayList<>();
                        while (true) {
                            System.out.print("Enter the course code of a prerequisite course or 0 to exit: ");
                            String prerequisite = input.nextLine();
                            if (prerequisite.equals("0")) {
                                break;
                            }
                            prerequisites.add(prerequisite);
                        }

                        acadsection.addCourse(courseId, courseName, departmentId, numLectures, numTutorials, numPracticals, credits, prerequisites);

                        System.out.print("Press 0 to exit or 1 to continue: ");
                        String exitChoice = input.nextLine();
                        if (exitChoice.equals("0")) {
                            break;
                        }
                    }
                    break;

                case "3":
                    while (true) {
                        System.out.print("Enter course id or 0 to exit: ");
                        String courseId = input.nextLine();
                        if (courseId.equals("0")) {
                            break;
                        }
                        System.out.println("enter the batch_id ");
                        String batch_id = input.nextLine();
                        System.out.println("enter the course type");
                        String courseType = input.nextLine();
                        acadsection.addCurriculum(courseId, courseType, batch_id);
                    }

                    break;

                case "4":
                    String academicYear, semester;
                    System.out.println("Enter the academic year:");
                    academicYear = input.nextLine();
                    System.out.println("Enter the semester (monsoon/winter):");
                    semester = input.nextLine();
                    acadsection.startSemester(academicYear,semester);
                    break;
                case "5":
                    acadsection.endSemester();
                    break;
                case "6":
                    System.out.println("Enter course code or enter 0 to exit.");
                    String courseCode = input.nextLine();
                    acadsection.updateCourseCatalog(courseCode);
                    break;
                case "7":
                    acadsection.showGrades();
                    break;
                case "8":
                    System.out.println("Press:\n0 to exit\n1 to add student\n2 to add instructor");

                    String role = input.nextLine();
                    List<String> info=new ArrayList<String>();
                    if(role.equals("0")) break;
                    else if(role.equals("1"))
                    {
                        System.out.println("Enter name of the student");
                        info.add(input.nextLine());
                        System.out.println("Enter batch ID of the student");
                        info.add(input.nextLine());
                        System.out.println("Enter phone number of the student");
                        info.add(input.nextLine());
                    }
                    else{
                        //String name, depId, phoneNumber;
                        System.out.println("Enter name of the instructor");
                        info.add(input.nextLine());
                        System.out.println("Enter department ID of the instructor");
                        info.add(input.nextLine());
                        System.out.println("Enter phone number of the instructor");
                        info.add(input.nextLine());
                    }
                    acadsection.addUser(role,info);
                    break;
                case "9":
                    System.out.println("Keep the transcript file ready.");
                    System.out.println("Enter the student ID.");
                    String studentId = input.nextLine();
                    acadsection.submitTranscript(studentId);
                    break;
                case "10":
                    System.out.println("Enter the student id:");
                    String stuId = input.nextLine();
                    acadsection.viewTranscript(stuId);
                    break;
                case "11":
                    acadsection.viewCourses();
                    break;
                case "12":
                    int c=0;
                    System.out.println("Enter 1 for student and 2 for instructor:");
                    c = input.nextInt();
                    acadsection.viewUsers(c);
                    break;
                default:
                    System.out.println("please follow the instructions");
                    break;
            }

            break;
        }
        return true;
    }
}
