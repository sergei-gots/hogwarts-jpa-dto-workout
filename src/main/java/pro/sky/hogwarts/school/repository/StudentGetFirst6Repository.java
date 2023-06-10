package pro.sky.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.hogwarts.school.entity.Student;


public interface StudentGetFirst6Repository extends JpaRepository <Student, Long>{
}
