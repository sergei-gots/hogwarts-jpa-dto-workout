package pro.sky.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.service.StudentAggregationService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentAggregationController {
    private final StudentAggregationService studentAggregationService;

    public StudentAggregationController(StudentAggregationService studentAggregationService) {
        this.studentAggregationService = studentAggregationService;
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getStudentsCount() {
        return ResponseEntity.ok(studentAggregationService.getCount());
    }
    @GetMapping("/avg-age")
    public ResponseEntity<Double> getStudentsAverageAge() {
        return ResponseEntity.ok(studentAggregationService.getAvgAge());
    }

    @GetMapping("/last-5")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        return ResponseEntity.ok(
                studentAggregationService.getLastStudents(5)
        );
    }

    @GetMapping("/last")
    public ResponseEntity<Collection<Student>> getLastNStudents(
            @RequestParam(defaultValue = "5") int count
    ) {
        return ResponseEntity.ok(
                studentAggregationService.getLastStudents(count)
        );
    }
}
