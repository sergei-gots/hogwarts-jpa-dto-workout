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
import java.util.Iterator;
import java.util.Optional;

@Service
public class StudentConsoleOutputService {

    private final StudentRepository studentRepository;
    private final Logger logger;

    public StudentConsoleOutputService(StudentRepository studentRepository,
                                       FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        logger = LoggerFactory.getLogger(StudentConsoleOutputService.class);
    }


    public Collection<Student> printAllInConsole() {
        logger.info("Method printAllInConsole() has been invoked.");
        Collection<Student> result = Collections.unmodifiableCollection(studentRepository.findAll());
        Iterator iterator = result.iterator();
        print2Students(iterator);
        new Thread(() -> print2Students(iterator)).start();
        new Thread(() -> print2Students(iterator)).start();
        return result;
    }

    private void print2Students(Iterator iterator) {
        for (int i = 0; i < 2; i++) {
            if (iterator.hasNext()) {
                System.out.println(Thread.currentThread().getName() +
                        ": student-info: " +
                        iterator.next());
            }
        }
    }

    public Collection<Student> printAllInConsoleSynchronized() {
        logger.info("Method printAllInConsoleSyncronized() has been invoked.");
        Collection<Student> result = Collections.unmodifiableCollection(studentRepository.findAll());
        Iterator iterator = result.iterator();
        print2Students(iterator);
        new Thread(() -> print2Students(iterator)).start();
        new Thread(() -> print2Students(iterator)).start();
        return result;
    }


    private synchronized void print2StudentsSynchronized(Iterator iterator) {
        for (int i = 0; i < 2; i++) {
            if (iterator.hasNext()) {
                System.out.println(Thread.currentThread().getName() +
                        ": student-info: " +
                        iterator.next());
            }
        }
    }
}