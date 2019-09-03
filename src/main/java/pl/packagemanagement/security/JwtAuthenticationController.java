package pl.packagemanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.role.Role;
import pl.packagemanagement.model.role.RoleName;
import pl.packagemanagement.model.role.RoleService;
import pl.packagemanagement.model.user.User;
import pl.packagemanagement.model.user.UserServiceImpl;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping("/authenticate")    //return TOKEN
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception{
        authenticate(authenticationRequest.getLogin(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getLogin());

        final String token = jwtTokenProvider.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping("/register")    //ADD NEW USER WITH DEFAULT ROLE: ROLE_USER
    @PostMapping
    public ResponseEntity<?> savePassword(@RequestBody @Valid User user) {
        if(userService.findByLoginOrEmail(user.getLogin(), user.getEmail()).isPresent())
            throw new EntityNotFoundException("Login/email zajety!");

        return ResponseEntity.ok(userService.save(user));
    }

    private void authenticate(String login, String password) throws Exception {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        }catch (DisabledException e){
            throw new Exception("User_DISABLED", e);
        }catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
