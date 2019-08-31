package pl.packagemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.packagemanagement.entity.Position;
import pl.packagemanagement.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByPosition(Position position);
    Optional<User> findByPasswordLogin(String login);
    Optional<User> findByPasswordEmail(String email);

}
