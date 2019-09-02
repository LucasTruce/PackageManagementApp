package pl.packagemanagement.model.carstatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.packagemanagement.model.carstatus.CarStatus;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.carstatus.CarStatusService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("carstatus")
public class CarStatusController {
    private final CarStatusService carStatusService;

    @Autowired
    public CarStatusController(CarStatusService carStatusService) {
        this.carStatusService = carStatusService;
    }

    @GetMapping
    public ResponseEntity<List<CarStatus>> findAll(){
        return new ResponseEntity<>(carStatusService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarStatus> findById(@PathVariable Long id){
        return new ResponseEntity<>(carStatusService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("CarStatus not found, id: " + id)
        ), HttpStatus.OK);
    }

    @PostMapping
    public void save(@Valid @RequestBody CarStatus carStatus){
        carStatusService.save(carStatus);
    }

    @DeleteMapping
    public ResponseEntity<CarStatus> delete(@RequestBody CarStatus carStatus){
        carStatusService.delete(carStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
