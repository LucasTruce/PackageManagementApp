package pl.packagemanagement.model.code;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.code.CodeService;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.pack.PackageService;
import pl.packagemanagement.model.product.Product;
import pl.packagemanagement.model.product.ProductService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("codes")
@RequiredArgsConstructor
@CrossOrigin
public class CodeController {
    private final CodeService codeService;
    private final PackageService packageService;
    private final ProductService productService;

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

    @PostMapping("/{packId}") // codes/packId
    public void saveWithPackage(@PathVariable(name = "packId") Long packId, @Valid @RequestBody Code code){
        Package tempPack = packageService.findById(packId).orElseThrow(
                () -> new EntityNotFoundException("package not found!")
        );

        tempPack.setCode(code);
        code.setPack(tempPack);

        codeService.save(code);
    }

    @PostMapping // codes
    public void saveWithProducts(@Valid @RequestBody List<Code> codes) {
        for (Code code: codes) {
            Product product = productService.findById(Long.valueOf(code.getFilePath())).orElseThrow(() -> new EntityNotFoundException("Product not found"));
            product.setCode(code);
            code.setProduct(product);
        }
        codeService.saveAll(codes);
    }



    @DeleteMapping
    public ResponseEntity<Code> delete(@RequestBody Code code){
        codeService.delete(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
