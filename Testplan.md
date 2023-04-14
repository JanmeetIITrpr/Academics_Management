Test Items:
All 11 classes but we only test top 2 level for heirarchy as low level classes are all called from them (refer uml)
We have created 7 JUnit Tests with 73 test methods (115 cases);
1: for App just to call it and execute.
2: for each menu to see whether each method they refer to further classes is being called by a boolean check
3: for each role as the role files have all privileges and functionalities 

Stakeholder and functionality :
1: First run tests for acadsection: it will add batch,users and start semester,add necessary courses in db for further use.
2: Then run faculty tests it will offer students courses and check all functions
3: Then run student(pupil) tests. To check all feauture availability.
----you can run faculty and acadsection again to update grades,generate transcript and viewCgpa--------------------
4: Then you can run all menus and App.
5: Tests can be run all together , JaCoCo reports a 91% coverage for 63 test cases that we have made and report can be reffered.
The remaining 9% is due to catch for SQL Exceptions and App() menu since it's a device controlled loop.

Testing environment:
It is preffered you run this on local computer to take care to database is properaly ready with demo values if out of order testing is done, but it will also work fine on other desktops.

