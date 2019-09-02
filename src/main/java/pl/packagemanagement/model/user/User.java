package pl.packagemanagement.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.role.Role;
import pl.packagemanagement.model.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "uzytkownicy")
@JsonIgnoreProperties({"user", "packages"})
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uzytkownicy")
    private long id;

    @Column(name = "email", length = 30, unique = true)
    @NotBlank(message = "Email nie może byc pusty")
    @Email(message = "Podaj poprawny adres email")
    private String email;

    @Column(name = "login", length = 16, unique = true)
    @NotBlank(message = "Login nie może być pusty")
    @Size(min = 3, max = 16, message = "Login musi zawierać od 3 do 16 znaków")
    private String login;

    @Column(name = "haslo")
    @NotBlank(message = "Podaj poprawne hasło!")
    private String password;

    //Szczegoly uzytkownika
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private UserDetails userDetails;

    //Paczki -> paczki_uzytkownikow
    @ManyToMany
    @JoinTable(name = "paczki_uzytkownikow",
            joinColumns = {@JoinColumn(name = "uzytkownicy_id", referencedColumnName = "id_uzytkownicy")},
            inverseJoinColumns = {@JoinColumn(name = "paczki_id", referencedColumnName = "id_paczki")}
    )
    private List<Package> packages;

    @ManyToMany
    @JoinTable(name = "role_uzytkownikow",
                    joinColumns = {@JoinColumn(name = "uzytkownicy_id", referencedColumnName = "id_uzytkownicy")},
                    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id_role")}
    )
    private List<Role> roles;
}
