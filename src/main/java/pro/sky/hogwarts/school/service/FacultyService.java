package pro.sky.hogwarts.school.service;

import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.entity.Faculty;
import pro.sky.hogwarts.school.repository.FacultyRepository;


import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Optional<Faculty> getById(Long id) {

        return facultyRepository.findById(id);
    }

    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty>editFaculty(Faculty newFacultyData) {
        return facultyRepository.findById(newFacultyData.getId())
                .map(existingFaculty -> {
                    existingFaculty.setName(newFacultyData.getName());
                    existingFaculty.setColor(newFacultyData.getColor());
                    return facultyRepository.save(existingFaculty);
                });
    }

    public Optional<Faculty> deleteById(Long id) {
        return facultyRepository.findById(id)
                .map(faculty -> {
                    facultyRepository.deleteById(id);
                    return faculty;
                });
    }

    public Collection<Faculty> findByColor(String color) {
        return Collections.unmodifiableCollection(facultyRepository.findByColor((color)));
    }

    public Collection<Faculty> findAll() {
        return Collections.unmodifiableCollection(facultyRepository.findAll());
    }
}
