package pl.packagemanagement.model.content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.product.Product;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "zawartosc")
@JsonIgnoreProperties({"pack", "products"})
@Data
@NoArgsConstructor
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zawartosc")
    private long id;

    @Column(name = "czas_kompletacji")
    @NotBlank
    private LocalDateTime date;

    @Column(name = "opis_zawartosci", length = 255)
    private String content;

    @OneToMany(mappedBy = "content")
    private List<Product> products;

    @OneToOne(mappedBy = "content")
    private Package pack;

}
