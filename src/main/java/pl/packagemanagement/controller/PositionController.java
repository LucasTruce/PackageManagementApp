package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Car;
import pl.packagemanagement.entity.Position;
import pl.packagemanagement.entity.User;
import pl.packagemanagement.repository.PositionRepository;
import pl.packagemanagement.service.PositionService;
import pl.packagemanagement.service.UserService;

import java.util.List;

@RestController
@RequestMapping("positions")
public class PositionController {

    PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public ResponseEntity<List<Position>> findAll(){
        return new ResponseEntity<>(positionService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/users/{userId}/position")
    public ResponseEntity<List<Position>> findPositionsForUser(@PathVariable Long userId){
        return new ResponseEntity<>(positionService.findPositionForUser(userId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> findById(@PathVariable Long id){
        return new ResponseEntity<>(positionService.findById(id), HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<Position> deletePosition(@RequestBody Position position){
        positionService.delete(position);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Position> savePosition(@RequestBody Position position){
        return new ResponseEntity<>(positionService.save(position), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Position> updatePosition(@RequestBody Position position){
        return new ResponseEntity<>(positionService.save(position), HttpStatus.OK);
    }

}
