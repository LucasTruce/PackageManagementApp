package pl.packagemanagement.model.content;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.pack.PackageService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("content")
@RequiredArgsConstructor
@CrossOrigin
public class ContentController {

    private final ContentService contentService;
    private final PackageService packageService;

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
    public ResponseEntity<Content> save(@Valid  @RequestBody Content content, @RequestParam(name = "packId") Long packId){
        Package tempPack = packageService.findById(packId).orElseThrow( () -> new EntityNotFoundException("Package not found, id: " + packId));
        content.setPack(tempPack);
        tempPack.setContent(content);
        return new ResponseEntity<>(contentService.save(content), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Content> delete(@RequestBody Content content){
        contentService.delete(content);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
