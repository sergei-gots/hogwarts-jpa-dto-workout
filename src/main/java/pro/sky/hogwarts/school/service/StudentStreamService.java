package pro.sky.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.entity.Faculty;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.repository.FacultyRepository;
import pro.sky.hogwarts.school.repository.StudentRepository;

import java.util.stream.Stream;

@Service
public class StudentStreamService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    private final Logger logger;

    public StudentStreamService(StudentRepository studentRepository,
                                FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        logger = LoggerFactory.getLogger(StudentStreamService.class);
    }

    public Stream<Student> findStudentsWithNameStartsWithLetter(Character letter) {
        logger.info("Method findStudentsWithNameStartsWithLetter(Character letter={}) has been invoked.",
                letter);
        return studentRepository.findAll().stream()
                .filter(student -> student.getName().charAt(0) == letter)
                .sorted((student1, student2) ->
                        student1.getName().
                                compareToIgnoreCase(student2.getName())
                );
    }

    public Double findStudentsAverageAge() {
        logger.info("Method findStudentsAverageAge() has been invoked.");
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(Double.NaN);
    }

    public String findFirstOfTheLongestFacultyName() {
        logger.info("Method findTheLongestFacultyName() has been invoked.");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .reduce("",
                        (theLongest, next) ->
                                (next != null && next.length() > theLongest.length()) ?
                                        next : theLongest
                );
    }


    public Pair<Long, Long> calcQuasiSumOfArithmeticProgressionFrom1to1MUsingReduce(int repeats) {
        logger.info("Method calcQuasiSumOfArithmeticProgressionFrom1to1MusingReduce(int repeats={}) has been invoked.",
                repeats);
        long result = 0;
        long time0 = System.currentTimeMillis();
        for (int i = 0; i < repeats; i++) {
            result = Stream.iterate(1, a -> a + 1)
                    .limit(1_000_000)
                    .reduce(Integer::sum).orElse(0);
        }
        long time1 = System.currentTimeMillis();
        return Pair.of(result, time1 - time0);
    }

    public Pair<Long, Long> calcQuasiSumOfArithmeticProgressionFrom1to1MUsingFormula(int repeats) {
        logger.info("Method calcQuasiSumOfArithmeticProgressionFrom1to1MusingFormula(int repeats={}) has been invoked.",
                repeats);
        long result = 0;
        long time0 = System.currentTimeMillis();
        for (int i = 0; i < repeats; i++) {
            result = (1 + 1_000_000) * (1_000_000 / 2);
            //should be: long result = (1L + 1_000_000L) * (1_000_000L / 2L);
        }
        long time1 = System.currentTimeMillis();
        return Pair.of(result, time1 - time0);
    }
}
