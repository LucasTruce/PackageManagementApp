package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "status_samochodu")
@JsonIgnoreProperties({"car"})
public class CarStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private long id;
    @Column(name = "nazwa", length = 30)
    private String name;
    @Column(name = "opis", length = 45)
    private String description;

    @OneToOne(mappedBy = "carStatus")
    private Car car;

    public CarStatus() {
    }

    public CarStatus(String name, String description, Car car) {
        this.name = name;
        this.description = description;
        this.car = car;
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

   public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "CarStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", car=" + car +
                '}';
    }
}
