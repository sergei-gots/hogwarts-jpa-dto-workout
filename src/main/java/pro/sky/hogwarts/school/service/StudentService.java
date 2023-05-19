package pro.sky.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Student> getStudent(Long id) {
        if(!students.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students.get(id));
    }

    public void addStudent(Student student) {
        student.setNextId();
        students.put(student.getId(), student);
    }

    public ResponseEntity<?> editStudent(Student student) {
        if(!students.containsKey(student.getId())) {
            return ResponseEntity.notFound().build();
        }
        students.put(student.getId(), student);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Student> removeStudent(Long id) {
        if(!students.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students.remove(id));
    }

    public Collection<Student> getStudentsWithAgeEqualto(int age) {
        return students.values().stream().filter(student -> student.getAge() == age).collect(Collectors.toUnmodifiableList());
    }

    public Collection<Student> getAllStudents() {
        return Collections.unmodifiableCollection(students.values());
    }
}
