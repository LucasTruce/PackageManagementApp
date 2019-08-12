package pl.packagemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.entity.Package;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    Package findByPackageNumber(String number);
}
