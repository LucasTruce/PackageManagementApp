package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "status_samochodu")
@JsonIgnoreProperties({"cars"})
public class CarStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private long id;

    @Column(name = "nazwa", length = 30)
    @NotBlank
    @Size(min = 3, message = "Must be between 3-30 characters")
    private String name;

    @Column(name = "opis", length = 45)
    @NotBlank
    private String description;

    @OneToMany(mappedBy = "carStatus")
    private List<Car> cars;

    public CarStatus() {
    }

    public CarStatus(String name, String description, List<Car> cars) {
        this.name = name;
        this.description = description;
        this.cars = cars;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "CarStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cars=" + cars +
                '}';
    }
}
