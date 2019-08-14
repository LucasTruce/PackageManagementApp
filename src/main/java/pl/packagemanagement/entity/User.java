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
    @NotBlank
    @Size(min = 3, max = 13, message = "Must be between 3-13 characters")
    private String name;

    @Column(name = "nazwisko", length = 49)
    @NotBlank
    @Size(min = 2, max = 49, message = "Must be between 2-49 characters")
    private String lastName;

    @Column(name = "telefon", length = 13)
    @NotBlank
    @Pattern(regexp = "([\\d]{9})|(\\+[\\d]{11})", message = "only 9digits number and with +48") //+48123456789 oraz 123456789
    private String number;

    @Column(name = "ulica", length = 72)
    @NotBlank
    @Size(min = 3, max = 72, message = "Must be between 3-72 characters")
    private String street;

    @Column(name = "miasto", length = 39)
    @NotBlank
    @Size(min = 3, max = 39, message = "Must be between 3-39 characters")
    private String city;

    @Column(name = "kod_pocztowy")
    @Range(min = 00000, max = 99999, message = "post code must have 5 digits")
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

    public User(String name, String lastName, String number, String street, String city, int postCode, Position position){
        this.name = name;
        this.lastName = lastName;
        this.number = number;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
        this.position = position;
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
