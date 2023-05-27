package pro.sky.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.hogwarts.school.entity.Avatar;
import pro.sky.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


@RestController
@RequestMapping("/student/{student_id}/avatar")
@Tag(name = "avatars", description = "End points to operate students avatars")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    void uploadAvatarForStudentId(
            @PathVariable(name = "student_id") long studentId,
            @RequestParam MultipartFile avatar)  {
        avatarService.uploadAvatarForStudentId(studentId, avatar);
    }

    @GetMapping
    public ResponseEntity<?> downloadAvatarForStudentId(
            @PathVariable(name= "student_id") long studentId,
            HttpServletResponse httpServletResponse) throws IOException
    {
        Avatar avatar = avatarService.getAvatarForStudentId(studentId);
        Path path = Path.of(avatar.getFilePath());
        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = httpServletResponse.getOutputStream();
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
                ) {
            httpServletResponse.setContentType(avatar.getMediaType());
            httpServletResponse.setContentLength((int)avatar.getFileSize());
            bis.transferTo(bos);
        }
        return ResponseEntity.ok().build();

    }

    @GetMapping("/preview")
    public ResponseEntity<byte[]> downloadAvatarPreviewForStudentId(
            @PathVariable(name = "student_id") long studentId) {
        Avatar avatar = avatarService.getAvatarForStudentId(studentId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        httpHeaders.setContentLength(avatar.getPreview().length);
        return ResponseEntity.ok().headers(httpHeaders).body(avatar.getPreview());
    }

    @GetMapping("/alter-preview")
    public ResponseEntity<byte[]> downloadPreview(
            @PathVariable(name = "student_id") long studentId) {
        return prepareDownloadResponse(
                avatarService.getPairPreviewAndMediaTypeByStudentId(studentId));
    }

    @GetMapping("/alter-avatar")
    public ResponseEntity<byte[]> downloadAvatar(
            @PathVariable(name = "student_id") long studentId) {
        return prepareDownloadResponse(
                avatarService.getPairAvatarAndMediaTypeByStudentId(studentId));
    }

    private ResponseEntity<byte[]> prepareDownloadResponse(Pair<byte[], String> avatarAndMediatype) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(avatarAndMediatype.getSecond()))
                .contentLength(avatarAndMediatype.getFirst().length)
                .body(avatarAndMediatype.getFirst());
    }

}
