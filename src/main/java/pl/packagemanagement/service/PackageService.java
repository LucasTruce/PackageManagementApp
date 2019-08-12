package pl.packagemanagement.service;

import pl.packagemanagement.entity.Package;

import java.util.List;
import java.util.Optional;

public interface PackageService {
    List<Package> findAll();
    Optional<Package> findById(Long id);
    Package findByNumber(String number);
    void delete(Package pack);
    Package save(Package pack);
}
