package pro.sky.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.service.StudentStreamService;

import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/student")
@Tag(name = "students", description = "Endpoints to operate student streams")
public class StudentStreamController {
    private final StudentStreamService studentStreamService;

    public StudentStreamController(StudentStreamService studentStreamService) {

        this.studentStreamService = studentStreamService;
    }

    @GetMapping("/get-students-whose-names-start-with-letter/{letter}")
    public Stream<Student> getStudentsWithNameStartsWithLetter(@PathVariable Character letter) {
        return studentStreamService.findStudentsWithNameStartsWithLetter(letter);
    }

    @GetMapping("/get-average-age")
    public Double getStudentsAverageAge() {
        return studentStreamService.findStudentsAverageAge();
    }

    @GetMapping("/get-first-among-the-longest-faculty-name")
    public String getFirstOfTheLongestFacultyName() {
        return studentStreamService.findFirstOfTheLongestFacultyName();
    }

    @GetMapping({"/get-quasi-sum-of-arithmetic-progression-from-1-to-1M/{repeats}",
            "/get-quasi-sum-of-arithmetic-progression-from-1-to-1M"})
    public Stream<Pair<String, Pair<Long, Long>>>
    getSumOfArithmeticProgressionFrom1to1M(
            @PathVariable(required = false) Optional<Integer> repeats
    ) {
        int repeatsNumber = repeats.orElse(10);
        return Stream.<Pair<String, Pair<Long, Long>>>builder()
                .add(Pair.of(
                                "Sum using reduce(0, (a,b) -> a + b)",
                                studentStreamService.calcQuasiSumOfArithmeticProgressionFrom1to1MUsingReduce(repeatsNumber)
                        )
                ).add(Pair.of(
                                "Sum using formula",
                                studentStreamService.calcQuasiSumOfArithmeticProgressionFrom1to1MUsingFormula(repeatsNumber)
                        )
                )
                .build();
    }
}
