package pro.sky.hogwarts.school;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.hogwarts.school.controller.FacultyController;
import pro.sky.hogwarts.school.entity.Faculty;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.repository.FacultyRepository;
import pro.sky.hogwarts.school.repository.StudentRepository;
import pro.sky.hogwarts.school.service.FacultyService;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = FacultyController.class)
@ExtendWith(MockitoExtension.class)
public class FacultyControllerWebMvcTestWithMockedRepositoriesTest {

    @Autowired
    private MockMvc mockMvc;

    //We will mock the Database Layer (repositories):
    @MockBean
    private FacultyRepository facultyRepository;
    @MockBean
    private StudentRepository studentRepository;
    //We will have real FacultyService:
    @SpyBean
    private FacultyService facultyService;
    //Therefore, we will check how the controller and service work,
    //and we will not check what occurs in the database.

    private final String url = "/faculty/";
    @Autowired
    private ObjectMapper objectMapper;
    private final Faker faker = new Faker();

    @Test
    public Faculty when_create_faculty_test() throws Exception {
        Faculty faculty = generateFaculty(1L);

        when(facultyRepository.save(any())).thenReturn(faculty);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(faculty)

                                )
                )
                .andExpect(result -> {
                            MockHttpServletResponse mockHttpServletResponse =
                                    result.getResponse();
                            Faculty facultyResult = objectMapper.readValue(
                                    mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8),
                                    Faculty.class);
                            assertThat(mockHttpServletResponse.getStatus())
                                    .isEqualTo(HttpStatus.OK.value());
                            assertThat(facultyResult)
                                    .isNotNull()
                                    .usingRecursiveComparison().isEqualTo(faculty);
                        }
                );
        return faculty;
    }

    @Test
    public void when_update_faculty_test() throws Exception {
        Faculty faculty = when_create_faculty_test();

        faculty.setName(faker.harryPotter().house());
        faculty.setColor(faker.color().name());

        when(facultyRepository.save(any())).thenReturn(faculty);
        when(facultyRepository.findById(faculty.getId())).thenReturn(Optional.of(faculty));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(faculty)

                                )
                )
                .andExpect(result -> {
                            MockHttpServletResponse mockHttpServletResponse =
                                    result.getResponse();
                            Faculty facultyResult = objectMapper.readValue(
                                    mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8),
                                    Faculty.class);
                            assertThat(mockHttpServletResponse.getStatus())
                                    .isEqualTo(HttpStatus.OK.value());
                            assertThat(facultyResult)
                                    .isNotNull()
                                    .usingRecursiveComparison().isEqualTo(faculty);
                        }
                );
    }

    @Test
    public void when_id_is_listed_then_get_returns_OK_test() throws Exception {
        long id = 1;
        Faculty faculty = generateFaculty(id);
        Optional<Faculty> optionalFaculty = Optional.of(faculty);

        when(facultyRepository.findById(id)).thenReturn(optionalFaculty);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(url + id)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(result -> {
                            MockHttpServletResponse mockHttpServletResponse =
                                    result.getResponse();
                            assertThat(mockHttpServletResponse.getStatus())
                                    .isEqualTo(HttpStatus.OK.value());
                            Faculty facultyResult = objectMapper.readValue(
                                    mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8),
                                    Faculty.class);
                            assertThat(facultyResult)
                                    .isNotNull()
                                    .usingRecursiveComparison().isEqualTo(faculty);

                        }
                );
    }

    @Test
    public void when_id_is_not_listed_then_we_get_NOT_FOUND_test() throws Exception {
        long wrongId = 2;

        when(facultyRepository.findById(wrongId)).thenReturn(Optional.empty());

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(url + wrongId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(result -> {
                            MockHttpServletResponse mockHttpServletResponse =
                                    result.getResponse();
                            assertThat(mockHttpServletResponse.getStatus())
                                    .isEqualTo(HttpStatus.NOT_FOUND.value());
                            String content = mockHttpServletResponse.getContentAsString();
                            assertThat(content.isEmpty()).isTrue();
                        }
                );
    }

    @Test
    public void when_id_is_listed_then_delete_returns_OK_test() throws Exception {
        long id = 1;
        Faculty faculty = generateFaculty(id);
        Optional<Faculty> optionalFaculty = Optional.of(faculty);

        when(facultyRepository.findById(id)).thenReturn(optionalFaculty);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete(url + id)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(result -> {
                            MockHttpServletResponse mockHttpServletResponse =
                                    result.getResponse();
                            assertThat(mockHttpServletResponse.getStatus())
                                    .isEqualTo(HttpStatus.OK.value());
                            Faculty facultyResult = objectMapper.readValue(
                                    mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8),
                                    Faculty.class);
                            assertThat(facultyResult)
                                    .isNotNull()
                                    .usingRecursiveComparison().isEqualTo(faculty);

                        }
                );
    }

    @Test
    public void when_id_is_not_listed_then_delete_returns_NOT_FOUND_test() throws Exception {
        long wrongId = 2;

        when(facultyRepository.findById(wrongId)).thenReturn(Optional.empty());

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete(url + wrongId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(result -> {
                            MockHttpServletResponse mockHttpServletResponse =
                                    result.getResponse();
                            assertThat(mockHttpServletResponse.getStatus())
                                    .isEqualTo(HttpStatus.NOT_FOUND.value());
                            String content = mockHttpServletResponse.getContentAsString();
                            assertThat(content.isEmpty()).isTrue();
                        }
                );
    }

    @Test
    public void when_get_students_by_faculty_id_test() throws Exception {
        long id = 1;
        Faculty faculty = generateFaculty(id);
        Collection<Student> students = Stream.generate(() -> {
            Student student = new Student();
            student.setId(faker.random().nextLong());
            student.setName(faker.harryPotter().character());
            student.setFaculty(faculty);
            return student;
        }).limit(10).collect(Collectors.toList());

        when(studentRepository.findByFacultyId(id)).thenReturn(students);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(url + id + "/students")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(result -> {
                            MockHttpServletResponse mockHttpServletResponse =
                                    result.getResponse();
                            assertThat(mockHttpServletResponse.getStatus())
                                    .isEqualTo(HttpStatus.OK.value());
                            Collection<Student> studentsResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(),
                            new TypeReference<>() {
                            });
                            assertThat(studentsResult)
                                    .isNotNull()
                                    .hasSize(students.size())
                                    .usingRecursiveFieldByFieldElementComparator()
                                    .containsExactlyInAnyOrderElementsOf(students);
                        }
                );
    }

    @Test
    public void when_get_all_faculties_test() throws Exception {
        List<Faculty> faculties = generateFaculites();
        when(facultyRepository.findAll()).thenReturn(faculties);
        performRequestAndCheckOkResult(
                MockMvcRequestBuilders
                        .get(url + "all")
                        .contentType(MediaType.APPLICATION_JSON),
                faculties
        );
    }

    @Test
    public void when_get_all_faculties_by_color_test() throws Exception {
        List<Faculty> faculties = generateFaculites();
        String color = "someColor";
        when(facultyRepository.findByColor(eq(color))).thenReturn(faculties);
        performRequestAndCheckOkResult(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("color", color), faculties);

    }

    @Test
    public void when_get_faculties_by_colror_or_name_ignore_case_test() throws Exception {
        List<Faculty> faculties = generateFaculites();

        String colorOrName = "SomeCOloROrName";
        when(facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(eq(colorOrName), eq(colorOrName))).thenReturn(faculties);
        performRequestAndCheckOkResult(
                MockMvcRequestBuilders
                        .get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("color_or_name", colorOrName),
                faculties);
    }

    private void performRequestAndCheckOkResult(MockHttpServletRequestBuilder mockHttpServletRequestBuilder,
                                                Collection<Faculty> expected) throws Exception {
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(result -> {
                            MockHttpServletResponse mockHttpServletResponse =
                                    result.getResponse();
                            assertThat(mockHttpServletResponse.getStatus())
                                    .isEqualTo(HttpStatus.OK.value());
                            Collection<Faculty> resultList =
                                    objectMapper.readValue(mockHttpServletResponse.getContentAsString(),
                                            new TypeReference<>() {
                                            });
                            assertThat(resultList)
                                    .isNotNull()
                                    .hasSize(expected.size())
                                    .usingRecursiveFieldByFieldElementComparator()
                                    .containsExactlyInAnyOrderElementsOf(expected);
                        }
                );
    }

    private Faculty generateFaculty(long id) {
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(faker.harryPotter().house());
        faculty.setColor(faker.color().name());
        return faculty;
    }

    private List<Faculty> generateFaculites() {
        return  Stream
                .generate(() -> generateFaculty(faker.random().nextLong()))
                .limit(10).collect(Collectors.toList());
    }
}
