package pl.packagemanagement.model.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.packagemanagement.model.role.Role;
import pl.packagemanagement.model.role.RoleName;
import pl.packagemanagement.model.role.RoleRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private static final String DEFAULT_ROLE = "ROLE_USER";

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User save(User user)
    {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public User update(User user){
        User tempPass = userRepository.findById(user.getId()).get();

        tempPass.setLogin(user.getLogin());
        tempPass.setPassword(user.getEmail());
        tempPass.setPassword(bcryptEncoder.encode(user.getPassword()));

        return userRepository.save(tempPass);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByLoginOrEmail(String login) {
        return userRepository.findByLoginOrEmail(login, login);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //security
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> pass = userRepository.findByLoginOrEmail(login, login);
        return new org.springframework.security.core.userdetails.User(pass.get().getLogin(), pass.get().getPassword(),
                new ArrayList<>());
    }
}
