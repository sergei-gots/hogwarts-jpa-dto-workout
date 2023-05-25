package pro.sky.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.hogwarts.school.entity.Avatar;
import pro.sky.hogwarts.school.service.AvatarService;

@RestController
@RequestMapping("/avatar")
@Tag(name = "avatars", description = "End points to operate students avatars")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/{student_id}")
    ResponseEntity<Avatar> getAvatarByStudentId(
            @PathVariable(name = "student_id") long studentId) {
        return avatarService.getAvatarByStudentId(studentId)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
}
