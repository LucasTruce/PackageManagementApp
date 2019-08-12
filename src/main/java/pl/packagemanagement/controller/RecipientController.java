package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Recipient;
import pl.packagemanagement.service.RecipientService;

import java.util.List;

@RestController
@RequestMapping("recipients")
public class RecipientController {

    private final RecipientService recipientService;

    @Autowired
    public RecipientController(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    @GetMapping
    public ResponseEntity<List<Recipient>> findAllRecipients(){
        return new ResponseEntity<>(recipientService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipient> findById(@PathVariable Long id){
        return new ResponseEntity<>(recipientService.findById(id).orElse(new Recipient()), HttpStatus.OK);
    }

    @PostMapping
    public void save(@RequestBody Recipient recipient){
        recipientService.save(recipient);
    }

    @DeleteMapping
    public ResponseEntity<Recipient> delete(@RequestBody Recipient recipient){
        recipientService.delete(recipient);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
