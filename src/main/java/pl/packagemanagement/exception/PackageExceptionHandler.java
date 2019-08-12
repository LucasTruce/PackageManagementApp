package pl.packagemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.packagemanagement.response.PackageErrorResponse;

import java.time.LocalDateTime;

@ControllerAdvice
public class PackageExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<PackageErrorResponse> PackageNotFound(PackageNotFoundException ex){
        PackageErrorResponse response = PackageErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
