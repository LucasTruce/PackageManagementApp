package pl.packagemanagement.model.role;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.packagemanagement.model.userdetails.UserDetails;
import pl.packagemanagement.exception.EntityNotFoundException;

import pl.packagemanagement.model.userdetails.UserDetailsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@CrossOrigin
public class RoleController {

    private final RoleService roleService;

}
