package pl.packagemanagement.model.userdetails;

import pl.packagemanagement.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDetailsService {
    List<UserDetails> findAll();
    Optional<UserDetails> findById(Long id);
    Optional<UserDetails> findByUserLoginOrUserEmail(String login);
    UserDetails save(UserDetails userDetails, User user);
    void delete(UserDetails userDetails);
    UserDetails update(UserDetails userDetails);
}
