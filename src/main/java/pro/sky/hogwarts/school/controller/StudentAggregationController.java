package pro.sky.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.hogwarts.school.service.StudentAggregationService;

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
}
