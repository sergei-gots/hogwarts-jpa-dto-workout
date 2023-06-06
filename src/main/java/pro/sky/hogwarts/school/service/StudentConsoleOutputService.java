package pro.sky.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.repository.FacultyRepository;
import pro.sky.hogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentConsoleOutputService {

    private final StudentRepository studentRepository;
    private final Logger logger;

    public StudentConsoleOutputService(StudentRepository studentRepository,
                                       FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        logger = LoggerFactory.getLogger(StudentConsoleOutputService.class);
    }


    public Collection<Student> print6StudentsInConsole() {
        logger.info("Method print6StudentsInConsole() has been invoked.");
        List<Student> students = Collections.unmodifiableList(studentRepository.findAll());
        print2Students(students, 0);
        new Thread(() -> print2Students(students, 2)).start();
        new Thread(() -> print2Students(students, 4)).start();
        return students;
    }

    private void print2Students(List<Student> students, int startIndex) {
        if(students.size()>=startIndex) {
            logger.info("Thread-{}: student-info:{}",
                    Thread.currentThread().getId(),
                    students.get(startIndex));
        }
        if(students.size()>startIndex) {
            logger.info("Thread-{}: student-info:{}",
                    Thread.currentThread().getId(),
                    students.get(startIndex+1) );
        }
    }

    public Collection<Student> print6StudentsInConsoleSynchronized() {
        logger.info("Method print6StudentsInConsoleSynchronized() has been invoked.");
        List<Student> students = Collections.unmodifiableList(studentRepository.findAll());
        print2Students(students, 0);
        new Thread(() -> print2Students(students, 2)).start();
        new Thread(() -> print2Students(students, 4)).start();
        return students;
    }

    private synchronized void print2StudentsSynchronized(List<Student> students, int startIndex) {
        if(students.size()>=startIndex) {
            logger.info("{}: student-info:{}",
                    Thread.currentThread().getName(),
                    students.get(startIndex));
        }
        if(students.size()>startIndex) {
            logger.info("{}: student-info:{}",
                    Thread.currentThread().getName(),
                    students.get(startIndex+1) );
        }
    }
}