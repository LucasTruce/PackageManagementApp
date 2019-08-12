package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "hasla")
@JsonIgnoreProperties({"user"})
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hasla")
    private long id;
    @Column(name = "email", length = 30)
    @NotNull
    private String email;
    @Column(name = "login", length = 16)
    @NotNull
    private String login;
    @Column(name = "haslo", length = 16)
    @NotNull
    private String password;

    //nie wiem czy to uzytkownicy nie powinni przetrzymywać hasel!
    //Uzytkownicy
    @OneToOne(mappedBy = "password")
    private User user;

    public Password() {
    }

    public Password(String email, String login, String password, User user) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Hasla{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", users=" + user +
                '}';
    }
}
