package pl.packagemanagement.model.userdetails;

import java.util.List;
import java.util.Optional;

public interface UserDetailsService {
    List<UserDetails> findAll();
    Optional<UserDetails> findById(Long id);
    Optional<UserDetails> findByUserLoginOrUserEmail(String login);
    UserDetails save(UserDetails userDetails);
    void delete(UserDetails userDetails);
    UserDetails update(UserDetails userDetails);
}
