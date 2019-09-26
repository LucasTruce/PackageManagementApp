package pl.packagemanagement.model.car;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.packagemanagement.model.car.Car;
import pl.packagemanagement.model.car.CarRepository;
import pl.packagemanagement.model.car.CarService;
import pl.packagemanagement.model.carstatus.CarStatus;
import pl.packagemanagement.model.carstatus.CarStatusRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarStatusRepository carStatusRepository;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public Car save(Car car) {
        CarStatus carStatus = carStatusRepository.findById(2l).get();
        carStatus.getCars().add(car);
        car.setCarStatus(carStatus);
        return carRepository.save(car);
    }

    @Override
    public void delete(Car car) {
        carRepository.delete(car);
    }
}
