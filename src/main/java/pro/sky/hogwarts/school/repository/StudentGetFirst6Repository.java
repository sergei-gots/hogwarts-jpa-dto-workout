package pro.sky.hogwarts.school.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.hogwarts.school.entity.Student;

import java.util.List;

public interface StudentGetFirst6Repository extends JpaRepository <Student, Long>{

    @Query(//nativeQuery=true,
           // value="SELECT * FROM students ORDER BY id")
        "SELECT new pro.sky.hogwarts.school.entity.Student(id, name, age, faculty) FROM Student")
    List<Student> page(Pageable pageable);
}
