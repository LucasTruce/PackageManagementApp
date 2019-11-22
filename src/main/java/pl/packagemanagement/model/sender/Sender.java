package pl.packagemanagement.model.sender;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import pl.packagemanagement.model.pack.Package;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "nadawcy")
@JsonIgnoreProperties({"packages"})
@Data
@NoArgsConstructor
public class Sender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nadawcy")
    private long id;

    @Column(name = "imie", length = 13)
    @NotBlank
    @Size(min = 3, max = 13, message = "Must be between 3-13 characters")
    private String name;

    @Column(name = "nazwisko", length = 49)
    @NotBlank
    @Size(min = 2, max = 49, message = "Must be between 2-49 characters")
    private String lastName;

    @Column(name = "nazwa_firmy", length = 80)
    private String companyName;

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

    @Column(name = "telefon", length = 13)
    @NotBlank
    @Pattern(regexp = "([\\d]{9})|(\\+[\\d]{11})", message = "only 9digits number and with +48") //+48123456789 oraz 123456789
    private String phoneNumber;

    @Column(name = "email", length = 30)
    @NotBlank
    @Email
    private String email;

    @Column(name = "numer_domu", length = 5)
    @NotBlank
    private String houseNumber;

    @Column(name = "numer_mieszkania", length = 5)
    @NotBlank
    private String apartmentNumber;

    @OneToMany(mappedBy = "sender")
    private List<Package> packages = new ArrayList<>();

}
