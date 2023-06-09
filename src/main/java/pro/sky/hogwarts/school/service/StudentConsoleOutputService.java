package pro.sky.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.repository.StudentGetFirst6Repository;

import java.util.*;

@Service
public class StudentConsoleOutputService {

    private final StudentGetFirst6Repository studentRepository;
    private final Logger logger;

    public StudentConsoleOutputService(StudentGetFirst6Repository studentRepository) {
        this.studentRepository = studentRepository;
        logger = LoggerFactory.getLogger(StudentConsoleOutputService.class);
    }


    public List<Student> getFirst6() {
        logger.info("Method getFirst6() has been invoked.");
        return Collections.unmodifiableList(
                studentRepository.page(
                        PageRequest.of(0,6, Sort.by("students.id"))
                )
        );
    }

    public Collection<Student> print6StudentsInConsole() {
        logger.info("Method print6StudentsInConsole() has been invoked.");
        List<Student> students = getFirst6();
        print2Students(students, 0);
        new Thread(() -> print2Students(students, 2)).start();
        new Thread(() -> print2Students(students, 4)).start();
        return students;
    }

    private void print2Students(List<Student> students, int startIndex) {
        try {
            if (students.size() >= startIndex) {
                Thread.sleep(100);
                printToConsole(Thread.currentThread(), students.get(startIndex), startIndex);
            }
            if (students.size() > startIndex) {
                Thread.sleep(100);
                printToConsole(Thread.currentThread(), students.get(startIndex + 1), startIndex + 1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Collection<Student> print6StudentsInConsoleSynchronized() {
        logger.info("Method print6StudentsInConsoleSynchronized() has been invoked.");
        List<Student> students = getFirst6();
        print2Students(students, 0);
        synchronized (this) {
            new Thread(() -> print2StudentsSynchronized(students, 2)).start();
            new Thread(() -> print2StudentsSynchronized(students, 4)).start();
        }
        return students;
    }

    private synchronized void print2StudentsSynchronized(List<Student> students, int startIndex) {
        try {
            if (students.size() >= startIndex) {
                Thread.sleep(100);
                printToConsole(Thread.currentThread(), students.get(startIndex), startIndex);
            }
            if (students.size() > startIndex) {
                Thread.sleep(100);
                printToConsole(Thread.currentThread(), students.get(startIndex + 1), startIndex+1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void printToConsole(Thread thread, Student student, int index) {
        logger.trace("Thread-{}: student-info:{}",
                thread.getId(),
                student);
        System.out.println("Thread-" + thread.getId() + ": student-info#" + (index+1) + ":" + student);

    }
}