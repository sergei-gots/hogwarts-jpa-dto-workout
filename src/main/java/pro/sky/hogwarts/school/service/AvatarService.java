package pro.sky.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.entity.Avatar;
import pro.sky.hogwarts.school.repository.AvatarRepository;

import java.util.Optional;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Optional<Avatar> getAvatarByStudentId(long studentId) {
        return avatarRepository.findByStudentId(studentId);
    }
}
