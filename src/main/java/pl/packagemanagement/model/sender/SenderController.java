package pl.packagemanagement.model.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.sender.Sender;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.sender.SenderService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("senders")
@RequiredArgsConstructor
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
    public  void save(@Valid @RequestBody Sender sender){
        senderService.save(sender);
    }

    @DeleteMapping
    public ResponseEntity<Sender> delete(@RequestBody Sender sender){
        senderService.delete(sender);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}