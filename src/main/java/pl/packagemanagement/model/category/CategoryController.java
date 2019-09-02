package pl.packagemanagement.model.category;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.category.Category;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.category.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Category not found, id: " + id)
        ), HttpStatus.OK);
    }

    @PostMapping
    public void save(@Valid  @RequestBody Category category){
        categoryService.save(category);
    }

    @DeleteMapping
    public ResponseEntity<Category> delete(@RequestBody Category category){
        categoryService.delete(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
