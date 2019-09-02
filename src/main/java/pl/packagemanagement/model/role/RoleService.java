package pl.packagemanagement.model.role;

import pl.packagemanagement.model.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role findByName(String name);
}
