package pro.sky.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.hogwarts.school.entity.Faculty;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
@Tag(name="students", description="Endpoints to operate upon students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/")
    public Student addStudent(@RequestBody Student student) {

        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        return studentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getFacultyByStudentId(@PathVariable long id) {
        return studentService.getFacultyByStudentId(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("/")
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {

        return studentService.editStudent(student)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        return studentService.deleteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping(params="age")
    public Collection<Student> getStudentsByAge(@RequestParam(required=false)  Integer age) {
        return studentService.findByAge(age);
    }

    @GetMapping(params = {"age0", "age1"})
    public Collection<Student> getStudentsByAge(@RequestParam(name = "age0") Integer ageMin,
                                                @RequestParam(name = "age1") Integer ageMax) {
        return studentService.findByAgeBetween(ageMin, ageMax);
    }

    @GetMapping("/all")
    public Collection<Student> getAllStudents() {
        return studentService.findAll();
    }

}
