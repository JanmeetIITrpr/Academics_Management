Welcome to IIT Ropar's Academic Portal designed by Janmeet Singh Makkar (2020CSB1175)
CS305 Mini Project 

You will find all the relevant classes in /src/main/java/org/example

1st in Heirarchy is App.java file upon executing it you will enter the portal:
Here roles will be presented to you a User, upon pressing:
1 you will be redirected to studentmenu class
2 you will be redirected to facultymenu class
3 you will be redirected to acadsectionmenu class

All the menu classes (3) come 2nd in heirarchy level, they will be used to present the user with their role functions.
Every class begins with a login based entry , here user is aunthenticated to be granted further priviliges.
Refer to UMLs in /UML folder for further understanding I have used 5 diagrams there, with sequence , flow and classes for Java code and Schema/ER for DB.

Let us breakdown rolewise access:
1: Student (default mail:2020csb0@iitrpr.ac.in ; password:"iitropar" or "new")
Upon login handleStudentMenu will present student with a variety of features as can be observed in menu.
Main features are: logout,view profile, update profile,check offered course,add/delete the course,viewing grades/cgpa and graduation checking.

2: Faculty(default mail:CS0@iitrpr.ac.in; password:"iitropar")
Upon login handleFaculty menu will give faculty options to logout,view/update profile(change password),see course catalogue,
add courses for students to view,view student grades,give them grades,approve/dissaprove requests.

3: Admin(default userid: admin, default password:"iitropar")
Admin is kind of a superuser here , they have been given access to start/end a batch, start/end a semester, add faculty or students,add courses, update course catalog, generate and view student transcripts.

Constraints:
1: First admin will add a course in course table, then they will add it in course_catalog table using UpdateCourseCatalog, then only faculty will be able to add the course from catalog and offer it to students.
2: Students can only request faculty for the courses offered by them, then faculty will approve/disapprove these course in registration_status.
3:These events regarding all courses can only be done if semester has started, batch is available (admin controlled).
4:Once semester ends, faculty can grade students (only registered_course). students can view cgpa also.
5:Trancript can also be generated would contain semester , year, instructor and grade of each completed course for a particular student.
6.JaCoCo report shows 91% Line coverage(/jacoco/index.html) , other exceptions left out only contain try catch outliers.
Total Lines of code: 3890 , some lines have been commented out fo rbetter testing to add redundant grades/users but while running app can be made functional for better checks and security.

Compile App.java using javac app.java and run the class then , you can use gradle build App and gradle run for better build
Thanks!