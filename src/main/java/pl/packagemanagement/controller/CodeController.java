package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Code;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.service.CodeService;

import java.util.List;

@RestController
@RequestMapping("codes")
public class CodeController {
    private final CodeService codeService;

    @Autowired
    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping
    public ResponseEntity<List<Code>> findAll(){
        return new ResponseEntity<>(codeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Code> findById(@PathVariable Long id){
        return new ResponseEntity<>(codeService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Code not found, id: " + id)
        ), HttpStatus.OK);
    }

    @PostMapping
    public void save(@RequestBody Code code){
        codeService.save(code);
    }

    @DeleteMapping
    public ResponseEntity<Code> delete(@RequestBody Code code){
        codeService.delete(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
