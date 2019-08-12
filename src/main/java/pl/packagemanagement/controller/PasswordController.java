package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Password;
import pl.packagemanagement.entity.User;
import pl.packagemanagement.exception.UserNotFoundException;
import pl.packagemanagement.repository.UserRepository;
import pl.packagemanagement.service.PasswordService;
import pl.packagemanagement.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/password")
public class PasswordController {
    private final PasswordService passwordService;
    private final UserService userService;

    @Autowired
    public PasswordController(PasswordService passwordService, UserService userService) {
        this.passwordService = passwordService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Password> findPasswordsForUser(@PathVariable Long userId){
        User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found, id: " + userId));
        return new ResponseEntity<>(passwordService.findPasswordForUser(user), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Password> savePassword(@RequestBody Password password){
        return new ResponseEntity<>(passwordService.save(password), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Password> deletePassword(@RequestBody Password password){
        passwordService.delete(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
