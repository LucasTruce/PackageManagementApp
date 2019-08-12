package pl.packagemanagement.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
