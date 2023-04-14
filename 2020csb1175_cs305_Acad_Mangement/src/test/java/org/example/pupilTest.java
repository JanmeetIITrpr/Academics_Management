package org.example;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class pupilTest {
    pupil z=new pupil();

    @Test @BeforeAll
    void login() throws SQLException, IOException {
        assertFalse(z.login("2020csb0@iitrpr.ac.in","wrongpass"));
        assertTrue(z.login("2020csb0@iitrpr.ac.in","iitropar") || z.login("2020csb0@iitrpr.ac.in","new"));
    }

    @Test @AfterAll
    void logout() throws SQLException, IOException {
        z.logout();
        assertFalse(z.userLoggedIn);
    }

    @Test
    void viewProfile() throws SQLException {
        assertTrue(z.viewProfile("2020csb0"));
    }

    @Test
    void updateProfile() throws SQLException {
        assertTrue(z.updateProfile("janmeet","999999","new"));
    }

    @Test
    void addCourse() throws SQLException {
        assertTrue(z.addCourse("CS301"));
        assertTrue(z.addCourse("DM100"));
        assertTrue(z.addCourse("DM101"));
        assertTrue(z.addCourse("0"));
    }


    @Test
    void offeredCourses() throws SQLException {
        assertTrue(z.offeredCourses());
    }

    @Test
    void mycourses() throws SQLException {
        assertTrue((z.mycourses()));
    }

    @Test @AfterAll
    void deleteCourse() throws SQLException {
        assertTrue(z.deleteCourse("CS301"));
    }

    @Test
    void showGrades() throws SQLException {
        assertTrue(z.showGrades());
    }

    @Test
    void getCgpa() throws SQLException {
        assertTrue(z.gimmeCg("csb0"));
    }

    @Test
    void checkGraduationEligibility() throws SQLException {
        assertTrue(z.checkGraduationEligibility());
    }

}