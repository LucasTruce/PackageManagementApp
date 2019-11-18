package pl.packagemanagement.model.code;

import com.fasterxml.jackson.core.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.packagemanagement.model.car.Car;
import pl.packagemanagement.model.car.CarService;
import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.code.CodeService;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.pack.PackageService;
import pl.packagemanagement.model.product.Product;
import pl.packagemanagement.model.product.ProductService;
import pl.packagemanagement.model.warehouse.Warehouse;
import pl.packagemanagement.model.warehouse.WarehouseService;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@RestController
@RequestMapping("codes")
@RequiredArgsConstructor
@CrossOrigin
public class CodeController {
    private final CodeService codeService;
    private final PackageService packageService;
    private final ProductService productService;
    private final WarehouseService warehouseService;
    private final CarService carService;

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

    @GetMapping("/{id}/qrcode")
    public ResponseEntity<Map<String,String>> getQRCodeImage(@PathVariable Long id) throws WriterException, IOException {
        Code code = codeService.findById(id).orElseThrow(() -> new EntityNotFoundException("Code not found"));

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(code.toString(), BarcodeFormat.QR_CODE, 256, 256);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        String encodeImage = Base64.getEncoder().encodeToString(pngData);
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("content", encodeImage);

        return new ResponseEntity<>(jsonMap, HttpStatus.OK);
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

    @PostMapping("/") // codes/?warehouseId= ?packId= ?carId=
    public void saveWithWarehouse(@Valid @RequestBody Code code,
                                  @RequestParam(required = false) Long warehouseId,
                                  @RequestParam(required = false) Long packId,
                                  @RequestParam(required = false) Long carId){
        if(packId != null) {
            Package tempPack = packageService.findById(packId).orElseThrow(() -> new EntityNotFoundException("Package not found!"));
            tempPack.setCode(code);
            code.setPack(tempPack);
            codeService.save(code);
        }
        if(warehouseId != null) {
            Warehouse warehouse = warehouseService.findById(warehouseId).orElseThrow(() -> new EntityNotFoundException("Warehouse not found"));
            warehouse.setCode(code);
            code.setWarehouse(warehouse);
            codeService.save(code);
        }
        if(carId != null) {
            Car car = carService.findById(carId).orElseThrow(() -> new EntityNotFoundException("Car not found"));
            car.setCode(code);
            code.setCar(car);
            codeService.save(code);
        }
    }



    @DeleteMapping
    public ResponseEntity<Code> delete(@RequestBody Code code){
        codeService.delete(code);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
