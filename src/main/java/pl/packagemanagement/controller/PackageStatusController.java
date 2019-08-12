package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Package;
import pl.packagemanagement.entity.PackageStatus;
import pl.packagemanagement.service.PackageStatusService;

import java.util.List;

@RestController
@RequestMapping("/packstatus")
public class PackageStatusController {
    PackageStatusService packStatusService;

    @Autowired
    public PackageStatusController(PackageStatusService packStatusService) {
        this.packStatusService = packStatusService;
    }

    @GetMapping
    public ResponseEntity<List<PackageStatus>> findAll(){
        return new ResponseEntity<>(packStatusService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageStatus> findById(@PathVariable Long id){
        return new ResponseEntity<>(packStatusService.findById(id).orElse(new PackageStatus()), HttpStatus.OK);
    }

    @PostMapping
    public void save(@RequestBody PackageStatus packageStatus){
        packStatusService.save(packageStatus);
    }

    @DeleteMapping
    public ResponseEntity<PackageStatus> delete(@RequestBody PackageStatus packageStatus){
        packStatusService.delete(packageStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
