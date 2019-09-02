package pl.packagemanagement.model.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.role.Role;
import pl.packagemanagement.model.role.RoleName;
import pl.packagemanagement.model.role.RoleService;
import pl.packagemanagement.model.userdetails.UserDetails;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.userdetails.UserDetailsService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/")    // users/?login=
    public ResponseEntity<User> findUser(@RequestParam(name = "login") String login){
        User user = userService.findByLoginOrEmail(login).orElseThrow(
                () -> new EntityNotFoundException("User not found, login: " + login)
        );

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping     //users
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user){
        User tempPass = userService.findById(user.getId()).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @DeleteMapping    //users
    public ResponseEntity<User> deletePassword(@RequestBody User user){
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
