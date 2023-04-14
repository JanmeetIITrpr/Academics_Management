package org.example;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.*;
import java.sql.*;

import java.io.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppTest {

//    @Test
//    void main() {
//    }
    //Connection connection=SQL.getConnection();
    //@Test
//    void getLoggedInUserRole() {
//        ByteArrayInputStream in1 = new ByteArrayInputStream("0\n".getBytes());
//        System.setIn(in1);
//        assertEquals(App.getLoggedInUserRole(),"1");
//
//        ByteArrayInputStream in2 = new ByteArrayInputStream("1\n0\n".getBytes());
//        System.setIn(in2);
//        assertEquals(App.getLoggedInUserRole(),"1");
//
//    }
    @Test
    void MainTest1() throws Exception {
        String[] data=new String[]{};
        ByteArrayInputStream in1 = new ByteArrayInputStream("0\n".getBytes());
        System.setIn(in1);
        App.main(null);
    }

//    @Test
//    void MainTest2() throws Exception {
//        studentmenu p=new studentmenu();
//        String[] data=new String[]{};
//        ByteArrayInputStream in1 = new ByteArrayInputStream("1\n2020csb1@iitrpr.ac.in\niitropar\n0\n0".getBytes());
//        System.setIn(in1);
//        App.main(null);
//        //assertTrue(studentmenu.handleStudentMenu());
//    }
//    @Test
//    void getLoggedInUserRole2() {
////        String query="UPDATE student set token='' where id='2021eeb';";
////        PreparedStatement ps=connection.prepareStatement(query);
////        ps.executeUpdate();
////        System.out.println("abc");
//
//        ByteArrayInputStream in3 = new ByteArrayInputStream("2\n0\n".getBytes());
//        System.setIn(in3);
//        assertEquals(App.getLoggedInUserRole(),"2");
//
////        ByteArrayInputStream in4 = new ByteArrayInputStream("1\n0\n".getBytes());
////        System.setIn(in4);
////        assertEquals(App.getLoggedInUserRole(),"1");
//    }
}
