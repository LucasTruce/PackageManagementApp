package pl.packagemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "paczki")
@JsonIgnoreProperties({"usersKey"})
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paczki")
    private long id;

    @Column(name = "numer_paczki", length = 14)
    private String packageNumber;
    @Column(name = "data_nadania") //columnDefinition mozna tez wpisac np. NVARCHAR(500) NOT NULL itp..//??
    private LocalDateTime date;
    @Column(name = "wysokosc")
    private int height;
    @Column(name = "dlugosc")
    private int length;
    @Column(name = "szerokosc")
    private int width;
    @Column(name = "uwagi", length = 45)
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
    private List<User> usersKey;

    @ManyToMany
    @JoinTable(name = "paczki_magazyny",
            joinColumns = {@JoinColumn(name = "paczki_id", referencedColumnName = "id_paczki")},
            inverseJoinColumns = {@JoinColumn(name = "magazyny_id", referencedColumnName = "id_magazyny")}
    )
    private List<Warehouse> warehouses;


    public Package() {
    }

    public Package(String packageNumber, LocalDateTime date, int height, int length, int width, String comments, PackageStatus status, Content content, Recipient recipient, Sender sender, Car car) {
        this.packageNumber = packageNumber;
        this.date = date;
        this.height = height;
        this.length = length;
        this.width = width;
        this.comments = comments;
        this.status = status;
        this.content = content;
        this.recipient = recipient;
        this.sender = sender;
        this.car = car;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPackageNumber() {
        return packageNumber;
    }

    public void setPackageNumber(String packageNumber) {
        this.packageNumber = packageNumber;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<User> getUsersKey() {
        return usersKey;
    }

    public void setUsersKey(List<User> usersKey) {
        this.usersKey = usersKey;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", packageNumber='" + packageNumber + '\'' +
                ", date=" + date +
                ", height=" + height +
                ", length=" + length +
                ", width=" + width +
                ", comments='" + comments + '\'' +
                ", status=" + status +
                ", content=" + content +
                ", code=" + code +
                ", recipient=" + recipient +
                ", sender=" + sender +
                ", car=" + car +
                ", usersKey=" + usersKey +
                ", warehouses=" + warehouses +
                '}';
    }
}
