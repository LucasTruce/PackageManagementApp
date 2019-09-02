package pl.packagemanagement.model.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.model.car.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
