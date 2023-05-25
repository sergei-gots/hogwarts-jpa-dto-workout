SELECT * FROM students;

SELECT * FROM faculties;

SELECT * FROM students where age between 18 and 19;

SELECT * FROM students where age >= 46 and age <=46;

SELECT name FROM students;

SELECT * FROM students WHERE name LIKE '%o%';

/* ALTER TABLE students DROP COLUMN faculties_id;*/

SELECT * FROM students;


UPDATE students SET id=1000 WHERE name LIKE 'Harry%';

SELECT * FROM students WHERE age < id;

SELECT * FROM students ORDER BY age;

SELECT * FROM faculties;

INSERT INTO faculties (name, color) VALUES('Ravenclaw', 'blue');
INSERT INTO faculties (name, color) VALUES
                                        ('Gryffindor', 'red'),
                                        ('Slytherin', 'green'),
                                        ('Hufflepuff', 'yellow');

SELECT * FROM students ORDER BY age;
INSERT INTO students (name, age, faculty_id) VALUES
                                        ('Harry Potter',        18, 2),
                                        ('Germione Grainger',   19, 2),
                                        ('Drako Malfoy',        18, 3);
