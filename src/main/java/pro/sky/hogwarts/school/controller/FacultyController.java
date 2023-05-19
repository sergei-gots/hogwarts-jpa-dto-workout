package pro.sky.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.hogwarts.school.model.Faculty;
import pro.sky.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("/")
    public void addFaculty(@RequestBody Faculty faculty) {
        facultyService.addFaculty(faculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping()
    public Collection<Faculty> getFacultiesWithColorEqualTo(@RequestParam String color) {
        return facultyService.getFacultiesWithColorEqualTo(color);
    }

    @GetMapping("/all")
    public Collection<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @PutMapping("/")
    public void editFaculty(@RequestBody Faculty faculty) {
        facultyService.editFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.removeFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
}
