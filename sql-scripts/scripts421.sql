UPDATE students SET age = 16 WHERE age < 16;
ALTER TABLE students
    ADD CONSTRAINT min_age CHECK ( age >= 16 );

DELETE FROM students WHERE name IS NULL;
ALTER TABLE students ALTER COLUMN name SET NOT NULL;

DELETE FROM students WHERE id IN
                     (SELECT s1.id from students s1 INNER JOIN students s2
                             ON s1.name = s2.name
                             WHERE s1.id > s2.id);
ALTER TABLE students
    ADD CONSTRAINT unique_name UNIQUE (name);

ALTER TABLE students
    ALTER COLUMN age SET DEFAULT 20;

DELETE FROM faculties
WHERE id IN (SELECT f1.id
             FROM faculties f1
                      INNER JOIN faculties f2
                                 ON f1.name = f2.name AND f1.color = f2.color
             WHERE f1.id > f2.id);
ALTER TABLE faculties
    ADD CONSTRAINT name_color_unique UNIQUE (name, color);

INSERT INTO students (name) values ('Sergei-3');
SELECT * FROM students;