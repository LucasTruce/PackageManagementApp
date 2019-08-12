package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Package;
import pl.packagemanagement.service.PackageService;

import java.util.List;

@RestController
@RequestMapping("packages")
public class PackageController {

    private final PackageService packageService;

    @Autowired
    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping
    public ResponseEntity<List<Package>> findAllPackages() {
        return new ResponseEntity<>(packageService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Package> findById(@PathVariable Long id){
        return new ResponseEntity<>(packageService.findById(id).orElse(new Package()), HttpStatus.OK);
    }

    @GetMapping("/number/{packageNumber}")
    public ResponseEntity<Package> findPackageByNumber(@PathVariable String packageNumber){
        return new ResponseEntity<>(packageService.findByNumber(packageNumber), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Package> delete(@RequestBody Package pack){
        packageService.delete(pack);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public void save(@RequestBody Package pack){
        packageService.save(pack);
    }
}
