package pl.packagemanagement.model.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<User>> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                              @RequestParam(defaultValue = "10") int pageSize,
                                              @RequestParam(defaultValue = "id") String orderBy,
                                              @RequestParam(defaultValue = "ASC") String direction){

        return new ResponseEntity<>(userService.findAll(pageNumber, pageSize, orderBy, direction), HttpStatus.OK);
    }

    @GetMapping("/")    // users/?login=
    public ResponseEntity<User> findUser(@RequestParam(name = "login") String login){
        User user = userService.findByLoginOrEmail(login, login).orElseThrow(
                () -> new EntityNotFoundException("User not found, login: " + login)
        );

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable Long userId) {
        User user = userService.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping     //users
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user){
        User tempPass = userService.findById(user.getId()).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

    @DeleteMapping //users?id=
    public ResponseEntity<User> deleteUser(@RequestParam(name = "id") Long id){
        userService.delete(userService.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found")));
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
