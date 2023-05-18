package pro.sky.hogwarts.school.controller;

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
    public Faculty getFaculty(@PathVariable Long id) {
        return facultyService.getFaculty(id);
    }

    @PutMapping("/")
    public void editFaculty(@RequestBody Faculty faculty) {
        facultyService.editFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public Faculty deleteFaculty(@PathVariable Long id) {
        return facultyService.removeFaculty(id);
    }

    @GetMapping("/")
    public Collection<Faculty> getFacultiesWithColorEqualTo(@RequestAttribute String color) {
        return facultyService.getFacultiesWithColorEqualTo(color);
    }
}
