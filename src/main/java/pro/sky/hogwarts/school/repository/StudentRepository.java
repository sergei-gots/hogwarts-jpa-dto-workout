package pro.sky.hogwarts.school.repository;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.hogwarts.school.entity.Student;

import java.awt.print.Pageable;
import java.util.Collection;

@Repository
public interface StudentRepository  extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);
    Collection<Student> findByAgeBetween(int ageMin, int ageMax);

    Collection<Student> findByFacultyId(long id);

    @Query(value = "SELECT COUNT(s) FROM Student s")
    int getStudentsCount();
    @Query(value = "SELECT AVG(age) FROM Student")
    int getStudentsAvgAge();

    /*@Query (
            value = "SELECT * FROM students ORDER BY id DESC LIMIT :count",
            nativeQuery = true
    )
    Collection<Student> getLast(int count);*/

    /*@Query ("FROM Student")
    Collection<Student> findAll(PageRequest pageRequest);*/
}
