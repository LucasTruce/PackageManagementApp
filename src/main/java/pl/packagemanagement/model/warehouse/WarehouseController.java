package pl.packagemanagement.model.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.warehouse.Warehouse;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.warehouse.WarehouseService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("warehouses")
@RequiredArgsConstructor
@CrossOrigin
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<Warehouse>> findAll(){
        return new ResponseEntity<>(warehouseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> findById(@PathVariable Long id){
        return new ResponseEntity<>(warehouseService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Warehouse not found, id: " + id)
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Warehouse> save(@Valid @RequestBody Warehouse warehouse){

        return new ResponseEntity<>(warehouseService.save(warehouse), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Warehouse> delete(@RequestBody Warehouse warehouse){
        warehouseService.delete(warehouse);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
