package pl.packagemanagement.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.packagemanagement.entity.Package;
import pl.packagemanagement.repository.PackageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PackageServiceImpl implements PackageService {

    PackageRepository packageRepository;

    public PackageServiceImpl(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @Override
    public List<Package> findAll() {
        return packageRepository.findAll();
    }

    @Override
    public Optional<Package> findById(Long id) {
        return packageRepository.findById(id);
    }

    @Override
    public Package findByNumber(String number) {
        return packageRepository.findByPackageNumber(number);
    }

    @Override
    public void delete(Package pack) {
        packageRepository.delete(pack);

    }

    @Override
    public Package save(Package pack) {
        return packageRepository.save(pack);
    }
}
