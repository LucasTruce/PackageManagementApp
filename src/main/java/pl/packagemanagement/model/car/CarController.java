package pl.packagemanagement.model.car;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.exception.EntityNotFoundException;

import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("cars")
@RequiredArgsConstructor
@CrossOrigin
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<Page<Car>> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                             @RequestParam(defaultValue = "10") int pageSize,
                                             @RequestParam(defaultValue = "id") String orderBy,
                                             @RequestParam(defaultValue = "ASC") String direction){
        return new ResponseEntity<>(carService.findAll(pageNumber, pageSize, orderBy, direction), HttpStatus.OK);
    }

    @GetMapping("/{id}")  //cars/{id}
    public ResponseEntity<Car> findById(@PathVariable Long id){
        return new ResponseEntity<>(carService.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Car not found, id: " + id)
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}/document")
    public ResponseEntity<Resource> getPdf(@PathVariable("id") Long id){
        Car car = carService.findById(id).orElseThrow(() -> new EntityNotFoundException("package not found"));

        String fileName = car.getBrand() + "-" + car.getLicensePlate() + ".pdf";
        String path = "src/main/resources/cars";
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
    public void save(@Valid @RequestBody Car car){
        carService.save(car);
    }

    @DeleteMapping //cars?id=
    public ResponseEntity<Car> delete(@RequestParam(name = "id") Long id){
        carService.delete(carService.findById(id).orElseThrow(() -> new EntityNotFoundException("Car not found, id: " + id)));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Car> updateCar(@Valid @RequestBody Car car) {
        Car tempCar = carService.findById(car.getId()).orElseThrow(() -> new EntityNotFoundException("Car not found"));
        return new ResponseEntity<>(carService.update(car), HttpStatus.OK);
    }
}
