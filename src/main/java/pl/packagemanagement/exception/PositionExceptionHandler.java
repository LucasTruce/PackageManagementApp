package pl.packagemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.packagemanagement.response.PositionErrorResponse;

import java.time.LocalDateTime;

@ControllerAdvice
public class PositionExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<PositionErrorResponse> PositionNotFound(PositionNotFoundException ex){
        PositionErrorResponse pass = PositionErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(pass, HttpStatus.NOT_FOUND);
    }
}
