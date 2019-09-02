package pl.packagemanagement.model.userdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import pl.packagemanagement.model.role.Role;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "uzytkownicy_informacje")
@JsonIgnoreProperties({"user"})
@Data
@NoArgsConstructor
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uzytkownicy_info")
    private long id;

    @Column(name = "imie", length = 13)
    @NotBlank(message = "Imię nie może być puste!")
    @Size(min = 3, max = 13, message = "Imię musi zawierać od 3 do 13 liter!")
    private String name;

    @Column(name = "nazwisko", length = 49)
    @NotBlank(message = "Nazwisko nie może być puste!")
    @Size(min = 2, max = 49, message = "Nazwisko musi zawierać od 2 do 49 liter!")
    private String lastName;

    @Column(name = "telefon", length = 13)
    @NotBlank(message = "Numer telefonu nie może być pusty!")
    @Pattern(regexp = "([\\d]{9})|(\\+[\\d]{11})", message = "Podaj numer 9 cyfrowy lub z formatem +48") //+48123456789 oraz 123456789
    private String number;

    @Column(name = "ulica", length = 72)
    @NotBlank(message = "Ulica nie może być pusta!")
    @Size(min = 3, max = 72, message = "Ulica musi zawierać od 3 do 72 liter!")
    private String street;

    @Column(name = "miasto", length = 39)
    @NotBlank(message = "Miasto nie może być puste!")
    @Size(min = 3, max = 39, message = "Miasto musi zawierac od 3 do 39 liter!")
    private String city;

    @Column(name = "kod_pocztowy")
    @Range(min = 0, max = 99999, message = "Kod pocztowy musi zawierac 5 cyfr!")
    private int postCode;


    //kto inny jest wlascicielem relacji
    //Uzytkownik
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "uzytkownik_id")
    private User user;


}
