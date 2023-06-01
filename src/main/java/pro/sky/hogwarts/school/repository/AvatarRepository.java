package pro.sky.hogwarts.school.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.hogwarts.school.dto.AvatarDto;
import pro.sky.hogwarts.school.entity.Avatar;

import java.util.List;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(long studentId);

    @Query(
            "SELECT new pro.sky.hogwarts.school.dto.AvatarDto(id, fileSize, mediaType, student.id) FROM Avatar")
    List<AvatarDto> page(Pageable pageable);
}
