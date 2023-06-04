package pro.sky.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    final private Integer port;

    public InfoController(@Value("${server.port:8080}") Integer port) {
        this.port = port;
    }

    @GetMapping("/getPort")
    ResponseEntity<Integer> getPort(){
        return ResponseEntity.ok(port);
    }

}
