package org.example;

import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class facultyTest {
    faculty f= new faculty();
    acadsection x= new acadsection();

    public String y;
    @BeforeAll
    void testfaculty() throws SQLException {
        List<String> data=new ArrayList<String>();
        data.add("dummy1");
        data.add("CS");
        data.add("9327223367");
        y=x.addUser("2",data);
        assertNotEquals(y,"failed");
    }


    @BeforeAll
    void login() throws SQLException, IOException {
        String mail= "CS0@iitrpr.ac.in";
        assertTrue(faculty.login(mail,"newpass") || f.login(mail,"iitropar"));
        assertFalse(f.login(mail,"wrongpass"));
    }

    @AfterAll
    void logout() throws SQLException, IOException {
        f.logout();
        assertFalse(f.user);
    }
//    @AfterAll
//    void removedummy() {
//        List<String> data=new ArrayList<String>();
//        data.add("dummy");
//        data.add("CS");
//        data.add("9327223367");
//        x.deleteUser("2",y);
//    }

    @Test
    void viewProfile() throws SQLException {
        assertTrue(f.viewProfile("CS0"));
        //assertFalse();
    }

    @Test
    void updateProfile() throws SQLException {
        String name,phone_number,password;
        name="notdummy1";
        phone_number="12534567";
        password="iitropar";
        assertTrue(f.updateProfile(name,phone_number,password));
        assertEquals(f.key,password);
    }

    @Test
    void addCourse() throws SQLException {
        if(acadsection.viewSemester().equals("No semester is running.")){
            x.startSemester("2020","monsoon");
            assertTrue(x.updateCourseCatalog("CS301"));
            //assertTrue(x.deleteFromCourseCatalog("CS301"));
        }
        else if(!f.addCourse("CS301",0.0)) {
            assertTrue(x.updateCourseCatalog("CS301"));
            assertTrue(x.updateCourseCatalog("DM100"));
            assertTrue(x.updateCourseCatalog("DM101"));

            //assertTrue(x.deleteFromCourseCatalog("CS301"));
        }
        assertTrue(f.addCourse("DM100",0.0));
        assertTrue(f.addCourse("DM101",0.0));
        assertTrue(f.addCourse("CS301",0.0));
        assertFalse(f.addCourse("LOL101",8.0));

    }

    @Test
    void offeredCourses() throws SQLException {
        assertTrue(f.offeredCourses());
    }

    @Test
    void displayMyCourses() throws SQLException {
        assertTrue(f.displayMyCourses());
    }

    @AfterAll
    void removeCourse() throws SQLException {
        assertTrue(f.removeCourse("DM101"));
        assertTrue(f.removeCourse("0"));
        assertTrue(f.removeCourse("99"));
    }

    @Test
    void showGrades() throws SQLException {
        assertTrue(f.showGrades());
    }

    @Test
    void enrollmentRequests() throws SQLException {
        assertTrue(f.enrollmentRequests("CS0"));
    }

//    @Test
//    void pendingRequests(){
//        assertEquals(f.requests,f.getPendingRequestsForInstructor("CS0"));
//    }

    @Test
    void printRequests(){
        assertTrue(f.printRequests(f.requests));
    }

    @Test
    void handleApprovalOrRejection() throws SQLException {
        assertTrue(f.handleApprovalOrRejection(f.requests,"2020csb0","CS301","1"));
        assertTrue(f.handleApprovalOrRejection(f.requests,"2020csb0","DM100","1"));
        assertTrue(f.handleApprovalOrRejection(f.requests,"2020csb0","DM101","2"));
    }

    @Test
    void submitGrades() throws SQLException, IOException {
        assertTrue(f.submitGrades());
    }
}