package pl.packagemanagement.model.pack;

import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.user.User;

import java.util.List;
import java.util.Optional;

public interface PackageService {
    List<Package> findAll();
    Optional<Package> findById(Long id);
    Optional<Package> findByNumber(String number);
    List<Package> findByUsers(List<User> users);
    void delete(Package pack);
    Package save(Package pack, User user);
}
