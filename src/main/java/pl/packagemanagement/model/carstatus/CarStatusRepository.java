package pl.packagemanagement.model.carstatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.model.carstatus.CarStatus;

@Repository
public interface CarStatusRepository extends JpaRepository<CarStatus, Long> {
}
