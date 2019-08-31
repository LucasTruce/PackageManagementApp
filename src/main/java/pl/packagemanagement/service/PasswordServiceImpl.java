package pl.packagemanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.packagemanagement.entity.Password;
import pl.packagemanagement.entity.User;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.repository.PasswordRepository;
import pl.packagemanagement.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PasswordServiceImpl implements PasswordService, UserDetailsService {

    private final PasswordRepository passwordRepository;
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    public PasswordServiceImpl(PasswordRepository passwordRepository,UserRepository userRepository) {
        this.passwordRepository = passwordRepository;
        this.userRepository = userRepository;
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
    public Password save(Password password)
    {
        password.setPassword(bcryptEncoder.encode(password.getPassword()));
        return passwordRepository.save(password);
    }

    @Override
    public Password update(Password password){
        Password tempPass = passwordRepository.findById(password.getId()).get();

        tempPass.setLogin(password.getLogin());
        tempPass.setPassword(password.getEmail());
        tempPass.setPassword(bcryptEncoder.encode(password.getPassword()));

        return passwordRepository.save(tempPass);
    }

    @Override
    public Optional<Password> findById(Long id) {
        return passwordRepository.findById(id);
    }

    @Override
    public Optional<Password> findByEmail(String email) {
        return passwordRepository.findByEmail(email);
    }

    @Override
    public Optional<Password> findByLogin(String login) {
        return passwordRepository.findByLogin(login);
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Password> pass = passwordRepository.findByLogin(login);
        if(pass.isEmpty())
            pass = passwordRepository.findByEmail(login);
        return new org.springframework.security.core.userdetails.User(pass.get().getLogin(), pass.get().getPassword(),
                new ArrayList<>());
    }
}
