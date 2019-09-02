package pl.packagemanagement.model.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.packagemanagement.model.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void delete(User user);
    User save(User user);
    User update(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    Optional<User> findByLoginOrEmail(String login, String email);

}
