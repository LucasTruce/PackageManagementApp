package pl.packagemanagement.model.car;

import org.springframework.data.domain.Page;
import pl.packagemanagement.model.car.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    Page<Car> findAll(int pageNumber, int pageSize, String orderBy, String direction);
    Optional<Car> findById(Long id);
    Car save(Car car);
    void delete(Car car);
}
