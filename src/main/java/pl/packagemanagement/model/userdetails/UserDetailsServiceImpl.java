package pl.packagemanagement.model.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.packagemanagement.model.role.Role;
import pl.packagemanagement.model.role.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;
    private final RoleRepository roleRepository;



    @Autowired
    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository, RoleRepository roleRepository) {
        this.userDetailsRepository = userDetailsRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserDetails> findAll() {
        return userDetailsRepository.findAll();
    }

    @Override
    public Optional<UserDetails> findById(Long id) {
        return userDetailsRepository.findById(id);
    }

    @Override
    public Optional<UserDetails> findByUserLoginOrUserEmail(String login){
        return userDetailsRepository.findByUserLoginOrUserEmail(login, login);
    }

    //zrobic funkcje na update uzytkownika z pozycjami
    @Override
    public UserDetails save(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }

    @Override
    public void delete(UserDetails userDetails) {
        userDetailsRepository.delete(userDetails);
    }

    @Override
    public UserDetails update(UserDetails userDetails){
        return userDetailsRepository.save(userDetails);
    }


}
