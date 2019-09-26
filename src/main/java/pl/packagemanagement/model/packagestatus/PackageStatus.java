package pl.packagemanagement.model.packagestatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.packagemanagement.model.pack.Package;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "status_paczki")
@JsonIgnoreProperties({"packages"})
@Data
@NoArgsConstructor
public class PackageStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status")
    private long id;

    @Column(name = "nazwa", length = 45)
    @NotBlank
    private String name;

    @Column(name = "opis", length = 45)
    @NotBlank
    private String description;

    @OneToMany(mappedBy = "packageStatus")
    private List<Package> packages = new ArrayList<>();

}
