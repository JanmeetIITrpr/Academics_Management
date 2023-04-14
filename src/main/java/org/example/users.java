package org.example;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class users {
    static final Connection connection = SQL.getConnection();

    public static String add(String role, List<String> info) throws SQLException {
        while (true) {
            switch (role) {
                case "0": {
                    return "wrong_input";
                }
                case "1": {
                    String name, batchId, phoneNumber;
                    name = info.get(0);
                    batchId = info.get(1);
                    phoneNumber = info.get(2);

                        PreparedStatement preparedStatement = connection.prepareStatement(
                                "SELECT COUNT(*) FROM student WHERE batch_id = ?;"
                        );
                        preparedStatement.setString(1, batchId);

                        ResultSet resultSet = preparedStatement.executeQuery();

                        int count =0;
                        while (resultSet.next())
                            count=resultSet.getInt(1);
                        String id = batchId + count;

                        preparedStatement = connection.prepareStatement(
                                "INSERT INTO student (id, name, batch_id, email, password, phone_number, credits, token) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, 0, '');"
                        );
                        preparedStatement.setString(1, id);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, batchId);
                        preparedStatement.setString(4, id + "@iitrpr.ac.in");
                        preparedStatement.setString(5, "iitropar");
                        preparedStatement.setString(6, phoneNumber);

                        preparedStatement.executeUpdate();
                        return id;

                    //break;
                }
                case "2": {
                    String name, depId, phoneNumber;
                    //System.out.println("Enter name of the instructor");
                    name = info.get(0);
                    //System.out.println("Enter department ID of the instructor");
                    depId = info.get(1);
                    //System.out.println("Enter phone number of the instructor");
                    phoneNumber = info.get(2);
                        PreparedStatement preparedStatement = connection.prepareStatement(
                                "SELECT COUNT(*) FROM faculty WHERE dep_id = ?;"
                        );
                        preparedStatement.setString(1, depId);

                        ResultSet resultSet = preparedStatement.executeQuery();
                        int count=0;
                        while(resultSet.next())
                            count = resultSet.getInt(1);
                        String id = depId + count;

                        preparedStatement = connection.prepareStatement(
                                "INSERT INTO faculty (id, name, dep_id, email, password, phone_number, token) " +
                                        "VALUES (?, ?, ?, ?, ?, ?, '');"
                        );
                        preparedStatement.setString(1, id);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, depId);
                        preparedStatement.setString(4, id + "@iitrpr.ac.in");
                        preparedStatement.setString(5, "iitropar");
                        preparedStatement.setString(6, phoneNumber);

                        preparedStatement.executeUpdate();
                        return id;
                    //break;
                }
                default: {
                    System.out.println("Please follow the instructions");
                }
            }
        }
    }

    public static boolean delete(String role, String id) {
        while(true){
            switch(role){
                case("0"): return true;
                case("1"):
                {
                    String deleteStudentQuery = "DELETE FROM student WHERE id = ?;";
                    try(PreparedStatement removeuser= connection.prepareStatement(deleteStudentQuery)){
                        removeuser.setString(1, id);
                        removeuser.executeUpdate();
                        System.out.println("Student successfully deleted!");
                        return true;
                    }catch(SQLException e){
                        System.out.println("Error deleting student");
                        return false;
                    }
                }
                case("2"):
                {
                    String deleteFacultyQuery = "DELETE FROM faculty WHERE id = ?;";
                    try(PreparedStatement removeuser= connection.prepareStatement(deleteFacultyQuery)){
                        removeuser.setString(1, id);
                        removeuser.executeUpdate();
                        System.out.println("Instructor successfully deleted!");
                        return true;
                    }catch(SQLException e){
                        System.out.println("Error deleting faculty member");
                        return false;
                    }
                }
                default: {
                    System.out.println("Please follow the instructions");
                }

            }
        }
    }

    public static boolean view(Integer choice) throws SQLException {
        //Scanner input = new Scanner(System.in);
//        System.out.println("Enter 1 for student and 2 for instructor:");
//        choice = input.nextInt();
        String query;
        if (choice == 1) {
            query = "SELECT id, name, batch_id, email, phone_number FROM student";
        } else if (choice == 2) {
            query = "SELECT id, name, email, dep_id, phone_number FROM faculty";
        } else {
            System.out.println("Invalid choice.");
            return false;
        }
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            ResultSetMetaData metaData = result.getMetaData();
            int numColumns = metaData.getColumnCount();
            StringBuilder responseBuilder = new StringBuilder();
            while (result.next()) {
                for (int i = 1; i <= numColumns; i++) {
                    String columnValue = result.getString(i);
                    String columnName = metaData.getColumnName(i);
                    responseBuilder.append(columnName).append(" ---> ").append(columnValue).append("   ");
                }
                responseBuilder.append("\n");
            }
            System.out.println(responseBuilder.toString());
            statement.close();
            result.close();
//            System.out.println("Press any key to continue.");
//            input.nextLine();
//            input.nextLine();
            return true;
    }


}
