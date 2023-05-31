package pro.sky.hogwarts.school.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.hogwarts.school.entity.Student;

import java.util.Collection;

@Repository
public interface StudentRepository  extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);
    Collection<Student> findByAgeBetween(int ageMin, int ageMax);

    Collection<Student> findByFacultyId(long id);

    @Query(value = "SELECT COUNT(*) FROM students", nativeQuery = true)
    int getStudentsCount();
}
