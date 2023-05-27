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
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.repository.FacultyRepository;
import pro.sky.hogwarts.school.repository.StudentRepository;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTRTTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    private final Faker faker = new Faker();

    @Test
    public void addStudent_test() {
        addStudent_andTest(generateStdudent(addFaculty_and_test(generateFaculty())));
    }

    private Student addStudent_andTest(Student student) {
        ResponseEntity<Student> studentResponseEntity =
                                           //POST http://localhost:8080/hogwarts/student/
                testRestTemplate.postForEntity("http://localhost:" + port + "/hogwarts/student",
                        student,
                        Student.class);
        assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student responseStudent = studentResponseEntity.getBody();
        assertThat(responseStudent).isNotNull();
        assertThat(responseStudent)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(student);
        assertThat(responseStudent.getId()).isNotNull();
        return responseStudent;
    }

    private Faculty addFaculty_and_test(Faculty faculty) {
        ResponseEntity<Faculty> facultyResponseEntity =
                testRestTemplate.postForEntity(
                        "http://localhost:" + port + "/hogwarts/faculty",
                        faculty,
                        Faculty.class
                );
        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty responseFaculty = facultyResponseEntity.getBody();
        assertThat(responseFaculty).isNotNull();
        assertThat(responseFaculty)
                .usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(faculty);
        assertThat(responseFaculty.getId()).isNotNull();
        return responseFaculty;
    }
    private Faculty generateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName(faker.harryPotter().house());
        faculty.setColor(faker.color().name());
        return faculty;
    }

    private Student generateStdudent(Faculty faculty) {
        Student student = new Student();
        student.setFaculty(faculty);
        student.setName(faker.harryPotter().character());
        student.setAge(faker.random().nextInt(11,48));
        return student;
    }



}
