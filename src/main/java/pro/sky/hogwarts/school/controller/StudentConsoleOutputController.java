package pro.sky.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.service.StudentConsoleOutputService;
import pro.sky.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
@Tag(name="students", description="Prints results into console")
public class StudentConsoleOutputController {
    private final StudentConsoleOutputService studentConsoleOutputService;

    public StudentConsoleOutputController(StudentConsoleOutputService studentConsoleOutputService) {
        this.studentConsoleOutputService = studentConsoleOutputService;
    }


    @GetMapping("/print-all")
    public Collection<Student> printAllStudentsInConsole() {

        return studentConsoleOutputService.printAllInConsole();
    }

    @GetMapping("/print-all-synchronized")
    public Collection<Student> printAllStudentsInConsoleSynchronized() {

        return studentConsoleOutputService.printAllInConsoleSynchronized();
    }

}
