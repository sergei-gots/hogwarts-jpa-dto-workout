package pro.sky.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pro.sky.hogwarts.school.dto.AvatarDto;
import pro.sky.hogwarts.school.repository.AvatarRepository;

import java.util.Collection;

@Service
public class AvatarPageService {
    private final AvatarRepository avatarRepository;
    private static String appHomeUrl;
    private final Logger logger;
    public static String getAppHomeUrl() {
        return appHomeUrl;
    }

    public AvatarPageService(
            AvatarRepository avatarRepository,
            @Value("${server.address}") String serverAddress,
            @Value("${server.port:8080}") String serverPort,
            @Value("${server.servlet.context-path}") String serverServletContext
    ) {
        this.avatarRepository = avatarRepository;
        AvatarPageService.appHomeUrl =
                "http://" + serverAddress + ':' + serverPort + serverServletContext;
        logger = LoggerFactory.getLogger(AvatarPageService.class);
        logger.info("AvatarPageService instance has been successfully created.");
        logger.info("appHomeUrl=\"{}\".", appHomeUrl);
    }

    public Collection<AvatarDto> getAvatarPage(int pageNumber, int pageSize){
        logger.info("Method getAvatarPage(int pageNumber={}, int pageSize={}) has been invoked.",
                pageNumber, pageSize);
        return avatarRepository
                .page(PageRequest.of(pageNumber, pageSize, Sort.by("id")));
    }
}
