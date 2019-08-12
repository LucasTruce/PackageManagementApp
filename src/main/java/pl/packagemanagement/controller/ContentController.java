package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Content;
import pl.packagemanagement.service.ContentService;

import java.util.List;

@RestController
@RequestMapping("content")
public class ContentController {

    ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public ResponseEntity<List<Content>> findAll(){
        return new ResponseEntity<>(contentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> findById(@PathVariable Long id){
        return new ResponseEntity<>(contentService.findById(id).orElse(new Content()), HttpStatus.OK);
    }

    @PostMapping
    public void save(@RequestBody Content content){
        contentService.save(content);
    }

    @DeleteMapping
    public ResponseEntity<Content> delete(@RequestBody Content content){
        contentService.delete(content);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
