package pro.sky.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.model.Faculty;


import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> faculties = new HashMap<>();

    public ResponseEntity<Faculty> getFaculty(Long id) {
        Faculty faculty =  faculties.get(id);
        if(faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    public void addFaculty(Faculty faculty) {
        faculty.setNextId();
        faculties.put(faculty.getId(), faculty);
    }

    public ResponseEntity<?> editFaculty(Faculty faculty) {
        if(!faculties.containsKey(faculty.getId())) {
            return ResponseEntity.notFound().build();
        }
        faculties.put(faculty.getId(), faculty);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Faculty> removeFaculty(Long id) {
        if(!faculties.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculties.remove(id));
    }

    public Collection<Faculty> getFacultiesWithColorEqualTo(String color) {
        return faculties.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toUnmodifiableList());
    }

    public Collection<Faculty> getAllFaculties() {
        return Collections.unmodifiableCollection(faculties.values());
    }
}
