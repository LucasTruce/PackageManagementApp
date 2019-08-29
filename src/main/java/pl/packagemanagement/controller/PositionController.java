package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pl.packagemanagement.entity.Position;
import pl.packagemanagement.entity.User;
import pl.packagemanagement.exception.EntityNotFoundException;

import pl.packagemanagement.service.PositionService;
import pl.packagemanagement.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PositionController {

    private final PositionService positionService;
    private final UserService userService;

    @Autowired
    public PositionController(PositionService positionService, UserService userService) {
        this.positionService = positionService;
        this.userService = userService;
    }

    @GetMapping("positions")
    public ResponseEntity<List<Position>> findAll(){
        return new ResponseEntity<>(positionService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/users/{userId}/position")
    public ResponseEntity<List<Position>> findPositionForUser(@PathVariable Long userId){
        User user = userService.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found, id: " + userId));
        return new ResponseEntity<>(positionService.findPositionByUser(user), HttpStatus.OK);
    }

    @GetMapping("positions/{id}")
    public ResponseEntity<Position> findById(@PathVariable Long id){
        return new ResponseEntity<>(positionService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Position not found, id: " + id)
        ), HttpStatus.OK);
    }


    @DeleteMapping("positions")
    public ResponseEntity<Position> deletePosition(@RequestBody Position position){
        positionService.delete(position);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("positions")
    public ResponseEntity<Position> savePosition(@Valid @RequestBody Position position){
        return new ResponseEntity<>(positionService.save(position), HttpStatus.OK);
    }

    @PutMapping("positions/{id}")
    public ResponseEntity<Position> updatePosition(@PathVariable Long id, @Valid @RequestBody Position position){
        Position oldPosition = positionService.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found, id: " + id));

        return new ResponseEntity<>(positionService.save(position), HttpStatus.OK);
    }

}
