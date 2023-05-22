package pro.sky.hogwarts.school.service;

import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<Student> getById(Long id) {

        return studentRepository.findById(id);
    }

    public Student addStudent(Student student) {
        student.setId(null);
        return studentRepository.save(student);
    }

    public Optional<Student> editStudent(Student newStudentData) {
        return studentRepository.findById(newStudentData.getId())
                .map(existingStudent -> {
                        existingStudent.setName(newStudentData.getName());
                        existingStudent.setAge(newStudentData.getAge());
                        return studentRepository.save(existingStudent);
                });
    }

    public Optional<Student> deleteById(Long id) {
        return studentRepository.findById(id)
                .map(student-> {
                    studentRepository.deleteById(id);
                    return student;
                });
    }

    public Collection<Student> findByAge(int age) {
        return Collections.unmodifiableCollection(studentRepository.findByAge(age));
    }

    public Collection<Student> findAll() {
        return Collections.unmodifiableCollection(studentRepository.findAll());
    }
}
