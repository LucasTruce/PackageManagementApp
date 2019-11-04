package pl.packagemanagement.model.history;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.packagemanagement.model.pack.Package;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "historia")
@JsonIgnoreProperties({"pack"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historii")
    private Long id;

    @Column(name = "opis")
    @NotNull
    private String description;

    @Column(name = "data")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @Column(name = "lokalizacja")
    @NotNull
    private String localization;

    @ManyToOne
    @JoinColumn(name = "paczka_id")
    private Package pack;
}
