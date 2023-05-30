
ALTER TABLE students ADD CONSTRAINT min_age CHECK ( age >= 16 );
ALTER TABLE students ALTER COLUMN name SET NOT NULL ;
ALTER TABLE students ADD CONSTRAINT unique_name UNIQUE(name);
/* But the next one will be redundant: */
ALTER TABLE students ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculties ADD UNIQUE (name, color);