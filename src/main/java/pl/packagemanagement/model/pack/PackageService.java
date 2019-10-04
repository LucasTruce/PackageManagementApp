package pl.packagemanagement.model.pack;

import org.springframework.data.domain.Page;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.user.User;

import java.util.List;
import java.util.Optional;

public interface PackageService {
    List<Package> findAll();
    Optional<Package> findById(Long id);
    Optional<Package> findByNumber(String number);
    Page<Package> findByUsers(List<User> users, int pageNumber, int pageSize, String orderBy, String direction);
    void delete(Package pack);
    Package save(Package pack, User user);
    Package update(Package pack);
}
