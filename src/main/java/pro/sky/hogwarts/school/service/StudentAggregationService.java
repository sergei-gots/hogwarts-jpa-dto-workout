package pro.sky.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentAggregationService {
    private final StudentRepository studentRepository;
    private final Logger logger;

    public StudentAggregationService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        logger = LoggerFactory.getLogger(AvatarService.class);
        logger.info("AvatarService instance has been successfully created.");
    }

    public int getCount() {
        logger.info("Method getCount() has been invoked.");
        return studentRepository.getStudentsCount();
    }
    public double getAvgAge() {
        logger.info("Method getAvgAge() has been invoked.");
        return studentRepository.getStudentsAvgAge();
    }

    public Collection<Student> getLastStudents(int count) {
        logger.info("Method getLastStudents(int count={}) has been invoked.", count);
        return studentRepository.findAll(
                PageRequest.of(0, count,
                        Sort.Direction.DESC,
                        "id"))
                .getContent();
    }
}
