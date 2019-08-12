package pl.packagemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.entity.Password;
import pl.packagemanagement.entity.User;

import java.util.List;

@Repository
public interface PasswordRepository extends JpaRepository<Password,Long> {
    Password findByUser(User user);
}
