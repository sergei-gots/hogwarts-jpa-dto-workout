package pro.sky.hogwarts.school.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentAggregationService {
    private final StudentRepository studentRepository;

    public StudentAggregationService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public int getCount() {
        return studentRepository.getStudentsCount();
    }
    public int getAvgAge() {
        return studentRepository.getStudentsAvgAge();
    }

    public Collection<Student> getLastStudents(int count) {
        //return studentRepository.getLast(count);
        return studentRepository.findAll(
                PageRequest.of(0, count,
                        Sort.Direction.DESC,
                        "id"))
                .getContent();
    }
}
