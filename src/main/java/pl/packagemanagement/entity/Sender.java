package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
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
    private String name;
    @Column(name = "nazwisko", length = 49)
    private String lastName;
    @Column(name = "nazwa_firmy", length = 80)
    private String companyName;
    @Column(name = "ulica", length = 72)
    private String street;
    @Column(name = "kod_pocztowy")
    private int postCode;
    @Column(name = "miasto", length = 30)
    private String city;
    @Column(name = "telefon", length = 13)
    private String number;
    @Column(name = "email", length = 30)
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
