package pl.packagemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.packagemanagement.response.UserErrorResponse;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> UserNotFound(UserNotFoundException ex) {
        UserErrorResponse postErrorRes = UserErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(postErrorRes, HttpStatus.NOT_FOUND);
    }
}
