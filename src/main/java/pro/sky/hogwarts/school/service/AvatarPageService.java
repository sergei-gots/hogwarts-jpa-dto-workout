package pro.sky.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.dto.AvatarDto;
import pro.sky.hogwarts.school.repository.AvatarRepository;

import java.util.Collection;

@Service
public class AvatarPageService {
    private final AvatarRepository avatarRepository;
    private static String applicationHomeUrl;

    public static String getApplicationHomeUrl() {
        return applicationHomeUrl;
    }

    public AvatarPageService(
            AvatarRepository avatarRepository,
            @Value("${application.home.url}") String applicationHomeUrl
    ) {
        this.avatarRepository = avatarRepository;
        AvatarPageService.applicationHomeUrl =applicationHomeUrl;
    }

    public Collection<AvatarDto> getAvatarPage(int pageNumber, int pageSize){
        return avatarRepository
                .page(PageRequest.of(pageNumber, pageSize));
    }
}
