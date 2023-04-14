package org.example;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class acadmenuTest {
        acadsection admin=new acadsection();
        @Test
        void testHandleacadmenu1() throws SQLException, IOException {
            ByteArrayInputStream in2 = new ByteArrayInputStream("admin\niitropar\n0\n".getBytes());
            System.setIn(in2);
            assertTrue(acadmenu.handleAdminMenu());
        }
        
        @Test
        void testHandleacadmenu2() throws SQLException, IOException {
            ByteArrayInputStream in2 = new ByteArrayInputStream("admin\niitropar\n1\n2021eeb\n2021\nEE\n0\n0".getBytes());
            System.setIn(in2);
            assertTrue(acadmenu.handleAdminMenu());
            //admin.deleteBatch("2021eeb");
        }

        @Test
        void testHandleacadmenu3() throws SQLException, IOException {
            ByteArrayInputStream in = new ByteArrayInputStream("admin\niitropar\n2\nSE420\nSex Ed\nCS\n3\n69\n69\n3\nBM101\n0\n0\n0\n".getBytes());
            System.setIn(in);
            assertTrue(acadmenu.handleAdminMenu());
            //admin.deleteCourse("SE420");
        }

        @Test
        void testHandleacadmenu4() throws SQLException, IOException {
            ByteArrayInputStream in = new ByteArrayInputStream("admin\niitropar\n3\nDM100\n2021eeb\nOpen Elective\n0\n0\n".getBytes());
            System.setIn(in);
            assertTrue(acadmenu.handleAdminMenu());
        }

        @Test
        void testHandleacadmenu5() throws SQLException, IOException {
            ByteArrayInputStream in = new ByteArrayInputStream("admin\niitropar\n4\n2021\nwinter\n0\n".getBytes());
            System.setIn(in);
            assertTrue(acadmenu.handleAdminMenu());
        }

        @Test
        void testHandleacadmenu6() throws SQLException, IOException {
            ByteArrayInputStream in = new ByteArrayInputStream("admin\niitropar\n5\n0\n0\n".getBytes());
            System.setIn(in);
            assertTrue(acadmenu.handleAdminMenu());
        }

        @Test
        void testHandleacadmenu7() throws SQLException, IOException {
            ByteArrayInputStream in = new ByteArrayInputStream("admin\niitropar\n6\nSE420\n0\n".getBytes());
            System.setIn(in);
            assertTrue(acadmenu.handleAdminMenu());
        }

        @Test
        void testHandleacadmenu8() throws SQLException, IOException {
            ByteArrayInputStream in = new ByteArrayInputStream("admin\niitropar\n7\n0\n".getBytes());
            System.setIn(in);
            assertTrue(acadmenu.handleAdminMenu());
        }
        @Test
        void testHandleacadmenu9() throws SQLException, IOException {
            ByteArrayInputStream in1 = new ByteArrayInputStream("admin\niitropar\n8\n1\nsakht\n2021eeb\n1234\n0\n".getBytes());
            System.setIn(in1);
            assertTrue(acadmenu.handleAdminMenu());
            ByteArrayInputStream in2 = new ByteArrayInputStream("admin\niitropar\n8\n2\nSoftie\nEE\n9634\n0\n".getBytes());
            System.setIn(in2);
            assertTrue(acadmenu.handleAdminMenu());
        }

        @Test
        void testHandleacadmenu10() throws SQLException, IOException {
            ByteArrayInputStream in = new ByteArrayInputStream("admin\niitropar\n9\n2020eeb0\n0\n".getBytes());
            System.setIn(in);
            assertTrue(acadmenu.handleAdminMenu());
        }

        @Test
        void testHandleacadmenu11() throws SQLException, IOException {
            ByteArrayInputStream in = new ByteArrayInputStream("admin\niitropar\n10\n2020eeb0\n0\n".getBytes());
            System.setIn(in);
            assertTrue(acadmenu.handleAdminMenu());
        }
//        @Test
//        void testHandleacadmenu12() throws SQLException, IOException {
//            ByteArrayInputStream in1 = new ByteArrayInputStream("admin\niitropar\n1\n0\n0\n0\n".getBytes());
//            System.setIn(in1);
//            assertTrue(acadmenu.handleAdminMenu());
//            ByteArrayInputStream in2 = new ByteArrayInputStream("admin\niitropar\n2\n0\n0\n0\n".getBytes());
//            System.setIn(in2);
//            assertTrue(acadmenu.handleAdminMenu());
//        }

        @Test
        void testHandleacadmenu13() throws SQLException, IOException {
            ByteArrayInputStream in = new ByteArrayInputStream("admin\niitropar\n13\n0\n".getBytes());
            System.setIn(in);
            assertTrue(acadmenu.handleAdminMenu());
        }

    }

