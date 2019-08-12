package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.packagemanagement.entity.CarStatus;
import pl.packagemanagement.service.CarStatusService;

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
        return new ResponseEntity<>(carStatusService.findById(id).orElse(new CarStatus()), HttpStatus.OK);
    }

    @PostMapping
    public void save(@RequestBody CarStatus carStatus){
        carStatusService.save(carStatus);
    }

    @DeleteMapping
    public ResponseEntity<CarStatus> delete(@RequestBody CarStatus carStatus){
        carStatusService.delete(carStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
