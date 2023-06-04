-- liquibase formatted sql

-- changeset sgots:1
CREATE INDEX name_index ON students(name);

-- changeset sgots:2
CREATE INDEX name_color_idx ON faculties(name, color)