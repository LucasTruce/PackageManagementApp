package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "zawartosc")
@JsonIgnoreProperties({"pack", "products"})
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zawartosc")
    private long id;

    @Column(name = "czas_kompletacji")
    @NotBlank
    private LocalDateTime date;

    @Column(name = "opis_zawartosci", length = 255)
    private String content;

    @OneToMany(mappedBy = "content")
    private List<Product> products;

    @OneToOne(mappedBy = "content")
    private Package pack;

    public Content() {
    }

    public Content(LocalDateTime date, String content) {
        this.date = date;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Package getPack() {
        return pack;
    }

    public void setPack(Package pack) {
        this.pack = pack;
    }

    @Override
    public String toString() {
        return "Zawartosc{" +
                "id=" + id +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", products=" + products +
                ", pack=" + pack +
                '}';
    }
}
