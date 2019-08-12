package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "kody")
@JsonIgnoreProperties({"pack", "car", "warehouse", "product"})
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kody")
    private long id;

    @Column(name = "sciezka_do_pliku", length = 45)
    private String filePath;

    @OneToOne(mappedBy = "code")
    private Car car;

    @OneToOne(mappedBy = "code")
    private Package pack;

    @OneToOne(mappedBy = "code")
    private Warehouse warehouse;

    @OneToOne(mappedBy = "code")
    private Product product;

    public Code() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Code{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", car=" + car +
                ", pack=" + pack +
                ", warehouse=" + warehouse +
                ", product=" + product +
                '}';
    }
}
