package pl.packagemanagement.model.car;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.exception.EntityNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("cars")
@RequiredArgsConstructor
@CrossOrigin
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> findAll(){
        return new ResponseEntity<>(carService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> findById(@PathVariable Long id){
        return new ResponseEntity<>(carService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Car not found, id: " + id)
        ), HttpStatus.OK);
    }

    @PostMapping
    public void save(@Valid @RequestBody Car car){
        carService.save(car);
    }

    @DeleteMapping
    public ResponseEntity<Car> delete(@RequestBody Car car){
        carService.delete(car);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
