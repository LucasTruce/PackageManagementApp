package pl.packagemanagement.model.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.user.User;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.user.UserService;
import pl.packagemanagement.model.role.RoleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("userdetails")
@RequiredArgsConstructor
@CrossOrigin
public class UserDetailsController {
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDetails>> findAllUsersDetails(){

        return new ResponseEntity<>(userDetailsService.findAll(), HttpStatus.OK);
    }


    @GetMapping("/")   //   userdetails?login=nazwa
    public ResponseEntity<UserDetails> findByLogin(@RequestParam(name = "login") String login){
        UserDetails tempUser = userDetailsService.findByUserLoginOrUserEmail(login).orElseThrow(
                () -> new EntityNotFoundException("UserDetails not found, login/email: " + login)
        );
            return new ResponseEntity<>(tempUser, HttpStatus.OK);
    }

    @PostMapping("/")   //userdetails/?login=
    public ResponseEntity<UserDetails> saveUserDetails(@RequestParam(name = "login") String login, @Valid @RequestBody UserDetails userDetails){
        User user = userService.findByLoginOrEmail(login, login).orElseThrow(
                () -> new EntityNotFoundException("User not found, login: " + login)
        );
        return new ResponseEntity<>(userDetailsService.save(userDetails, user), HttpStatus.OK);
    }


    @PutMapping("/") //userdetails/?login=
    public  ResponseEntity<UserDetails> updateUser(@RequestParam(name = "login") String login, @Valid @RequestBody UserDetails userDetails){
        UserDetails tempUserDetails = userDetailsService.findById(userDetails.getId()).orElseThrow(
                () -> new EntityNotFoundException("User not found, id: " + userDetails.getId())
        );
        userDetails.setUser(tempUserDetails.getUser());
        userDetailsService.update(userDetails);

        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

}
