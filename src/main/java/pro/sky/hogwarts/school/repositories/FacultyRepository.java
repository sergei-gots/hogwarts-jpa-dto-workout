package pro.sky.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.*;

import pro.sky.hogwarts.school.model.Faculty;


public interface FacultyRepository extends JpaRepository<Faculty, Long>{
}
