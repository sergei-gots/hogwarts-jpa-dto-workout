package pro.sky.hogwarts.school;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.hogwarts.school.controller.FacultyController;
import pro.sky.hogwarts.school.entity.Faculty;
import pro.sky.hogwarts.school.repository.FacultyRepository;
import pro.sky.hogwarts.school.repository.StudentRepository;
import pro.sky.hogwarts.school.service.FacultyService;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = FacultyController.class)
@ExtendWith(MockitoExtension.class)
public class FacultyControllerMockTest {

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
    //Therefore, we will check how the controller and service works,
    //and we will not check what occurs in the database.

    private final String url = "/faculty/";
    @Autowired
    private ObjectMapper objectMapper;
    private final Faker faker = new Faker();

    @Test
    public void create_test() throws Exception {
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
    }

    @Test
    public void get_test() throws Exception {
        long id = 1;
        Faculty faculty = generateFaculty(id);
        Optional<Faculty> optionalFaculty = Optional.of(faculty);

        when(facultyRepository.findById(any())).thenReturn(optionalFaculty);

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
    public void get_when_idIsNotListed_test() throws Exception {
        long id = 1;
        long wrongId = 2;
        Faculty faculty = generateFaculty(id);

        when(facultyRepository.findById(any())).thenReturn(Optional.empty());

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
    public void delete_test() throws Exception {
        long id = 1;
        Faculty faculty = generateFaculty(id);
        Optional<Faculty> optionalFaculty = Optional.of(faculty);

        when(facultyRepository.findById(any())).thenReturn(optionalFaculty);

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
    public void delete_when_idIsNotListed_test() throws Exception {
        long id = 1;
        long wrongId = 2;
        Faculty faculty = generateFaculty(id);

        when(facultyRepository.findById(any())).thenReturn(Optional.empty());

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

    private Faculty generateFaculty(long id) {
        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(faker.harryPotter().house());
        faculty.setColor(faker.color().name());
        return faculty;
    }
}
