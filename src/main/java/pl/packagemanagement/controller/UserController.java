package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Password;
import pl.packagemanagement.entity.User;
import pl.packagemanagement.exception.UserNotFoundException;
import pl.packagemanagement.service.PasswordService;
import pl.packagemanagement.service.UserService;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final PasswordService passwordService;

    @Autowired
    public UserController(UserService userService, PasswordService passwordService) {
        this.userService = userService;
        this.passwordService = passwordService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id){
        return new ResponseEntity<>(
                userService.findById(id).orElseThrow( () -> new UserNotFoundException("User not found, id: " + id)), HttpStatus.FOUND);

    }

    @GetMapping("/password")
    public ResponseEntity<User> findByLogin(@RequestParam(name = "login") String login){
        User user = userService.findByLogin(login);
        if(user == null)
             throw(new UserNotFoundException("User not found, login: " + login));
        return new ResponseEntity<>(userService.findByLogin(login), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id){
        User user = userService.findById(id).orElseThrow( () -> new UserNotFoundException("User not found, id: " + id) );
        userService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser(@RequestBody User user){
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public void saveUser(@RequestBody User user){
        userService.save(user);
    }

    @PutMapping("/{id}") //NALEŻY PODAĆ ID
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        User temp = userService.findById(id).orElseThrow(() -> new UserNotFoundException("User not found, id: " + id));
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
