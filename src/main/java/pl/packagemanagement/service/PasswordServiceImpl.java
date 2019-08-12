package pl.packagemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.packagemanagement.entity.Password;
import pl.packagemanagement.entity.User;
import pl.packagemanagement.exception.UserNotFoundException;
import pl.packagemanagement.repository.PasswordRepository;
import pl.packagemanagement.repository.UserRepository;

import java.util.List;

@Service
public class PasswordServiceImpl implements PasswordService {

    PasswordRepository passwordRepository;
    UserRepository userRepository;

    public PasswordServiceImpl(PasswordRepository passwordRepository, UserRepository userRepository) {
        this.passwordRepository = passwordRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Password findPasswordForUser(Long userId) {
        User user = userRepository.findById(userId).orElse(new User());
        return passwordRepository.findByUser(user);
    }

    @Override
    public void delete(Password password) {
        passwordRepository.delete(password);
    }

    @Override
    public Password save(Password password) {
        return passwordRepository.save(password);
    }
}
