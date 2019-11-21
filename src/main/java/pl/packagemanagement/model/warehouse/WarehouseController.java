package pl.packagemanagement.model.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.car.Car;
import pl.packagemanagement.model.warehouse.Warehouse;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.warehouse.WarehouseService;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    @GetMapping("/{id}/document")
    public ResponseEntity<Resource> getPdf(@PathVariable("id") Long id){
        Warehouse warehouse = warehouseService.findById(id).orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));

        String fileName = warehouse.getCity() + "-" + warehouse.getStreet() +  ".pdf";
        String path = "src/main/resources/warehouses";
        String finalPath = path + "/" + fileName;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        FileInputStream file;
        try {
            file = new FileInputStream(finalPath);
        }catch (FileNotFoundException e){
            throw new EntityNotFoundException("file not found");
        }
        InputStreamResource resource = new InputStreamResource(file);

        headers.add(HttpHeaders.CONTENT_DISPOSITION, fileName);   //jesli dodamy tą linijke to po otrzymaniu żądania włączy się pobieranie w przeglądarce
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
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
        return new ResponseEntity<>(warehouseService.update(warehouse), HttpStatus.OK);
    }
}
