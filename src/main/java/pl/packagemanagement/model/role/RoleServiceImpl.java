package pl.packagemanagement.model.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.packagemanagement.model.userdetails.UserDetails;
import pl.packagemanagement.model.userdetails.UserDetailsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
