package pl.packagemanagement.service;

import pl.packagemanagement.entity.Password;
import pl.packagemanagement.entity.User;

import java.util.List;
import java.util.Optional;

public interface PasswordService {
    Password findPasswordForUser(User user);
    void delete(Password password);
    Password save(Password password);
    Password update(Password password);
    Optional<Password> findById(Long id);

}
