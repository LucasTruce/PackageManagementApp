package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "stanowiska")
@JsonIgnoreProperties({"users"})
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stanowiska")
    private long id;

    @Column(name = "nazwa", length = 45)
    @NotBlank(message = "Nazwa nie może być pusta!")
    private String name;

    @Column(name = "opis", length = 45)
    @NotBlank(message = "Opis nie może być pusty!")
    private String description;

    //kto inny jest wlascicielem (PODAJEMY ZMIENNA NIE NAZWE KOLUMNY)
    //Uzytkownicy
    @OneToMany(mappedBy = "position")
    private List<User> users;

    public Position() {
    }

    public Position(@NotNull String name, String description, List<User> users) {
        this.name = name;
        this.description = description;
        this.users = users;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", users=" + users +
                '}';
    }
}
