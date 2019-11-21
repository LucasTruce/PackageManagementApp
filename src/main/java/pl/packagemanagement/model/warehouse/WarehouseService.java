package pl.packagemanagement.model.warehouse;

import org.springframework.data.domain.Page;
import pl.packagemanagement.model.warehouse.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseService {
    Page<Warehouse> findAll(int pageNumber, int pageSize, String orderBy, String direction);
    Optional<Warehouse> findById(Long id);
    Warehouse save(Warehouse warehouse);
    void delete(Warehouse warehouse);
    Warehouse update(Warehouse warehouse);
}
