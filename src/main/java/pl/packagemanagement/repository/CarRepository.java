package pl.packagemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
