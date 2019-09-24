package pl.packagemanagement.model.pack;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    Optional<Package> findByPackageNumber(String number);
    List<Package> findPackagesByUsers(List<User> users);
}
