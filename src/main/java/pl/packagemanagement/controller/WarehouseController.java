package pl.packagemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.entity.Warehouse;
import pl.packagemanagement.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("warehouses")
public class WarehouseController {

    WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public ResponseEntity<List<Warehouse>> findAll(){
        return new ResponseEntity<>(warehouseService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> findById(@PathVariable Long id){
        return new ResponseEntity<>(warehouseService.findById(id).orElse(new Warehouse()), HttpStatus.OK);
    }

    @PostMapping
    public void save(@RequestBody Warehouse warehouse){
        warehouseService.save(warehouse);
    }

    @DeleteMapping
    public ResponseEntity<Warehouse> delete(@RequestBody Warehouse warehouse){
        warehouseService.delete(warehouse);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
