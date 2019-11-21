package pl.packagemanagement.model.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.packagemanagement.model.category.Category;
import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.model.content.Content;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "towary")
@JsonIgnoreProperties({"content"})
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_towary")
    private long id;

    @Column(name = "nazwa", length = 45)
    @NotBlank
    private String name;

    @Column(name = "waga")
    private float weight;

    @Column(name = "uwagi", length = 45)
    @NotBlank
    private String comments;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "kody_id")
    private Code code;

    @ManyToOne
    @JoinColumn(name = "kategoria_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "zawartosc_id")
    private Content content;
}
