package org.example;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class StudentMenuTest {
    //menu menu=new menu();
    @Test
    void testHandleStudentMenu1() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("2020csb0@iitrpr.ac.in\nnew\n0\n0\n".getBytes());
        System.setIn(in);
        assertTrue(studentmenu.handleStudentMenu());
    }
    @Test
    void testHandleStudentMenu2() throws SQLException, IOException {
        ByteArrayInputStream in2 = new ByteArrayInputStream("2020csb0@iitrpr.ac.in\nnew\n1\n0\n0\n".getBytes());
        System.setIn(in2);
        assertTrue(studentmenu.handleStudentMenu());
    }

    @Test
    void testHandleStudentMenu3() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("2020csb0@iitrpr.ac.in\nnew\n2\njan singh\n73011\nnew\n0\n".getBytes());
        System.setIn(in);
        assertTrue(studentmenu.handleStudentMenu());
    }

    @Test
    void testHandleStudentMenu4() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("2020csb0@iitrpr.ac.in\nnew\n3\n0\n".getBytes());
        System.setIn(in);
        assertTrue(studentmenu.handleStudentMenu());
    }

    @Test
    void testHandleStudentMenu5() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("2020csb0@iitrpr.ac.in\nnew\n4\nCS301\n0\n".getBytes());
        System.setIn(in);
        assertTrue(studentmenu.handleStudentMenu());
    }

    @Test
    void testHandleStudentMenu6() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("2020csb0@iitrpr.ac.in\nnew\n5\n0\n0\n".getBytes());
        System.setIn(in);
        assertTrue(studentmenu.handleStudentMenu());
    }

    @Test
    void testHandleStudentMenu7() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("2020csb0@iitrpr.ac.in\nnew\n6\n0\n".getBytes());
        System.setIn(in);
        assertTrue(studentmenu.handleStudentMenu());
    }

    @Test
    void testHandleStudentMenu8() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("2020csb0@iitrpr.ac.in\nnew\n7\n0\n".getBytes());
        System.setIn(in);
        assertTrue(studentmenu.handleStudentMenu());
    }

    @Test
    void testHandleStudentMenu9() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("2020csb0@iitrpr.ac.in\nnew\n8\n0\n0\n".getBytes());
        System.setIn(in);
        assertTrue(studentmenu.handleStudentMenu());
    }

    @Test
    void testHandleStudentMenu10() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("2020csb0@iitrpr.ac.in\nnew\n9\n0\n".getBytes());
        System.setIn(in);
        assertTrue(studentmenu.handleStudentMenu());
    }
    
}
