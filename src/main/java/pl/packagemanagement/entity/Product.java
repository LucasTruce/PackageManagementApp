package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "towary")
@JsonIgnoreProperties({"content", "category"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_towary")
    private long id;

    @Column(name = "nazwa", length = 45)
    private String name;
    @Column(name = "waga")
    private float waga;
    @Column(name = "uwagi", length = 45)
    private String comments;

    @OneToOne
    @JoinColumn(name = "kody_id")
    private Code code;

    @ManyToOne
    @JoinColumn(name = "kategoria_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "zawartosc_id")
    private Content content;

    public Product() {
    }

    public Product(String name, float waga, String comments, Category category, Content content) {
        this.name = name;
        this.waga = waga;
        this.comments = comments;
        this.category = category;
        this.content = content;
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

    public float getWaga() {
        return waga;
    }

    public void setWaga(float waga) {
        this.waga = waga;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "Towary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", waga=" + waga +
                ", comments='" + comments + '\'' +
                ", code=" + code +
                ", category=" + category +
                ", content=" + content +
                '}';
    }
}
