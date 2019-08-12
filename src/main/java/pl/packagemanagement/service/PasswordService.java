package pl.packagemanagement.service;

import pl.packagemanagement.entity.Password;

import java.util.List;
import java.util.Optional;

public interface PasswordService {
    Password findPasswordForUser(Long userId);
    void delete(Password password);
    Password save(Password password);

}
