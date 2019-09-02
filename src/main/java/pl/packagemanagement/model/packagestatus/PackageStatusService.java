package pl.packagemanagement.model.packagestatus;

import pl.packagemanagement.model.packagestatus.PackageStatus;

import java.util.List;
import java.util.Optional;

public interface PackageStatusService {
    List<PackageStatus> findAll();
    Optional<PackageStatus> findById(Long id);
    void delete(PackageStatus packageStatus);
    PackageStatus save(PackageStatus packageStatus);
}
