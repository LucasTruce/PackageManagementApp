package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Password;
import pl.packagemanagement.service.PasswordService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/password")
public class PasswordController {
    PasswordService passwordService;

    @Autowired
    public PasswordController(PasswordService passwordService){
        this.passwordService = passwordService;
    }

    @GetMapping
    public ResponseEntity<Password> findPasswordsForUser(@PathVariable Long userId){
        return new ResponseEntity<>(passwordService.findPasswordForUser(userId), HttpStatus.OK);
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
