package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "nadawcy")
@JsonIgnoreProperties({"packages"})
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
    @NotBlank
    private String companyName;

    @Column(name = "ulica", length = 72)
    @NotBlank
    @Size(min = 3, max = 72, message = "Must be between 3-72 characters")
    private String street;

    @Column(name = "kod_pocztowy")
    @NotBlank
    @Range(min = 00000, max = 99999, message = "post code must have 5 digits")
    private int postCode;

    @Column(name = "miasto", length = 39)
    @NotBlank
    @Size(min = 3, max = 39, message = "Must be between 3-39 characters")
    private String city;

    @Column(name = "telefon", length = 13)
    @NotBlank
    @Pattern(regexp = "([\\d]{9})|(\\+[\\d]{11})", message = "only 9digits number and with +48") //+48123456789 oraz 123456789
    private String number;

    @Column(name = "email", length = 30)
    @NotBlank
    @Email
    private String email;

    @OneToMany(mappedBy = "sender")
    private List<Package> packages;

    public Sender() {
    }

    public Sender(String name, String lastName, String companyName, String street, int postCode, String city, String number, String email) {
        this.name = name;
        this.lastName = lastName;
        this.companyName = companyName;
        this.street = street;
        this.postCode = postCode;
        this.city = city;
        this.number = number;
        this.email = email;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    @Override
    public String toString() {
        return "Nadawcy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", street='" + street + '\'' +
                ", postCode=" + postCode +
                ", city='" + city + '\'' +
                ", number='" + number + '\'' +
                ", email='" + email + '\'' +
                ", packages=" + packages +
                '}';
    }
}
