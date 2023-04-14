package org.example;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class adminTest {

    acadsection x=new acadsection();
    @Test
    void deleteUserdummies() throws SQLException {
        List<String> stu_data=new ArrayList<String>();
        List<String> ins_data=new ArrayList<String>();
        stu_data.add("jan");
        stu_data.add("2020csb");
        stu_data.add("7307710111");
        ins_data.add("");
        ins_data.add("CS");
        ins_data.add("99999999");
        String stu_id= x.addUser("1",stu_data);
        String ins_id =x.addUser("2",ins_data);

        assertTrue(x.deleteUser("1",stu_id));
        assertTrue(x.deleteUser("2",ins_id));
        assertTrue(x.deleteUser("0",ins_id));
    }
    @Test
    void deletebatchdummies() throws SQLException {

        x.addBatch("2010csb","2010","CS");
        assertTrue(x.deleteBatch("2010csb"));
    }
    @Test
    void deletecoursedummies(){
        List<String> pre=new ArrayList<String>();
        pre.add("CS301");
        x.addCourse("DM100","dummboy","CS",3,3,3,3,pre);
        assertTrue(x.deleteCourse("DM100"));
        x.addCourse("DM100","dummboy","CS",3,3,3,3,pre);
    }



    @Test
    void deletecoursecurriculumdummies(){
        x.addCurriculum("CS200","open elective","2020eeb");
        assertTrue(x.deleteFromCurriculum("CS200","2020eeb"));
        x.addCurriculum("CS301","PC","2020csb");
        x.addCurriculum("DM100","PE","2020csb");
    }
    @BeforeAll
    @Test
    void login() throws IOException {
        assertFalse(x.login("admin","new"));
        assertTrue(x.login("admin","iitropar"));
    }
    @AfterAll
    @Test
    void logout() throws IOException {
        x.logout();
        assertFalse(x.user);
    }

    @Test
    void addbatch() throws SQLException {
        boolean f=x.addBatch("2010csb","2010","CS");
        assertTrue(f);
        x.deleteBatch("2010csb");
        x.addBatch("2020csb","2020","CS");
    }

    @Test
    void addcourse() {
        List<String> prereq=new ArrayList<String>();
        boolean f=x.addCourse("DM100","dummy_course","CS",3,3,3,3,prereq);
        assertTrue(x.addCourse("CS301","DBMS","CS",4,3,3,4,prereq));
        prereq.add("DM100");
        boolean f2=x.addCourse("DM101","dummy_course","CS",3,3,3,3,prereq);
        assertTrue(f);
        assertTrue(f2);

//        x.deleteCourse("DM100");
//        x.deleteCourse("DM101");

    }

    @Test
    void addcurriculum() throws SQLException {
        List<String> prereq=new ArrayList<String>();
        assertTrue(x.addBatch("2020eeb","2020","EE"));
        assertTrue(x.addCourse("CS200","Computer Dhokla","CS",3,3,3,3,prereq));
        boolean f= x.addCurriculum("CS200","core","2020eeb");
        assertTrue(f);
        x.deleteFromCurriculum("CS200","2020EEB");
        x.deleteBatch("2020eeb");
    }

    @Test
    void showGrades() throws SQLException {
//          String grades=x.showGrades();
        boolean f=x.showGrades();
        assertTrue(f);
    }
    //
    @Test
    void adduser() throws SQLException {
        List<String> data=new ArrayList<String>();
        data.add("dummy");
        data.add("2020csb");
        data.add("9327223367");
        String f=x.addUser("1",data);
        List<String> data2=new ArrayList<String>();
        data2.add("dummy");
        data2.add("CS");
        data2.add("9327223367");
        String f2=x.addUser("2",data2);
        assertNotEquals(f,"failed");
        assertNotEquals(f2,"failed");
        x.deleteUser("1",f);
        //x.deleteUser("2",f2);
        List<String> data3=new ArrayList<String>();
        data.add("janmeet");
        data.add("2020csb");
        data.add("12345678");
        String f4=x.addUser("1",data);
        String f5=x.addUser("0",data);

    }

    @Test @BeforeAll
    void startsem() throws SQLException {
        String resp=x.viewSemester();
            Boolean f=x.startSemester("2026","monsoon");
//         System.out.println(resp);
        if(resp.equals("No semester is running.")){
            assertNotEquals(f,"a sem is already running");
            boolean b=x.endSemester();
            assertEquals(b,true);
        }
        else{
            assertEquals(f,true);
        }
    }

    @AfterAll
    void endsem() throws SQLException {
        String resp=x.viewSemester();
        boolean f=x.endSemester();
//         System.out.println(resp);
        if(resp.equals("No semester is running.")){
            assertEquals(f,false);
        }
        else{
            assertEquals(f,true);

        }
        assertTrue(x.startSemester("2020","monsoon"));
    }

    @Test @AfterAll
    void updatecoursecatalog() throws SQLException {
        if(acadsection.viewSemester().equals("No semester is running.")){
            x.startSemester("2020","monsoon");
            assertTrue(x.updateCourseCatalog("CS301"));
            assertTrue(x.deleteFromCourseCatalog("CS301"));
        }
        else{
            List<String> prereq=new ArrayList<String>();
            prereq.add("BM101");
            assertTrue(x.addCourse("BM102","BIO ADVANCED","CS",3,3,3,3,prereq));
            assertTrue(x.updateCourseCatalog("BM102"));
            assertTrue(x.deleteFromCourseCatalog("BM102"));
            assertTrue(x.deleteCourse("BM102"));
        }
//        x.updateCourseCatalog("CS301");
//        x.updateCourseCatalog("DM100");
//        x.updateCourseCatalog("DM101");
    }

    @Test
    void submitransscript() throws Exception {
        boolean f=x.submitTranscript("2020csb0");
        assertEquals(f,true);
        //assertEquals(x.deleteTranscript("2020csb0"),true);
    }

    @Test
    void viewtranscript() throws Exception {
        boolean f=x.submitTranscript("2020csb0");
        assertEquals(f,true);
        f=x.viewTranscript("2020csb0");
        assertEquals(f,true);

        //assertEquals(x.deleteTranscript("2020csb0"),true);

    }

    @Test
    void viewcourses() {
        assertEquals(x.viewCourses(),true);
    }

    @Test
    void viewusers() throws SQLException {
        assertEquals(x.viewUsers(1),true);
        assertEquals(x.viewUsers(2),true);
        assertEquals(x.viewUsers(0),false);
    }
}