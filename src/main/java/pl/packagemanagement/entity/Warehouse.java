package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
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
    private String number;
    @Column(name = "ulica", length = 72)
    private String street;
    @Column(name = "kod_pocztowy")
    private int postCode;
    @Column(name = "miasto", length = 30)
    private String city;
    @Column(name = "opis_magazynu", length = 45)
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
