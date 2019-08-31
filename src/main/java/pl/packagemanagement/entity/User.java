package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "uzytkownicy")
@JsonIgnoreProperties({"packages"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uzytkownicy")
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

    //Paczki -> paczki_uzytkownikow
    @ManyToMany
    @JoinTable(name = "paczki_uzytkownikow",
                joinColumns = {@JoinColumn(name = "uzytkownicy_id", referencedColumnName = "id_uzytkownicy")},
                inverseJoinColumns = {@JoinColumn(name = "paczki_id", referencedColumnName = "id_paczki")}
    )
    private List<Package> packages;


    //kto inny jest wlascicielem relacji
    //Hasla
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "hasla_id")
    private Password password;

    //wlasne relacje
    //Stanowiska
    @ManyToOne(optional = true)
    @JoinColumn(name = "stanowiska_id", nullable = true)
    private Position position;

    public User(){ }

    public User(String name, String lastName, String number, String street, String city, int postCode, Position position, Password password){
        this.name = name;
        this.lastName = lastName;
        this.number = number;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
        this.position = position;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postalCode) {
        this.postCode = postalCode;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", number='" + number + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postCode=" + postCode +
                ", packages=" + packages +
                ", password=" + password +
                ", position=" + position +
                '}';
    }
}
