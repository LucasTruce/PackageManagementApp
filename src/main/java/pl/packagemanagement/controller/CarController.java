package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Car;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.service.CarService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

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
