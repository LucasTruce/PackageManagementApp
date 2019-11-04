package pl.packagemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.util.TimeZone;

@SpringBootApplication
public class PackageManagementApplication {
    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC")); //ustawienie strefy czasowej dla daty !!
    }

    public static void main(String[] args) {
        SpringApplication.run(PackageManagementApplication.class, args);
    }

}
