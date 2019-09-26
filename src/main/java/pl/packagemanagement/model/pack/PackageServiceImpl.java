package pl.packagemanagement.model.pack;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.packagestatus.PackageStatus;
import pl.packagemanagement.model.packagestatus.PackageStatusService;
import pl.packagemanagement.model.user.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;
    private final PackageStatusService packageStatusService;


    @Override
    public List<Package> findAll() {
        return packageRepository.findAll();
    }

    @Override
    public Optional<Package> findById(Long id) {
        return packageRepository.findById(id);
    }

    @Override
    public Optional<Package> findByNumber(String number) {
        return packageRepository.findByPackageNumber(number);
    }

    @Override
    public List<Package> findByUsers(List<User> users) {
        return packageRepository.findPackagesByUsers(users);
    }

    @Override
    public void delete(Package pack) {
        packageRepository.delete(pack);

    }

    @Override
    public Package save(Package pack, User user) {
        String generatedString = RandomStringUtils.random(13, false, true);
        if(packageRepository.findByPackageNumber(generatedString).isEmpty()) {
            PackageStatus packageStatus = packageStatusService.findById(3l).get();
            packageStatus.getPackages().add(pack);
            pack.setPackageStatus(packageStatus);
            pack.setPackageNumber(generatedString);
            pack.getUsers().add(user);
            user.getPackages().add(pack);
        }
        else
            this.save(pack, user);
        return packageRepository.save(pack);
    }
}
