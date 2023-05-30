/* Описание структуры: у каждого человека есть машина.
  Причем несколько человек могут пользоваться одной машиной.
  У каждого человека есть имя, возраст и признак того,
  что у него есть права (или их нет).
  У каждой машины есть марка, модель и стоимость.
  Также не забудьте добавить таблицам первичные ключи и связать их.
 */

 CREATE TABLE people (
     id SERIAL PRIMARY KEY,
     name VARCHAR(63) NOT NULL,
     age INT2 CHECK ( age > 0 ),
     car_id INT REFERENCES cars (id),
     driver_license BOOLEAN
 );

CREATE TABLE cars (
    id SERIAL PRIMARY KEY ,
    manufacturer_brand VARCHAR(15),
    model VARCHAR(31),
    price NUMERIC CHECK ( price > 0 )
);

INSERT INTO cars DEFAULT VALUES;

INSERT INTO people (name, age, car_id)
    VALUES ('Sergei', 46, 1);

SELECT * FROM people INNER JOIN cars ON people.car_id = cars.id;

ALTER TABLE people ALTER COLUMN driver_license SET DEFAULT (false);