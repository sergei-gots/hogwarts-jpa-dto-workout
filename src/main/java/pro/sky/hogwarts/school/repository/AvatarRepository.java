package pro.sky.hogwarts.school.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.hogwarts.school.dto.AvatarDto;
import pro.sky.hogwarts.school.entity.Avatar;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(long studentId);

    @Query(
            "SELECT new pro.sky.hogwarts.school.dto.AvatarDto(id, fileSize, mediaType, student.id) FROM Avatar")
    List<AvatarDto> page(Pageable pageable);
}
