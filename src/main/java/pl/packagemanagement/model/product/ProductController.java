package pl.packagemanagement.model.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.product.Product;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.product.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;



    @GetMapping
    public ResponseEntity<List<Product>> findAll(){
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        return new ResponseEntity<>(productService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found, id: " + id)
        ), HttpStatus.OK);
    }

    @PostMapping
    public void save(@Valid @RequestBody Product product){
        productService.save(product);
    }

    @DeleteMapping
    public ResponseEntity<Product> delete(@RequestBody Product product){
        productService.delete(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
