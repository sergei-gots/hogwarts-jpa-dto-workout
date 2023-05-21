package pro.sky.hogwarts.school;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class HogwartsSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(HogwartsSchoolApplication.class, args);
    }

}
