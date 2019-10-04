package pl.packagemanagement.model.content;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.product.Product;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "zawartosc")
@JsonIgnoreProperties({"pack"})
@Data
@NoArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zawartosc")
    private long id;

    @Column(name = "czas_kompletacji")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    @Column(name = "opis_zawartosci", length = 255)
    private String content;

    @OneToMany(mappedBy = "content")
    private List<Product> products = new ArrayList<>();

    @OneToOne(mappedBy = "content")
    private Package pack;

}
