package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Product;
import pl.packagemanagement.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll(){
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        return new ResponseEntity<>(productService.findById(id).orElse(new Product()), HttpStatus.OK);
    }

    @PostMapping
    public void save(@RequestBody Product product){
        productService.save(product);
    }

    @DeleteMapping
    public ResponseEntity<Product> delete(@RequestBody Product product){
        productService.delete(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
