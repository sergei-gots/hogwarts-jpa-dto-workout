package pro.sky.hogwarts.school.exception;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    private final String maxFileSize;

    public ApiExceptionHandler(
            @Value("${spring.servlet.multipart.max-file-size}") String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    @ExceptionHandler({StudentNotFoundException.class,
            AvatarNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AvatarProcessingException.class)
    public ResponseEntity<?> handleAvatarProcessingException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<?> handleSizeLimitExceededException() {
        return ResponseEntity.badRequest().body(
                "You tried to upload a file that was too large. Max file size = " +
                        maxFileSize);
    }
}

