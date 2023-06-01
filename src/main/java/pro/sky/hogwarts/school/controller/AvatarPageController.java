package pro.sky.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.hogwarts.school.dto.AvatarDto;
import pro.sky.hogwarts.school.service.AvatarPageService;
import pro.sky.hogwarts.school.entity.Avatar;

import java.util.Collection;

@RestController
@RequestMapping("/avatar")
public class AvatarPageController {
    private final AvatarPageService avatarPageService;

    public AvatarPageController(AvatarPageService avatarPageService) {
        this.avatarPageService = avatarPageService;
    }

    @GetMapping
    public ResponseEntity<Collection<AvatarDto>> getAvatarPage(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "1") int size) {
        return ResponseEntity.ok(
                avatarPageService.getAvatarPage(page, size)
        );
    }
}
