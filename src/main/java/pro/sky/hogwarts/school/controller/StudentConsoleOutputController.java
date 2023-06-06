package pro.sky.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.service.StudentConsoleOutputService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
@Tag(name="students", description="Prints results into console")
public class StudentConsoleOutputController {
    private final StudentConsoleOutputService studentConsoleOutputService;

    public StudentConsoleOutputController(StudentConsoleOutputService studentConsoleOutputService) {
        this.studentConsoleOutputService = studentConsoleOutputService;
    }


    @GetMapping("/print-6-students")
    public Collection<Student> print6StudentsInConsole() {

        return studentConsoleOutputService.print6StudentsInConsole();
    }

    @GetMapping("/print-6-students-synchronized")
    public Collection<Student> print6StudentsInConsoleSynchronized() {

        return studentConsoleOutputService.print6StudentsInConsoleSynchronized();
    }

}
