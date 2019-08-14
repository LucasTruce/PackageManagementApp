package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "magazyny")
@JsonIgnoreProperties({"packages"})
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_magazyny")
    private long id;

    @Column(name = "telefon", length = 13)
    @NotBlank
    @Pattern(regexp = "([\\d]{9})|(\\+[\\d]{11})", message = "only 9digits number and with +48") //+48123456789 oraz 123456789
    private String number;

    @Column(name = "ulica", length = 72)
    @NotBlank
    @Size(min = 3, max = 72, message = "Must be between 3-72 characters")
    private String street;

    @Column(name = "kod_pocztowy")
    @Range(min = 00000, max = 99999, message = "post code must have 5 digits")
    private int postCode;

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

    public Warehouse() {
    }

    public Warehouse(String number, String street, int postCode, String city, String description) {
        this.number = number;
        this.street = street;
        this.postCode = postCode;
        this.city = city;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", street='" + street + '\'' +
                ", postCode=" + postCode +
                ", city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", packages=" + packages +
                ", code=" + code +
                '}';
    }
}
