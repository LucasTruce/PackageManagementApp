package pl.packagemanagement.model.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.content.Content;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.content.ContentService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("content")
public class ContentController {

    private final ContentService contentService;

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
        return new ResponseEntity<>(contentService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Content not found, id: " + id)
        ), HttpStatus.OK);
    }

    @PostMapping
    public void save(@Valid  @RequestBody Content content){
        contentService.save(content);
    }

    @DeleteMapping
    public ResponseEntity<Content> delete(@RequestBody Content content){
        contentService.delete(content);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
