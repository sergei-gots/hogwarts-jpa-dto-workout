
ALTER TABLE students ADD CONSTRAINT min_age CHECK ( age >= 16 );
ALTER TABLE students ALTER COLUMN name SET NOT NULL ;
ALTER TABLE students ADD CONSTRAINT unique_name UNIQUE(name);
/* But the next one will be redundant:  */
ALTER TABLE students ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculties
    ADD CONSTRAINT name_color_unique UNIQUE (name, color);

DELETE FROM students
    WHERE id IN (SELECT s1.id FROM students s1
        INNER JOIN students s2 ON s1.id = s2.id
            WHERE s2.id > s1.id);
