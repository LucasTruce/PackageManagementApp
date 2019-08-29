package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Position;
import pl.packagemanagement.entity.User;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.service.PositionService;
import pl.packagemanagement.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final PositionService positionService;

    @Autowired
    public UserController(UserService userService, PositionService positionService) {
        this.userService = userService;
        this.positionService = positionService;
    }

    @CrossOrigin("http://localhost:4200")
    @GetMapping
    public ResponseEntity<List<User>> findAllUsers(){

        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id){
        return new ResponseEntity<>(
                userService.findById(id).orElseThrow( () -> new EntityNotFoundException("User not found, id: " + id)), HttpStatus.FOUND);

    }

    @GetMapping("/")   //   users/?login=nazwa
    public ResponseEntity<User> findByLogin(@RequestParam(name = "login") String login){
        return new ResponseEntity<>(userService.findByLogin(login).orElseThrow(
                () -> new EntityNotFoundException("User not found, login: " + login)
        ), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id){
        User user = userService.findById(id).orElseThrow( () -> new EntityNotFoundException("User not found, id: " + id) );
        userService.delete(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<User> deleteUser(@RequestBody User user){
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public void saveUser(@Valid @RequestBody User user){
        userService.save(user);
    }

    @PutMapping("/{id}") //NALEŻY PODAĆ ID
    public ResponseEntity<User> updateUser(@PathVariable Long id,  @Valid @RequestBody User user){
        User tempUser = userService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found, id: " + id)
        );
        if(user.getPosition() == null)
            throw(new EntityNotFoundException("Position not found, put right position"));

        userService.update(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
