package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "samochody")
@JsonIgnoreProperties({"packages"})
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_samochody")
    private long id;

    @Column(name = "marka", length = 15)
    @NotBlank
    private String brand;

    @Column(name = "model", length = 18)
    @NotBlank
    private String model;

    @Column(name = "typ_silnika", length = 10)
    @NotBlank
    private String engineType;

    @Column(name = "moc")
    private int power;

    @Column(name = "pojemnosc")
    private double capacity;

    @Column(name = "kolor", length = 30)
    @NotBlank
    private String color;

    @Column(name = "rodzaj", length = 45)
    @NotBlank
    private String type;

    @Column(name = "numer_rejestracyjny", length = 7)
    @NotBlank
    private String licensePlate;

    @Column(name = "ladownosc")
    private float load;

    @Column(name = "uwagi", length = 45)
    @NotBlank
    private String comments;

    @ManyToOne
    @JoinColumn(name = "status_samochodu_id")
    private CarStatus carStatus;

    @OneToMany(mappedBy = "car")
    private List<Package> packages;

    @OneToOne
    @JoinColumn(name = "kody_id")
    private Code code;

    public Car() {
    }

    public Car(String brand, String model, String engineType, int power, double capacity, String color, String type, String licensePlate, float load, String comments, CarStatus carStatus, List<Package> packages, Code code) {
        this.brand = brand;
        this.model = model;
        this.engineType = engineType;
        this.power = power;
        this.capacity = capacity;
        this.color = color;
        this.type = type;
        this.licensePlate = licensePlate;
        this.load = load;
        this.comments = comments;
        this.carStatus = carStatus;
        this.packages = packages;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public float getLoad() {
        return load;
    }

    public void setLoad(float load) {
        this.load = load;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
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
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", engineType='" + engineType + '\'' +
                ", power=" + power +
                ", capacity=" + capacity +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", load=" + load +
                ", comments='" + comments + '\'' +
                ", carStatus=" + carStatus +
                ", packages=" + packages +
                ", code=" + code +
                '}';
    }
}
