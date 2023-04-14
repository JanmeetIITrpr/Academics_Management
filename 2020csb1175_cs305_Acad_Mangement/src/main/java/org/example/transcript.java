package org.example;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class transcript {
    static final Connection connection = SQL.getConnection();
    public static boolean submit(String studentId) throws FileNotFoundException, SQLException {
        String runquery="select c.id,c.name,c.c,g.instructor_id,g.grade,g.semester,g.academic_year from GRADES g JOIN "+ "course c ON g.course_id=c.id WHERE g.student_id=?";
        StringBuilder transcript=new StringBuilder();
        PreparedStatement ts=connection.prepareStatement(runquery);
        ts.setString(1,studentId);
        ResultSet rs =ts.executeQuery();
        ResultSetMetaData rsmd=rs.getMetaData();
        int ci= rsmd.getColumnCount();
        while(rs.next())
        {
            for(int i=1;i<ci+1;i++)
            {
                switch(i)
                {
                    case 1:
                        transcript.append("Course ID: ");
                        break;
                    case 2:
                        transcript.append("Course Name: ");
                        break;
                    case 3:
                        transcript.append("Credits: ");
                        break;
                    case 4:
                        transcript.append("Faculty ID: ");
                        break;
                    case 5:
                        transcript.append("Grade: ");
                        break;
                    case 6:
                        transcript.append("AY: ");
                        break;
                    case 7:
                        transcript.append("Semester: ");
                        break;
                }
                String cv=rs.getString(i);
                transcript.append(cv).append("\n");
            }
            transcript.append("\n");
        }

        try(BufferedWriter output= new BufferedWriter(new FileWriter("src/main/resources/transcript-"+studentId+".txt"))){
            output.write(transcript.toString());
        } catch (IOException e) {
            System.out.println("Could not generate Transcript");
            throw new RuntimeException(e);
        }
        return true;
    }


        public static boolean view (String studentId){
            return true;
        }
//            try {
//                PreparedStatement ps = connection.prepareStatement("SELECT transcript FROM transcript WHERE student_id = ?");
//                ps.setString(1, studentId);
//
//                ResultSet rs = ps.executeQuery();
//
//                if (rs.next()) {
//                    byte[] transcriptBytes = rs.getBytes("transcript");
//                    String transcript = new String(transcriptBytes);
//
//                    System.out.println(transcript);
//                    rs.close();
//                    ps.close();
//
////                    System.out.println("Press any key to continue.");
////                    input.nextLine();
//                    return true;
//                } else {
//                    System.out.println("No transcript found for the student.");
//                    rs.close();
//                    ps.close();
//
////                    System.out.println("Press any key to continue.");
////                    input.nextLine();
//                    return true;
//                }
//            } catch (SQLException e) {
//                System.out.println("Error viewing transcript: " + e.getMessage());
//                return false;
//            }
//        }
    }

