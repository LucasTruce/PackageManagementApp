package pl.packagemanagement.service;

import pl.packagemanagement.entity.Position;
import pl.packagemanagement.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByLogin(String login);
    Optional<User> findByEmail(String email);
    User save(User user);
    void delete(User user);
    void deleteById(Long id);
    User update(User user);
}
