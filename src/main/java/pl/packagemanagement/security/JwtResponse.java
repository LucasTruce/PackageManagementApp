package pl.packagemanagement.security;

import pl.packagemanagement.model.role.Role;

import java.io.Serializable;
import java.util.Set;

public class JwtResponse implements Serializable {
    private final String jwttoken;
    private final Set<Role> roles;

    public JwtResponse(String jwttoken, Set<Role> roles){
        this.jwttoken = jwttoken;
        this.roles = roles;
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
