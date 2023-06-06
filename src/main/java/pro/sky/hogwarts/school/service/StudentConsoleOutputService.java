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
        try {
            if (students.size() >= startIndex) {
                Thread.sleep(100);
                printToConsole(Thread.currentThread(), students.get(startIndex));
            }
            if (students.size() > startIndex) {
                Thread.sleep(100);
                printToConsole(Thread.currentThread(), students.get(startIndex + 1));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Collection<Student> print6StudentsInConsoleSynchronized() {
        logger.info("Method print6StudentsInConsoleSynchronized() has been invoked.");
        List<Student> students = Collections.unmodifiableList(studentRepository.findAll());
        print2Students(students, 0);
        new Thread(() -> print2StudentsSynchronized(students, 2)).start();
        new Thread(() -> print2StudentsSynchronized(students, 4)).start();
        return students;
    }

    private synchronized void print2StudentsSynchronized(List<Student> students, int startIndex) {
        try {
            if (students.size() >= startIndex) {
                Thread.sleep(100);
                printToConsole(Thread.currentThread(), students.get(startIndex));
            }
            if (students.size() > startIndex) {
                Thread.sleep(100);
                printToConsole(Thread.currentThread(), students.get(startIndex + 1));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printToConsole(Thread thread, Student student) {
        logger.trace("Thread-{}: student-info:{}",
                thread.getId(),
                student);
        System.out.println("Thread-" + thread.getId() + ": student-info:" + student);

    }
}