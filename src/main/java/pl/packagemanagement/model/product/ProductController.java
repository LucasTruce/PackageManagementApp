package pl.packagemanagement.model.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.content.Content;
import pl.packagemanagement.model.content.ContentService;
import pl.packagemanagement.model.product.Product;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.product.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductService productService;
    private final ContentService contentService;


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

    @PostMapping("/") // products/?contentId=
    public ResponseEntity<List<Product>> saveAll(@Valid @RequestBody List<Product> products, @RequestParam(name = "contentId") Long contentId){
        Content tempContent = contentService.findById(contentId).orElseThrow(() -> new EntityNotFoundException("Content not found!"));
        return new ResponseEntity<>(productService.saveAll(products, tempContent), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<List<Product>> updateAll(@Valid @RequestBody List<Product> products, @RequestParam(name = "contentId") Long contentId){
        Content tempContent = contentService.findById(contentId).orElseThrow(() -> new EntityNotFoundException("Content not found!"));
        return new ResponseEntity<>(productService.saveAll(products, tempContent), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<List<Product>> delete(@Valid @RequestBody List<Product> products){
        productService.deleteAll(products);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
