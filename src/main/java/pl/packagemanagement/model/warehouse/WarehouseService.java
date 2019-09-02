package pl.packagemanagement.model.warehouse;

import pl.packagemanagement.model.warehouse.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseService {
    List<Warehouse> findAll();
    Optional<Warehouse> findById(Long id);
    Warehouse save(Warehouse warehouse);
    void delete(Warehouse warehouse);
}
