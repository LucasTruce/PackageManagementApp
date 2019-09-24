package pl.packagemanagement.model.code;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.car.Car;
import pl.packagemanagement.model.product.Product;
import pl.packagemanagement.model.warehouse.Warehouse;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Entity
@Table(name = "kody")
@JsonIgnoreProperties({"pack", "car", "warehouse", "product"})
@Data
@NoArgsConstructor
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kody")
    private long id;

    @Column(name = "sciezka_do_pliku", length = 45)
    @NotBlank
    private String filePath;

    @OneToOne(mappedBy = "code")
    private Car car;

    @OneToOne(mappedBy = "code")
    private Package pack;

    @OneToOne(mappedBy = "code")
    private Warehouse warehouse;

    @OneToOne(mappedBy = "code")
    private Product product;

    public Code(String filePath, Package pack){
        this.filePath = filePath;
        this.pack = pack;
    }
}
