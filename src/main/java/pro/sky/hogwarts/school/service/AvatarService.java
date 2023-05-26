package pro.sky.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.hogwarts.school.entity.Avatar;
import pro.sky.hogwarts.school.entity.Student;
import pro.sky.hogwarts.school.repository.AvatarRepository;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class AvatarService {
    @Value("${avatars.dir.path}")
    private String avatarsDirPath;
    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    public AvatarService(AvatarRepository avatarRepository,
                         StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    public ResponseEntity<String> uploadAvatarForStudentId(long studentId, MultipartFile avatarFile) throws IOException {
        Optional <Student> optionalStudent = studentService.findById(studentId);
        if(optionalStudent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Path filePath = Path.of(avatarsDirPath,
                studentId + "." +
                       getExtension(Objects.requireNonNull(avatarFile.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (InputStream is = avatarFile.getInputStream();
            OutputStream os = Files.newOutputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
             ) {
            bis.transferTo(bos);
        }
        Avatar avatar = avatarRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(optionalStudent.get());
        avatar.setFilePath(filePath.toString());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setPreview(generateImagePreview(filePath));
        avatarRepository.save(avatar);
        return ResponseEntity.ok().build();
    }

    public Optional<Avatar> getAvatarForStudentId(long studentId) {
        return avatarRepository.findByStudentId(studentId);
    }
    private byte[] generateImagePreview(Path filePath) throws IOException {
        try(InputStream is = Files.newInputStream(filePath);
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
                    getExtension(filePath.getFileName().toString()),
                    baos);
            return baos.toByteArray();
        }
    }

    private String getExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }


}
