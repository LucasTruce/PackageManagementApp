package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Category;
import pl.packagemanagement.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.findById(id).orElse(new Category()), HttpStatus.OK);
    }

    @PostMapping
    public void save(@RequestBody Category category){
        categoryService.save(category);
    }

    @DeleteMapping
    public ResponseEntity<Category> delete(@RequestBody Category category){
        categoryService.delete(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
