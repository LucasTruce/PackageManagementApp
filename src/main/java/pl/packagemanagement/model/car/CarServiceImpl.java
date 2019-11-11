package pl.packagemanagement.model.car;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.packagemanagement.model.carstatus.CarStatus;
import pl.packagemanagement.model.carstatus.CarStatusRepository;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarStatusRepository carStatusRepository;

    @Override
    public Page<Car> findAll(int pageNumber, int pageSize, String orderBy, String direction) {
        Page<Car> pagedCar;
        if(direction.equals(Sort.Direction.ASC.name()))
            pagedCar = carRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, orderBy)));
        else
            pagedCar = carRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, orderBy)));
        return pagedCar;
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

    @Override
    public Car update(Car car) {
        return carRepository.save(car);
    }
}
