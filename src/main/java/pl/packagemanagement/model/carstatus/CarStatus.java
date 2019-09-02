package pl.packagemanagement.model.carstatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.packagemanagement.model.car.Car;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "status_samochodu")
@JsonIgnoreProperties({"cars"})
@Data
@NoArgsConstructor
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
}
