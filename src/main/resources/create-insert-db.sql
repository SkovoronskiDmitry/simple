DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
	employee_id SERIAL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(30) NOT NULL,
	department_id INT,
	job_title VARCHAR(30),
	gender VARCHAR (6),
	date_of_birth DATE,
	PRIMARY KEY (employee_id));

INSERT INTO employee (first_name, last_name ,department_id, job_title, gender,date_of_birth)
VALUES ('Ivan', 'Ivanov',7,'boss','male','1970-01-10');
INSERT INTO employee (first_name, last_name ,department_id, job_title, gender,date_of_birth)
VALUES ('Petr', 'Petrov',6,'employe','male','1990-05-10');
INSERT INTO employee (first_name, last_name ,department_id, job_title, gender,date_of_birth)
VALUES ('Vers', 'Ivanova',7,'secretary','female','2000-06-10');

