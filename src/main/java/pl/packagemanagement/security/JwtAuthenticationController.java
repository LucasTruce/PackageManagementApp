package pl.packagemanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Password;
import pl.packagemanagement.service.PasswordService;
import pl.packagemanagement.service.PasswordServiceImpl;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordServiceImpl passwordService;

    @RequestMapping("/authenticate")
    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception{
        authenticate(authenticationRequest.getLogin(), authenticationRequest.getPassword());

        final UserDetails userDetails = passwordService.loadUserByUsername(authenticationRequest.getLogin());

        final String token = jwtTokenProvider.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping("/register")
    @PostMapping
    public ResponseEntity<?> savePassword(@RequestBody @Valid Password password) throws Exception {
        return ResponseEntity.ok(passwordService.save(password));

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
