package pl.packagemanagement.model.pack;


import lombok.RequiredArgsConstructor;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.user.User;
import pl.packagemanagement.model.user.UserService;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
   public ResponseEntity<Package> findById(@AuthenticationPrincipal UserDetails user, @PathVariable Long id){
        Package pack = packageService.findById(id).orElseThrow(() -> new EntityNotFoundException("Package not found, id: " + id));
        boolean role = false;
        for(Object product : user.getAuthorities()){
            if(product.toString().equals("ROLE_ADMIN") || product.toString().equals("ROLE_WORKER")){
           role = true;
       }
   }
        if(pack.getUsers().get(0).getLogin().equals(user.getUsername()) || pack.getUsers().get(0).getEmail().equals(user.getUsername()) || role)
            return new ResponseEntity<>(pack, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/admin")
    public ResponseEntity<Page<Package>> findPackagesForAdmin(@RequestParam(defaultValue = "0") int pageNumber,
                                                              @RequestParam(defaultValue = "10") int pageSize,
                                                              @RequestParam(defaultValue = "id") String orderBy,
                                                              @RequestParam(defaultValue = "ASC") String direction) {

        return new ResponseEntity<>(packageService.findForAdmin(pageNumber, pageSize, orderBy, direction), HttpStatus.OK);
    }

    @GetMapping("/forDelivery")
    public ResponseEntity<Page<Package>> findPackageForDelivery(@RequestParam(defaultValue = "0") int pageNumber,
                                                                @RequestParam(defaultValue = "10") int pageSize) {

        return new ResponseEntity<>(packageService.findForDelivery(pageNumber, pageSize), HttpStatus.OK);
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

    @GetMapping("/number/{packageNumber}") //packages/number/X
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
    public ResponseEntity<Package> save(@RequestParam(name = "login") String login, @Valid @RequestBody Package pack) {
        User user = userService.findByLoginOrEmail(login, login).orElseThrow(
                () -> new EntityNotFoundException("User not found, id: " + login)
        );


        return new ResponseEntity<>(packageService.save(pack, user), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Package> updatePackage(@Valid @RequestBody Package pack) {

        return new ResponseEntity<>(packageService.update(pack), HttpStatus.OK);
    }

    @GetMapping("/{id}/raport")
    public ResponseEntity<Resource> getRaport(@PathVariable("id") Long id){
        Package pack = packageService.findById(id).orElseThrow(() -> new EntityNotFoundException("package not found"));

        String fileName = pack.getUsers().get(0).getLogin() + "-" + pack.getPackageNumber() + ".pdf";
        String path = "src/main/resources/documents/" + pack.getUsers().get(0).getLogin();
        String finalPath = path + "/" + fileName;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        FileInputStream file;
        try {
            file = new FileInputStream(finalPath);
        }catch (FileNotFoundException e){
            throw new EntityNotFoundException("file not found");
        }
        InputStreamResource resource = new InputStreamResource(file);

        headers.add(HttpHeaders.CONTENT_DISPOSITION, fileName);   //jesli dodamy tą linijke to po otrzymaniu żądania włączy się pobieranie w przeglądarce
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
