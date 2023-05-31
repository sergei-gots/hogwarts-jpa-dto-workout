package pro.sky.hogwarts.school.service;

import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.repository.StudentRepository;

@Service
public class StudentAggregationService {
    private final StudentRepository studentRepository;

    public StudentAggregationService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public int getCount() {
        return studentRepository.getStudentsCount();
    }
}
