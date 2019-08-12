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

    @Autowired
    public PasswordServiceImpl(PasswordRepository passwordRepository, UserRepository userRepository) {
        this.passwordRepository = passwordRepository;

    }

    @Override
    public Password findPasswordForUser(User user) {
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
