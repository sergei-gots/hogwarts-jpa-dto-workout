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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers= FacultyController.class)
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

    //hogwarts/faculty/
    private final String url = "/hogwarts/faculty/";
    @Autowired
    private ObjectMapper objectMapper;
    private final Faker faker = new Faker();

    @Test
    public void create_test() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName(faker.harryPotter().house());
        faculty.setColor(faker.color().name());

        when(facultyRepository.save(any())).thenReturn(faculty);

        mockMvc.perform(
                MockMvcRequestBuilders.post(
                        url,
                        MediaType.APPLICATION_JSON,
                        objectMapper.writeValueAsString(faculty)

                )
        )
                .andExpect(result -> {
                            MockHttpServletResponse mockHttpServletResponse =
                                result.getResponse();
                            Faculty facultyResult = objectMapper.readValue(
                                    mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8),
                                    Faculty.class);
                            assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK);
                            assertThat(facultyResult)
                                    .isNotNull()
                                    .usingRecursiveComparison().isEqualTo(faculty);
                        }

                );
    }
}
