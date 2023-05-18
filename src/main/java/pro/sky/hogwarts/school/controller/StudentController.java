package pro.sky.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.hogwarts.school.model.Student;
import pro.sky.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/")
    public void addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PutMapping("/")
    public void editStudent(@RequestBody Student student) {
        studentService.editStudent(student);
    }

    @DeleteMapping("/{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.removeStudent(id);
    }

    @GetMapping("/")
    public Collection<Student> getStudentsWithAgeEqualTo(@RequestAttribute int age) {
        return studentService.getStudentsWithAgeEqualto(age);
    }

}
