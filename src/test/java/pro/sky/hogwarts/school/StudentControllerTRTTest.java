package pro.sky.hogwarts.school;


import com.github.javafaker.Faker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.hogwarts.school.entity.Faculty;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.repository.FacultyRepository;
import pro.sky.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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

    private String hogwartsUrl() {
        return "http://localhost:" + port + "/hogwarts";
    }

    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    public void postStudent_test() {
        prepareStudent_andTest();
    }

    @Test
    public void put_setFaculty_test() {
        Faculty faculty1 = addFaculty_andTest(generateFaculty());
        Faculty faculty2 = addFaculty_andTest(generateFaculty());
        Student student = addStudent_andTest(generateStudent(faculty1));

        Student gotStudent = getStudent_andTest(student);
        assertThat(gotStudent.getFaculty()).usingRecursiveComparison().isEqualTo(faculty1);

        student.setFaculty(faculty2);
        Student putStudent = putStudent_andTest(student);
        assertThat(putStudent.getFaculty()).usingRecursiveComparison().isEqualTo(faculty2);
    }

    @Test
    public void put_setName_test() {
        Student student = prepareStudent_andTest();
        String name1 = student.getName();
        String name2 = faker.harryPotter().character();

        Student gotStudent = getStudent_andTest(student);
        assertThat(gotStudent.getName()).isEqualTo(name1);

        student.setName(name2);
        Student putStudent = putStudent_andTest(student);
        assertThat(putStudent.getName()).isEqualTo(name2);
    }

    @Test
    public void put_setAge_test() {
        Student student = prepareStudent_andTest();
        int age1 = student.getAge();
        int age2 = generateAge();

        Student gotStudent = getStudent_andTest(student);
        assertThat(gotStudent.getAge()).isEqualTo(age1);

        student.setAge(age2);
        Student putStudent = putStudent_andTest(student);
        assertThat(putStudent.getAge()).isEqualTo(age2);
    }

    @Test
    public void findFacultyByStudentId_test() {
        Student student = prepareStudent_andTest();
        //GET http://localhost:8080/hogwarts/student/1/faculty
        ResponseEntity<Faculty> facultyResponseEntity =
                testRestTemplate.getForEntity(
                        hogwartsUrl() +
                                "/student/"  +
                                student.getId() +
                                "/faculty", Faculty.class);
        assertThat(facultyResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(facultyResponseEntity.getBody())
                .isNotNull()
                .usingRecursiveComparison().isEqualTo(student.getFaculty());

    }


    @Test
    public void findByAge_test() {
        List<Student> students = prepareStudents();
        int age = 23;

        List<Student> expectedStudents = students.stream()
                .filter(studentRecord ->
                        studentRecord.getAge() == age
                )
                .collect(Collectors.toList());

        //GET http://localhost:8080/hogwarts/student?age=23
        testCollectionResponseEntity(
                testRestTemplate.exchange(hogwartsUrl() +
                                "/student?age={age}",
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<>() {
                        },
                        age
                ),
                expectedStudents
        );
    }


    @Test
    public void findByAgeBetween_test() {
        List<Student> students = prepareStudents();
        int age0 = 14;
        int age1 = 23;

        List<Student> expectedStudents = students.stream()
                .filter(studentRecord ->
                        studentRecord.getAge() >= age0 &&
                                studentRecord.getAge() <= age1
                )
                .collect(Collectors.toList());

        //GET http://localhost:8080/hogwarts/student?age0=14&age1=23
        testCollectionResponseEntity(
                testRestTemplate.exchange(hogwartsUrl() +
                                "/student?age0={age0}&age1={age1}",
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<>() {
                        },
                        age0, age1
                ),
                expectedStudents);
    }

    @Test
    public void deleteStudent_test() {
        List<Student> students = prepareStudents();
        final int initialSize = students.size();
        Student studentToBeDeleted = students.get(faker.random().nextInt(initialSize));
        students.remove(studentToBeDeleted);

        //DELETE http://localhost:8080/hogwarts/student/{id}
        ResponseEntity<Student> deleteResponseEntity = testRestTemplate.exchange(
                hogwartsUrl() + "/student/{id}",
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                },
                studentToBeDeleted.getId()
        );

        assertThat(deleteResponseEntity).isNotNull();
        assertThat(deleteResponseEntity.getBody())
                .usingRecursiveComparison().isEqualTo(studentToBeDeleted);

        assertThat(studentToBeDeleted)
                .usingRecursiveComparison().isNotIn(
                        testCollectionResponseEntity(testRestTemplate.exchange(
                                        hogwartsUrl() + "/student/all",
                                        HttpMethod.GET,
                                        HttpEntity.EMPTY,
                                        new ParameterizedTypeReference<>() {
                                        }
                                ),
                                students
                        )
                );


    }

    @Test
    public void getAll_test() {
        List<Student> students = prepareStudents();

        ResponseEntity<Collection<Student>> getAllResponseEntity =
                testRestTemplate.exchange(
                        hogwartsUrl() + "/student/all",
                        HttpMethod.GET,
                        HttpEntity.EMPTY,
                        new ParameterizedTypeReference<>() {
                        }
                );
        testCollectionResponseEntity(getAllResponseEntity, students);
    }

    private List<Student> prepareStudents() {
        List<Faculty> faculties = generateFaculties();
        return generateStudents(faculties);
    }

    private Collection<Student> testCollectionResponseEntity(
            ResponseEntity<Collection<Student>> responseEntity,
            Collection<Student> expected) {

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Collection<Student> actual = responseEntity.getBody();
        assertThat(actual)
                .isNotNull()
                .hasSize(expected.size())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expected);
        return responseEntity.getBody();
    }

    private List<Student> generateStudents(List<Faculty> faculties) {
        return Stream.generate(
                        () -> generateStudent(faculties.get(faker.random().nextInt(faculties.size())))
                )
                .limit(50)
                .map(this::addStudent_andTest)
                .collect(Collectors.toList());
    }

    private List<Faculty> generateFaculties() {
        return Stream.generate(this::generateFaculty)
                .limit(5)
                .map(this::addFaculty_andTest)
                .collect(Collectors.toList());
    }

    private Student putStudent_andTest(Student student) {
        ResponseEntity<Student> putStudentResponseEntity =
                testRestTemplate.exchange(
                        hogwartsUrl() + "/student",
                        HttpMethod.PUT,
                        new HttpEntity<>(student),
                        Student.class);
        assertThat(putStudentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student putStudent = putStudentResponseEntity.getBody();
        assertThat(putStudent).isNotNull();
        assertThat(putStudent).usingRecursiveComparison().isEqualTo(student);
        return putStudent;
    }

    private Student getStudent_andTest(Student student) {
        ResponseEntity<Student> getStudentResponseEntity =
                testRestTemplate.getForEntity(
                        hogwartsUrl() + "/student/" + student.getId(),
                        Student.class);
        assertThat(getStudentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student gotStudent = getStudentResponseEntity.getBody();
        assertThat(gotStudent).isNotNull();
        assertThat(gotStudent).usingRecursiveComparison().isEqualTo(student);
        return gotStudent;
    }

    private Student addStudent_andTest(Student student) {
        ResponseEntity<Student> studentResponseEntity =
                //POST http://localhost:8080/hogwarts/student/
                testRestTemplate.postForEntity(hogwartsUrl() + "/student",
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

    private Student prepareStudent_andTest() {
        Faculty faculty = addFaculty_andTest(generateFaculty());
        return addStudent_andTest(generateStudent(faculty));
    }

    private Faculty addFaculty_andTest(Faculty faculty) {
        //POST http://localhost:8080/hogwarts/faculty/
        ResponseEntity<Faculty> facultyResponseEntity =
                testRestTemplate.postForEntity(
                        hogwartsUrl() + "/faculty",
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

    private Student generateStudent(Faculty faculty) {
        Student student = new Student();
        student.setFaculty(faculty);
        student.setName(faker.harryPotter().character());
        student.setAge(generateAge());
        return student;
    }

    private int generateAge() {
        return faker.random().nextInt(11, 48);
    }

}
