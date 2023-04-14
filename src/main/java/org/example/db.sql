

CREATE TABLE department(
id VARCHAR(10),
name VARCHAR(50),
PRIMARY KEY (id)
);

INSERT INTO department(id,name) VALUES ('CS','Computer Science');
INSERT INTO department(id,name) VALUES ('EE','Electrical Engineering');
INSERT INTO department(id,name) VALUES ('MNC','Maths and Computing');
INSERT INTO department(id,name) VALUES ('ME','Mechanical Engineering');
INSERT INTO department(id,name) VALUES ('HSS','Humanities & Social Sciences');


CREATE TABLE batch(
id VARCHAR(10),
year VARCHAR(10),
dep_id VARCHAR(10),
PRIMARY KEY(id),
FOREIGN KEY (dep_id) references department (id) ON DELETE CASCADE
);

CREATE TABLE course(
id VARCHAR(10),
name VARCHAR(100),
dep_id VARCHAR(10),
l integer,
t integer,
p integer,
c integer,
PRIMARY KEY(id),
FOREIGN KEY (dep_id) references department (id) ON DELETE CASCADE
);

CREATE TABLE ug_curriculum(
course_id VARCHAR(10),
batch_id VARCHAR(10),
course_type VARCHAR(100),
FOREIGN KEY (course_id) references course (id) ON DELETE CASCADE
);

CREATE TABLE course_catalog(
course_id VARCHAR(10),
PRIMARY KEY(course_id),
FOREIGN KEY (course_id) references course (id) ON DELETE CASCADE
);

CREATE TABLE course_offering(
course_id VARCHAR(10),
cgpa_limit INTEGER,
instructor_id VARCHAR(10),
PRIMARY KEY(course_id),
FOREIGN KEY (course_id) references course_catalog (course_id),
FOREIGN KEY (instructor_id) references faculty (id) ON DELETE CASCADE

);

CREATE TABLE prerequisites(
course_id VARCHAR(10),
prerequisite_id VARCHAR(10),
FOREIGN KEY (course_id) references course (id),
FOREIGN KEY (prerequisite_id) references course (id) ON DELETE CASCADE
);


CREATE TABLE student(
id VARCHAR(10),
name VARCHAR(100),
batch_id VARCHAR(10),
email VARCHAR(100),
password VARCHAR(100),
phone_number VARCHAR(20),
credits INTEGER,
token TEXT,
PRIMARY KEY (id),
FOREIGN KEY (batch_id) references batch (id) ON DELETE CASCADE
);

CREATE TABLE faculty(
id VARCHAR(10),
name VARCHAR(10),
email VARCHAR(100),
dep_id VARCHAR(10),
password VARCHAR(100),
phone_number VARCHAR(20),
token TEXT,
PRIMARY KEY (id),
FOREIGN KEY (dep_id) references department (id) ON DELETE CASCADE
);


CREATE TABLE registration_status(
course_id VARCHAR(10),
student_id VARCHAR(10),
instructor_id VARCHAR(10),
status VARCHAR(100),
FOREIGN KEY (course_id) references course_offering (course_id) ON DELETE CASCADE,
FOREIGN KEY (student_id) references student (id) ON DELETE CASCADE,
FOREIGN KEY (instructor_id) references faculty (id) ON DELETE CASCADE
);

CREATE TABLE GRADES(
student_id VARCHAR(10),
instructor_id VARCHAR(10),
course_id VARCHAR(10),
grade VARCHAR(5),
semester VARCHAR(100),
academic_year VARCHAR(100),
FOREIGN KEY (student_id) references student (id) ON DELETE CASCADE,
FOREIGN KEY (instructor_id) references faculty (id) ON DELETE CASCADE,
FOREIGN KEY (course_id) references course (id) ON DELETE CASCADE
);

CREATE TABLE semester(
academic_year VARCHAR(10),
semester VARCHAR(10)
);

CREATE TABLE transcript(
student_id VARCHAR(10),
transcript bytea,
FOREIGN KEY (student_id) references student (id) ON DELETE CASCADE
);

CREATE TABLE time_slots(
    slot_number serial PRIMARY KEY,
    monday_start TIME,
    monday_end TIME,
    tuesday_start TIME,
    tuesday_end TIME,
    wednesday_start TIME,
    wednesday_end TIME,
    thursday_start TIME,
    thursday_end TIME,
    friday_start TIME,
    friday_end TIME
);



select course_offering.course_id,ug_curriculum.course_type,course_offering.instructor_id
from student,ug_curriculum,course_offering
where student.batch_id=ug_curriculum.batch_id and ug_curriculum.course_id=course_offering.course_id;


select ug_curriculum.course_id
from ug_curriculum
where course_type='core'
except
select grades.course_id
from grades
where grades.grade!='F' and grades.student_id=user_id;