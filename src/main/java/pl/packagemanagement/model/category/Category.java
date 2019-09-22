package pl.packagemanagement.model.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.packagemanagement.model.product.Product;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "kategorie")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"products"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kategorii")
    private long id;

    @Column(name = "nazwa", length = 45)
    @NotBlank
    @Size(min = 2, message = "Must have between 2-45 characters")
    private String name;

    @Column(name = "opis", length = 45)
    @NotBlank
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
