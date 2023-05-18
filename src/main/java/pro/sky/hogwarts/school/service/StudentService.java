package pro.sky.hogwarts.school.service;

import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();

    public Student getStudent(Long id) {
        return students.get(id);
    }

    public void addStudent(Student student) {
        student.setNextId();
        students.put(student.getId(), student);
    }

    public void editStudent(Student student) {
        students.put(student.getId(), student);
    }

    public Student removeStudent(Long id) {
        return students.remove(id);
    }

    public Collection<Student> getStudentsWithAgeEqualto(int age) {
        return students.values().stream().filter(student -> student.getAge() == age).collect(Collectors.toUnmodifiableList());
    }
}
