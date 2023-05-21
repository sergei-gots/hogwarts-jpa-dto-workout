package pro.sky.hogwarts.school.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.hogwarts.school.entity.Student;

import java.util.Collection;

public interface StudentRepository  extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);
}
