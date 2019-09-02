package pl.packagemanagement.model.packagestatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.packagemanagement.model.pack.Package;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "status_paczki")
@JsonIgnoreProperties({"pack"})
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

    @OneToOne(mappedBy = "status")
    private Package pack;

    public PackageStatus() {
    }

    public PackageStatus(String name, String description, Package pack) {
        this.name = name;
        this.description = description;
        this.pack = pack;
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

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }

    @Override
    public String toString() {
        return "StatusPaczki{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pack=" + pack +
                '}';
    }
}
