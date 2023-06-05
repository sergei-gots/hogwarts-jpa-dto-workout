package pro.sky.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.hogwarts.school.entity.Avatar;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.exception.AvatarNotFoundException;
import pro.sky.hogwarts.school.exception.AvatarProcessingException;
import pro.sky.hogwarts.school.exception.StudentNotFoundException;
import pro.sky.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
@Transactional
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;
    private final String avatarsDirPath;
    private final Logger logger;

    public AvatarService(AvatarRepository avatarRepository,
                         StudentService studentService,
                         @Value("${application.avatars.dir.path}") String avatarsDirPath) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
        this.avatarsDirPath = avatarsDirPath;
        logger = LoggerFactory.getLogger(AvatarService.class);
        logger.info("AvatarService instance has been successfully created.");
    }

    public void uploadAvatarForStudentId(long studentId, MultipartFile avatarFile) {
        logger.info("Method uploadAvatarForStudentId(long studentId={}, MultipartFile avatarFile={}) has been invoked.",
                studentId, avatarFile);
        Student student = studentService
                .findById(studentId)
                .orElseThrow(StudentNotFoundException::new);
        Path filePath = Path.of(avatarsDirPath,
                studentId + "." +
                        StringUtils.getFilenameExtension(avatarFile.getOriginalFilename()));
        try {
            byte[] data = avatarFile.getBytes();
            Files.write(filePath, data);

            Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
            avatar.setStudent(student);
            avatar.setFilePath(filePath.toString());
            avatar.setMediaType(avatarFile.getContentType());
            avatar.setFileSize(avatarFile.getSize());
            avatar.setPreview(generateImagePreview(filePath));
            avatarRepository.save(avatar);
        } catch (IOException e) {
            logger.error("IOException e with message=\"{}\" during avatar upload occurred ",
                    e.getMessage()
            );
            throw new AvatarProcessingException();
        }
    }

    public Avatar getAvatarForStudentId(long studentId) {
        logger.info("Method getAvatarForStudentId(long studentId={}) has been invoked.",
                studentId);
        return avatarRepository.findByStudentId(studentId).orElseThrow(AvatarNotFoundException::new);
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        logger.info("Method generateImagePreview(Path filePath={}) has been invoked.",
                filePath);
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();
            ImageIO.write(preview,
                    Objects.requireNonNull(
                            StringUtils.getFilenameExtension(filePath.toString())),
                    baos);
            return baos.toByteArray();
        }
    }

    public Pair<byte[], String> getPairPreviewAndMediaTypeByStudentId(long studentId) {
        logger.info("Method getPairPreviewAndMediaTypeByStudentId(long studentId={}) has been invoked.",
                studentId);
        Avatar avatar = getAvatarForStudentId(studentId);
        return Pair.of(avatar.getPreview(), avatar.getMediaType());
    }

    public Pair<byte[], String> getPairAvatarAndMediaTypeByStudentId(long studentId) {
        logger.info("Method getPairAvatarAndMediaTypeByStudentId(long studentId={}) has been invoked.",
                studentId);
        Avatar avatar = getAvatarForStudentId(studentId);
        try {
            return Pair.of(Files.readAllBytes(Path.of(avatar.getFilePath())), avatar.getMediaType());
        } catch(IOException e) {
            logger.error("IOException e with message=\"{}\" during avatar retrieving occurred ",
                    e.getMessage()
            );
            throw new AvatarNotFoundException();
        }
    }
}
