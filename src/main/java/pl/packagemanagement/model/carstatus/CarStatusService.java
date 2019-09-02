package pl.packagemanagement.model.carstatus;

import pl.packagemanagement.model.carstatus.CarStatus;

import java.util.List;
import java.util.Optional;

public interface CarStatusService {
    List<CarStatus> findAll();
    Optional<CarStatus> findById(Long id);
    CarStatus save(CarStatus carStatus);
    void delete(CarStatus carStatus);
}
