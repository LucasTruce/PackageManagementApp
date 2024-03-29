package pl.packagemanagement.model.sender;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.pack.PackageService;
import pl.packagemanagement.exception.EntityNotFoundException;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("senders")
@RequiredArgsConstructor
@CrossOrigin
public class SenderController {
    private final SenderService senderService;

    @GetMapping
    public ResponseEntity<List<Sender>> findAll(){
        return new ResponseEntity<>(senderService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sender> findById(@PathVariable Long id){
        return new ResponseEntity<>(senderService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Sender not found, id: " + id)
        ), HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<Sender> save(@Valid @RequestBody Sender sender){
        return new ResponseEntity<>(senderService.save(sender), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Sender> delete(@RequestBody Sender sender){
        senderService.delete(sender);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Sender> update(@Valid @RequestBody Sender sender) {
        return new ResponseEntity<>(senderService.save(sender), HttpStatus.OK);
    }
}
