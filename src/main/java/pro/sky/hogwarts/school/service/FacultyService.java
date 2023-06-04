package pro.sky.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger;

    public FacultyService(FacultyRepository facultyRepository,
                          StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
        logger = LoggerFactory.getLogger(AvatarService.class);
        logger.info("FacultyService instance has been successfully created.");
    }

    public Optional<Faculty> getById(Long id) {
        logger.info("Method getById(Long id={}) has been invoked.", id);
        return facultyRepository.findById(id);
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Method addFaculty(Faculty faculty={}) has been invoked.", faculty);
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    public Optional<Faculty>editFaculty(Faculty newFacultyData) {
        logger.info("Method editFaculty(Faculty newFacultyData={}) has been invoked.",
                newFacultyData);
        return facultyRepository.findById(newFacultyData.getId())
                .map(existingFaculty -> {
                    existingFaculty.setName(newFacultyData.getName());
                    existingFaculty.setColor(newFacultyData.getColor());
                    return facultyRepository.save(existingFaculty);
                });
    }

    public Optional<Faculty> deleteById(Long id) {
        logger.info("Method deleteById(Long id={}) has been invoked.", id);
        return facultyRepository.findById(id)
                .map(faculty -> {
                    facultyRepository.deleteById(id);
                    return faculty;
                });
    }

    public Collection<Faculty> findAll() {
        logger.info("Method findAll() has been invoked.");
        return Collections.unmodifiableCollection(facultyRepository.findAll());
    }

    public Collection<Faculty> findByColor(String color) {
        logger.info("Method findByColor(String color={}) has been invoked.", color);
        return Collections.unmodifiableCollection(facultyRepository.findByColor((color)));
    }

    public Collection<Faculty> findByColorIgnoreCaseOrNameIgnoreCase(String colorOrName) {
        logger.info("Method findByColorIgnoreCaseOrNameIgnoreCase(String colorOrName={}) has been invoked.",
                colorOrName);
        return Collections.unmodifiableCollection(
               facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(
                       colorOrName,
                       colorOrName)
            );
    }

    public Collection<Student> findStudentsByFacultyId(long id) {
        logger.info("Method findStudentsByFacultyId(long id={}) has been invoked.", id);
        return studentRepository.findByFacultyId(id);
    }
}
