package pl.packagemanagement.model.recipient;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.pack.PackageService;
import pl.packagemanagement.model.recipient.Recipient;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.recipient.RecipientService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("recipients")
@RequiredArgsConstructor
@CrossOrigin
public class RecipientController {

    private final RecipientService recipientService;


    @GetMapping
    public ResponseEntity<List<Recipient>> findAllRecipients(){
        return new ResponseEntity<>(recipientService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipient> findById(@PathVariable Long id){
        return new ResponseEntity<>(recipientService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Recipient not found, id: " + id)
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Recipient> save(@Valid @RequestBody Recipient recipient){
        return new ResponseEntity<>(recipientService.save(recipient), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Recipient> delete(@RequestBody Recipient recipient){
        recipientService.delete(recipient);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Recipient> update(@Valid @RequestBody Recipient recipient) {
        return new ResponseEntity<>(recipientService.save(recipient), HttpStatus.OK);
    }
}
