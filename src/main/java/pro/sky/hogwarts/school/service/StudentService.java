package pro.sky.hogwarts.school.service;

import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long lastStudentId;
    public Student getStudent(Long id) {
        return students.get(id);
    }

    public void addStudent(Student student) {
        student.setId(lastStudentId++);
        students.put(student.getId(), student);
    }

    public void editStudent(Student student) {
        if(!students.containsKey(student.getId())) {
            student.setId(lastStudentId++);
        }
        students.put(student.getId(), student);
    }

    public Student removeStudent(Long id) {
        return students.remove(id);
    }

    public Collection<Student> getStudentsWithAgeEqualto(int age) {
        return students.values().stream().filter(student -> student.getAge() == age).collect(Collectors.toUnmodifiableList());
    }

    public Collection<Student> getAllStudents() {
        return Collections.unmodifiableCollection(students.values());
    }
}
