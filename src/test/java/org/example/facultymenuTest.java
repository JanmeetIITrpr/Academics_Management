package org.example;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class facultymenuTest {

    @Test
    void testHandleFacultyMenu1() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("CS0@iitrpr.ac.in\niitropar\n0\n0\n".getBytes());
        System.setIn(in);
        assertTrue(facultymenu.handleFacultyMenu());
    }
    @Test
    void testHandleFacultyMenu2() throws SQLException, IOException {
        ByteArrayInputStream in2 = new ByteArrayInputStream("CS0@iitrpr.ac.in\niitropar\n1\n0\n0\n".getBytes());
        System.setIn(in2);
        assertTrue(facultymenu.handleFacultyMenu());
    }

    @Test
    void testHandleFacultyMenu3() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("CS0@iitrpr.ac.in\niitropar\n2\nB.Sodhi\n99999\niitropar\n0\n".getBytes());
        System.setIn(in);
        assertTrue(facultymenu.handleFacultyMenu());
    }

    @Test
    void testHandleFacultyMenu4() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("CS0@iitrpr.ac.in\niitropar\n3\nCS301\n0\n".getBytes());
        System.setIn(in);
        assertTrue(facultymenu.handleFacultyMenu());
    }

    @Test
    void testHandleFacultyMenu5() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("CS0@iitrpr.ac.in\niitropar\n4\nCS301\n0.0\n0\n".getBytes());
        System.setIn(in);
        assertTrue(facultymenu.handleFacultyMenu());
    }

    @Test
    void testHandleFacultyMenu6() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("CS0@iitrpr.ac.in\niitropar\n5\nCS301\n0\n".getBytes());
        System.setIn(in);
        assertTrue(facultymenu.handleFacultyMenu());
    }

    @Test
    void testHandleFacultyMenu7() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("CS0@iitrpr.ac.in\niitropar\n6\n0\n".getBytes());
        System.setIn(in);
        assertTrue(facultymenu.handleFacultyMenu());
    }

    @Test
    void testHandleFacultyMenu8() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("CS0@iitrpr.ac.in\niitropar\n7\n0\n".getBytes());
        System.setIn(in);
        assertTrue(facultymenu.handleFacultyMenu());
    }

    @Test
    void testHandleFacultyMenu9() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("CS0@iitrpr.ac.in\niitropar\n8\n0\nCS301\n2020csb0\n1\n0\n0\n".getBytes());
        System.setIn(in);
        assertTrue(facultymenu.handleFacultyMenu());
    }

    @Test
    void testHandleFacultyMenu10() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("CS0@iitrpr.ac.in\niitropar\n9\n0\n".getBytes());
        System.setIn(in);
        assertTrue(facultymenu.handleFacultyMenu());
    }

    @Test
    void testHandleFacultyMenu11() throws SQLException, IOException {
        ByteArrayInputStream in = new ByteArrayInputStream("CS0@iitrpr.ac.in\niitropar\n10\n0\n".getBytes());
        System.setIn(in);
        assertTrue(facultymenu.handleFacultyMenu());
    }
}