package pl.packagemanagement.model.user;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.packagemanagement.model.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void delete(User user);
    User save(User user);
    User update(User user);
    Optional<User> findById(Long id);
    Page<User> findAll(int pageNumber, int pageSize, String orderBy, String direction);
    Optional<User> findByLoginOrEmail(String login, String email);

}
