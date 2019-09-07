package pl.packagemanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // @Getter, @Setter, @EqualsAndHashCode, @RequiredArgsConstructor oraz @ToString
@Builder
@AllArgsConstructor @NoArgsConstructor
public class EntityErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
