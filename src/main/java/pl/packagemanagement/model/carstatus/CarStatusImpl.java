package pl.packagemanagement.model.carstatus;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarStatusImpl implements CarStatusService {

    private final CarStatusRepository carStatusRepo;

    @Override
    public List<CarStatus> findAll() {
        return carStatusRepo.findAll();
    }

    @Override
    public Optional<CarStatus> findById(Long id) {
        return carStatusRepo.findById(id);
    }

    @Override
    public CarStatus save(CarStatus carStatus) {
        return carStatusRepo.save(carStatus);
    }

    @Override
    public void delete(CarStatus carStatus) {
        carStatusRepo.delete(carStatus);
    }
}
