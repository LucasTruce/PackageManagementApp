package pl.packagemanagement.model.packagestatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.packagestatus.PackageStatus;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.packagestatus.PackageStatusService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/packstatus")
public class PackageStatusController {
    private final PackageStatusService packStatusService;

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
        return new ResponseEntity<>(packStatusService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Package not found, id: " + id)
        ), HttpStatus.OK);
    }

    @PostMapping
    public void save(@Valid @RequestBody PackageStatus packageStatus){
        packStatusService.save(packageStatus);
    }

    @DeleteMapping
    public ResponseEntity<PackageStatus> delete(@RequestBody PackageStatus packageStatus){
        packStatusService.delete(packageStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
