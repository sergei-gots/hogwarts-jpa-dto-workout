package pro.sky.hogwarts.school.service;

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

    public AvatarService(AvatarRepository avatarRepository,
                         StudentService studentService,
                         @Value("${avatars.dir.path}") String avatarsDirPath) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
        this.avatarsDirPath = avatarsDirPath;
    }

    public void uploadAvatarForStudentId(long studentId, MultipartFile avatarFile) {
        Student student = studentService
                .findById(studentId)
                .orElseThrow(StudentNotFoundException::new);
        Path filePath = Path.of(avatarsDirPath,
                studentId + "." +
                        StringUtils.getFilenameExtension(avatarFile.getOriginalFilename()));
        try {
       /*     Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            try (InputStream is = avatarFile.getInputStream();
                 OutputStream os = Files.newOutputStream(filePath);
                 BufferedInputStream bis = new BufferedInputStream(is, 1024);
                 BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
            ) {
                bis.transferTo(bos);
            }*/

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
            throw new AvatarProcessingException();
        }
    }

    public Avatar getAvatarForStudentId(long studentId) {
        return avatarRepository.findByStudentId(studentId).orElseThrow(AvatarNotFoundException::new);
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {

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
        Avatar avatar = getAvatarForStudentId(studentId);
        return Pair.of(avatar.getPreview(), avatar.getMediaType());
    }

    public Pair<byte[], String> getPairAvatarAndMediaTypeByStudentId(long studentId) {
        Avatar avatar = getAvatarForStudentId(studentId);
        try {
            return Pair.of(Files.readAllBytes(Path.of(avatar.getFilePath())), avatar.getMediaType());
        } catch(IOException e) {
            throw new AvatarNotFoundException();
        }
    }
}
