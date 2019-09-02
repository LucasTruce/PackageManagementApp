package pl.packagemanagement.model.car;

import pl.packagemanagement.model.car.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> findAll();
    Optional<Car> findById(Long id);
    Car save(Car car);
    void delete(Car car);
}