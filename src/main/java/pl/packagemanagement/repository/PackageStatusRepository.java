package pl.packagemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.entity.PackageStatus;

@Repository
public interface PackageStatusRepository extends JpaRepository<PackageStatus,Long> {
}
