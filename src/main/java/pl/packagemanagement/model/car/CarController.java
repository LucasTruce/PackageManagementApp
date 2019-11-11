package pl.packagemanagement.model.car;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<Car>> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                             @RequestParam(defaultValue = "10") int pageSize,
                                             @RequestParam(defaultValue = "id") String orderBy,
                                             @RequestParam(defaultValue = "ASC") String direction){
        return new ResponseEntity<>(carService.findAll(pageNumber, pageSize, orderBy, direction), HttpStatus.OK);
    }

    @GetMapping("/{id}")  //cars/{id}
    public ResponseEntity<Car> findById(@PathVariable Long id){
        return new ResponseEntity<>(carService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Car not found, id: " + id)
        ), HttpStatus.OK);
    }

    @PostMapping
    public void save(@Valid @RequestBody Car car){
        carService.save(car);
    }

    @DeleteMapping //cars?id=
    public ResponseEntity<Car> delete(@RequestParam(name = "id") Long id){
        carService.delete(carService.findById(id).orElseThrow(() -> new EntityNotFoundException("Car not found, id: " + id)));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Car> updateCar(@Valid @RequestBody Car car) {
        Car tempCar = carService.findById(car.getId()).orElseThrow(() -> new EntityNotFoundException("Car not found"));
        return new ResponseEntity<>(carService.update(car), HttpStatus.OK);
    }
}
