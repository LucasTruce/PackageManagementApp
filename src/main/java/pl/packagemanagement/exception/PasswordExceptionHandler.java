package pl.packagemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.packagemanagement.response.PasswordErrorResponse;

import java.time.LocalDateTime;

@ControllerAdvice
public class PasswordExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<PasswordErrorResponse> PasswordNotFound(PasswordNotFoundException ex){
        PasswordErrorResponse response = PasswordErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
