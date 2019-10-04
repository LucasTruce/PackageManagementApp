package pl.packagemanagement.model.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<Warehouse>> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                   @RequestParam(defaultValue = "10") int pageSize,
                                                   @RequestParam(defaultValue = "id") String orderBy,
                                                   @RequestParam(defaultValue = "ASC") String direction){
        return new ResponseEntity<>(warehouseService.findAll(pageNumber, pageSize, orderBy, direction), HttpStatus.OK);
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

    @DeleteMapping //warehouses?id=
    public ResponseEntity<Warehouse> delete(@RequestParam(name = "id") Long id){
        warehouseService.delete(warehouseService.findById(id).orElseThrow(() -> new EntityNotFoundException("Warehouse not found, id" + id)));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Warehouse> updateWarehouse(@Valid @RequestBody Warehouse warehouse) {
        Warehouse tempWarehouse = warehouseService.findById(warehouse.getId()).orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));
        return new ResponseEntity<>(warehouseService.save(warehouse), HttpStatus.OK);
    }
}
