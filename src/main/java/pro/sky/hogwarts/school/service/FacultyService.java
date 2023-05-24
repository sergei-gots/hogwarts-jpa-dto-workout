package pro.sky.hogwarts.school.service;

import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.entity.Faculty;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.repository.FacultyRepository;
import pro.sky.hogwarts.school.repository.StudentRepository;


import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    public FacultyService(FacultyRepository facultyRepository,
                          StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
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

    public Collection<Faculty> findAll() {
        return Collections.unmodifiableCollection(facultyRepository.findAll());
    }

    public Collection<Faculty> findByColor(String color) {
        return Collections.unmodifiableCollection(facultyRepository.findByColor((color)));
    }

    public Collection<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(String colorOrName) {
        return Collections.unmodifiableCollection(
               facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(
                       colorOrName,
                       colorOrName)
            );
    }

    public Collection<Student> findStudentsByFacultyId(long id) {
        return studentRepository.findByFacultyId(id);
    }
}
