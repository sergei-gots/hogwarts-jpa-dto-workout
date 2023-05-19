package pro.sky.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }


    @PutMapping("/")
    public void editStudent(@RequestBody Student student) {
        studentService.editStudent(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student student = studentService.removeStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping()
    public Collection<Student> getStudentsWithAgeEqualTo(@RequestParam int age) {
        return studentService.getStudentsWithAgeEqualto(age);
    }

    @GetMapping("/all")
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

}
