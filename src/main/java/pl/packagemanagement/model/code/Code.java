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
    @Nullable
    private Car car;

    @OneToOne(mappedBy = "code")
    @Nullable
    private Package pack;

    @OneToOne(mappedBy = "code")
    @Nullable
    private Warehouse warehouse;

    @OneToOne(mappedBy = "code")
    @Nullable
    private Product product;

    public String toString() {
        String string = "";
        if(car != null)
            string = " carId: " + car.getId();
        else if(pack != null)
            string = " packId: " + pack.getId();
        else if(warehouse != null)
            string = " warehouseId: " + warehouse.getId();
        else if(product != null)
            string = " productId: " + product.getId();
        return "id: " + id + " filePath: " + filePath + string;
    }
}
