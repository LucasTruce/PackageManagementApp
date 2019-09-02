package pl.packagemanagement.model.packagestatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.model.packagestatus.PackageStatus;

@Repository
public interface PackageStatusRepository extends JpaRepository<PackageStatus,Long> {
}
