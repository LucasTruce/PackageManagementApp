package pl.packagemanagement.model.pack;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.packagemanagement.model.user.User;
import pl.packagemanagement.model.userdetails.UserDetails;
import pl.packagemanagement.model.car.Car;
import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.model.content.Content;
import pl.packagemanagement.model.packagestatus.PackageStatus;
import pl.packagemanagement.model.recipient.Recipient;
import pl.packagemanagement.model.sender.Sender;
import pl.packagemanagement.model.warehouse.Warehouse;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "paczki")
@JsonIgnoreProperties({"usersKey"})
@Data
@NoArgsConstructor
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paczki")
    private long id;

    @Column(name = "numer_paczki", length = 13)
    private String packageNumber;

    @Column(name = "data_nadania")  //columnDefinition mozna tez wpisac np. NVARCHAR(500) NOT NULL itp..//??
    private LocalDateTime date;

    @Column(name = "wysokosc")
    @Min(1) @Max(1000)
    private int height;

    @Column(name = "dlugosc")
    @Min(1) @Max(1000)
    private int length;

    @Column(name = "szerokosc")
    @Min(1) @Max(1000)
    private int width;

    @Column(name = "uwagi", length = 45)
    @NotBlank
    private String comments;

    //1:1
    //Status_paczki
    @OneToOne
    @JoinColumn(name = "status_id")
    private PackageStatus status;

    @OneToOne
    @JoinColumn(name = "zawartosc_id")
    private Content content;

    @OneToOne
    @JoinColumn(name = "kody_id")
    private Code code;

    //N:1
    @ManyToOne
    @JoinColumn(name = "odbiorcy_id")
    private Recipient recipient;

    @ManyToOne
    @JoinColumn(name = "nadawcy_id")
    private Sender sender;

    @ManyToOne
    @JoinColumn(name = "samochody_id")
    private Car car;

    //N:M
    //Uzytkownicy
    @ManyToMany(mappedBy = "packages")
    private List<User> users = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "paczki_magazyny",
            joinColumns = {@JoinColumn(name = "paczki_id", referencedColumnName = "id_paczki")},
            inverseJoinColumns = {@JoinColumn(name = "magazyny_id", referencedColumnName = "id_magazyny")}
    )
    private List<Warehouse> warehouses;

}
