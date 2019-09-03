package pl.packagemanagement.model.user;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.role.Role;
import pl.packagemanagement.model.role.RoleName;
import pl.packagemanagement.model.role.RoleRepository;


import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;


    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User save(User user)
    {
        if(!userRepository.findByLoginOrEmail(user.getLogin(), user.getEmail()).isEmpty())
            throw new EntityNotFoundException("Login/email zajety!");

        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        Role tempRole = roleRepository.findByName(RoleName.ROLE_USER);
        user.getRoles().add(tempRole);

        Set<Role> roles = user.getRoles();
        for (Role role: roles) {
            role.getUsers().add(user);
        }

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
    public Optional<User> findByLoginOrEmail(String login, String email) {
        return userRepository.findByLoginOrEmail(login, email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //security
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLoginOrEmail(login, login);
        return new org.springframework.security.core.userdetails.User(user.get().getLogin(), user.get().getPassword(),
                convertAuthorities(user.get().getRoles()));
    }

    private Set<GrantedAuthority> convertAuthorities(Set<Role> roles){
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName().toString()));
        }
        return authorities;
    }
}
