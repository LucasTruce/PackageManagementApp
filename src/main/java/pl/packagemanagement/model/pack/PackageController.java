package pl.packagemanagement.model.pack;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.model.code.CodeService;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.pack.PackageService;
import pl.packagemanagement.model.packagestatus.PackageStatus;
import pl.packagemanagement.model.packagestatus.PackageStatusService;
import pl.packagemanagement.model.user.User;
import pl.packagemanagement.model.user.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("packages")
@RequiredArgsConstructor
@CrossOrigin
public class PackageController {

    private final PackageService packageService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<Package>> findAllPackages() {
        return new ResponseEntity<>(packageService.findAll(), HttpStatus.OK);
    }

   @GetMapping("/{id}")
   public ResponseEntity<Package> findById(@PathVariable Long id){
        return new ResponseEntity<>(packageService.findById(id).orElseThrow(() -> new EntityNotFoundException("Package not found, id: " + id)), HttpStatus.OK);
    }

    @GetMapping("/") // packages/?login=
    public ResponseEntity<Page<Package>> findPackagesForUser(@RequestParam(name = "login") String login,
                                                             @RequestParam(defaultValue = "0") int pageNumber,
                                                             @RequestParam(defaultValue = "10") int pageSize,
                                                             @RequestParam(defaultValue = "id") String orderBy,
                                                             @RequestParam(defaultValue = "ASC") String direction) {
        User user = userService.findByLoginOrEmail(login, login).orElseThrow(() -> new EntityNotFoundException("User not found, login: " + login));
        List<User> users = new ArrayList<>();
        users.add(user);
        return new ResponseEntity<>(packageService.findByUsers(users, pageNumber, pageSize, orderBy, direction), HttpStatus.OK);
    }

    @GetMapping("/number/{packageNumber}") // packages/number/X
    public ResponseEntity<Package> findPackageByNumber(@PathVariable String packageNumber){
        return new ResponseEntity<>(packageService.findByNumber(packageNumber).orElseThrow(
                () -> new EntityNotFoundException("Package not found, packageNumber: " + packageNumber)
        ), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Package> delete(@RequestParam(name = "id") Long id){
        packageService.delete(packageService.findById(id).orElseThrow(() -> new EntityNotFoundException("Package not found")));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Package> save(@RequestParam(name = "login") String login, @Valid @RequestBody Package pack){
        User user = userService.findByLoginOrEmail(login, login).orElseThrow(
                () -> new EntityNotFoundException("User not found, id: " + login)
        );

        return new ResponseEntity<>(packageService.save(pack, user), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Package> updatePackage(@Valid @RequestBody Package pack) {
        return new ResponseEntity<>(packageService.update(pack), HttpStatus.OK);
    }
}
