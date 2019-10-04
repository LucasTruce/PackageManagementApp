package pl.packagemanagement.model.warehouse;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements  WarehouseService{
    private final WarehouseRepository warehouseRepository;

    @Override
    public Page<Warehouse> findAll(int pageNumber, int pageSize, String orderBy, String direction) {
        Page<Warehouse> pagedWarehouse;
        if(direction.equals(Sort.Direction.ASC.name()))
            pagedWarehouse = warehouseRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, orderBy)));
        else
            pagedWarehouse = warehouseRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, orderBy)));
        return pagedWarehouse;
    }

    @Override
    public Optional<Warehouse> findById(Long id) {
        return warehouseRepository.findById(id);
    }

    @Override
    public Warehouse save(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    @Override
    public void delete(Warehouse warehouse) {
        warehouseRepository.delete(warehouse);
    }
}
