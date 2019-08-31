package pl.packagemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.entity.Password;
import pl.packagemanagement.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordRepository extends JpaRepository<Password,Long> {
    Password findByUser(User user);
    Optional<Password> findByLogin(String login);
    Optional<Password> findByEmail(String email);
}
