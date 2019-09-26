package pl.packagemanagement.model.warehouse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.model.pack.Package;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "magazyny")
@JsonIgnoreProperties({"packages"})
@Data
@NoArgsConstructor
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_magazyny")
    private long id;

    @Column(name = "telefon", length = 13)
    @NotBlank
    @Pattern(regexp = "([\\d]{9})|(\\+[\\d]{11})", message = "only 9digits number and with +48") //+48123456789 oraz 123456789
    private String phoneNumber;

    @Column(name = "ulica", length = 72)
    @NotBlank
    @Size(min = 3, max = 72, message = "Must be between 3-72 characters")
    private String street;

    @Column(name = "kod_pocztowy")
    @Size(min = 6, message = "Podaj poprawny format kodu! (xx-zz)")
    @Pattern(regexp = "([\\d]{2})-([\\d]{3})", message = "Podaj poprawny format kodu! (xx-zz)")
    private String postCode;

    @Column(name = "miasto", length = 39)
    @NotBlank
    @Size(min = 3, max = 39, message = "Must be between 3-39 characters")
    private String city;

    @Column(name = "opis_magazynu", length = 45)
    @NotBlank
    private String description;

    @ManyToMany(mappedBy = "warehouses")
    private List<Package> packages;

    @OneToOne
    @JoinColumn(name = "kody_id")
    private Code code;


}
