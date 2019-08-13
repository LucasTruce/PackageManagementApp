package pl.packagemanagement.service;

import pl.packagemanagement.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByLogin(String login);
    User save(User user);
    void delete(User user);
    void deleteById(Long id);
}
