package pro.sky.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.entity.Faculty;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.repository.FacultyRepository;
import pro.sky.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    private final Logger logger;

    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        logger = LoggerFactory.getLogger(StudentService.class);
    }

    public Optional<Student> findById(Long id) {
        logger.info("Method findById(Long id={}) has been invoked.", id);
        return studentRepository.findById(id);
    }

    public Student addStudent(Student student) {
        logger.info("Method addStudent(Student student={}) has been invoked.", student);
        student.setId(null);
        student.setFaculty(Optional.ofNullable(student.getFaculty())
                .filter(f -> f.getId() != null)
                .flatMap(f -> facultyRepository.findById(f.getId()))
                .orElse(null));
        return studentRepository.save(student);
    }

    public Optional<Student> editStudent(Student newStudentData) {
        logger.info("Method editStudent(Student newStudentData={}) has been invoked.", newStudentData);
        return studentRepository.findById(newStudentData.getId())
                .map(existingStudent -> {
                        existingStudent.setName(newStudentData.getName());
                        existingStudent.setAge(newStudentData.getAge());
                        existingStudent.setFaculty(Optional.ofNullable(newStudentData.getFaculty())
                                .filter(f -> f.getId() != null)
                                .flatMap(f -> facultyRepository.findById(f.getId()))
                                .orElse(null));
                        return studentRepository.save(existingStudent);
                });
    }

    public Optional<Student> deleteById(Long id) {
        logger.info("Method deleteById(Long id={}) has been invoked.", id);
        return studentRepository.findById(id)
                .map(student-> {
                    studentRepository.deleteById(id);
                    return student;
                });
    }

    public Collection<Student> findByAge(int age) {
        logger.info("Method findByAge(int age={}) has been invoked.", age);
        return Collections.unmodifiableCollection(studentRepository.findByAge(age));
    }

    public Collection<Student> findByAgeBetween(int ageMin, int ageMax) {
        logger.info("Method findByAgeBetween(int ageMin={}, int ageMax={}) has been invoked.",
                ageMin, ageMax);
        return Collections.unmodifiableCollection(studentRepository.findByAgeBetween(ageMin, ageMax));
    }

    public Collection<Student> findAll() {
        logger.info("Method findByAll() has been invoked.");
        return Collections.unmodifiableCollection(studentRepository.findAll());
    }

    public Optional<Faculty> getFacultyByStudentId(long id) {
        logger.info("Method getFacultyByStudentId(long id={}) has been invoked.", id);
        return studentRepository.findById(id)
                .map(Student::getFaculty);
    }
}
