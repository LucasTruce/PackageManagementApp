package pl.packagemanagement.model.pack;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    Optional<Package> findByPackageNumber(String number);
    Page<Package> findAllByUsers(List<User> users, Pageable pageable);
    Page<Package> findAllByPackageStatusIdOrderBySenderCity(Long id, Pageable pageable);

}
