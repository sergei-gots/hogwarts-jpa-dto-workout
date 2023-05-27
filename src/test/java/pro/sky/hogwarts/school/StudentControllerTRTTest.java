package pro.sky.hogwarts.school;


import com.github.javafaker.Faker;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.hogwarts.school.entity.Faculty;
import pro.sky.hogwarts.school.repository.FacultyRepository;
import pro.sky.hogwarts.school.repository.StudentRepository;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTRTTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    FacultyRepository facultyRepository;

    private final Faker faker = new Faker();

    @Test
    public void addStudent_test() {
        addFaculty_and_test(generateFaculty());
    }

    private Faculty addFaculty_and_test(Faculty faculty) {
        final String url = "http://localhost:" + port + "/hogwarts/faculty";
        ResponseEntity<Faculty> facultyResponseEntity =
                testRestTemplate.postForEntity(
                        url,
                        faculty, Faculty.class
                );
        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty responseFaculty = facultyResponseEntity.getBody();
        assertThat(responseFaculty).isNotNull();
        assertThat(responseFaculty).usingRecursiveComparison().isEqualTo(faculty);
        assertThat(responseFaculty.getId()).isNotNull();

        return responseFaculty;
    }
    private Faculty generateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName(faker.harryPotter().house());
        faculty.setColor(faker.color().name());
        return faculty;
    }


}
