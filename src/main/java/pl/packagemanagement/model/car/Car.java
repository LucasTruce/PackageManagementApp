package pl.packagemanagement.model.car;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.packagemanagement.model.carstatus.CarStatus;
import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.model.pack.Package;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "samochody")
@JsonIgnoreProperties({"packages"})
@Data
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_samochody")
    private long id;

    @Column(name = "marka", length = 15)
    @NotBlank(message = "Marka nie może być pusta!")
    private String brand;

    @Column(name = "model", length = 18)
    @NotBlank(message = "Model nie może być pusty!")
    private String model;

    @Column(name = "typ_silnika", length = 10)
    @NotBlank(message = "Typ silnika nie może być pusty!")
    private String engineType;

    @Column(name = "moc")
    private int power;

    @Column(name = "pojemnosc")
    private double capacity;

    @Column(name = "kolor", length = 30)
    @NotBlank(message = "Kolor nie może być pusty!")
    private String color;

    @Column(name = "rodzaj", length = 45)
    @NotBlank(message = "Rodzaj nie może być pusty!")
    private String type;

    @Column(name = "numer_rejestracyjny", length = 7)
    @NotBlank(message = "Numer rejestracyjny nie możę być pusty!")
    private String licensePlate;

    @Column(name = "ladownosc")
    private float load;

    @Column(name = "uwagi", length = 45)
    @NotBlank(message = "Musisz podać komentarz!")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "status_samochodu_id")
    private CarStatus carStatus;

    @OneToMany(mappedBy = "car")
    private List<Package> packages;

    @OneToOne
    @JoinColumn(name = "kody_id")
    private Code code;
}
