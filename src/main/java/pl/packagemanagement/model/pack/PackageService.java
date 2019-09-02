package pl.packagemanagement.model.pack;

import pl.packagemanagement.model.pack.Package;

import java.util.List;
import java.util.Optional;

public interface PackageService {
    List<Package> findAll();
    Optional<Package> findById(Long id);
    Optional<Package> findByNumber(String number);
    void delete(Package pack);
    Package save(Package pack);
}
