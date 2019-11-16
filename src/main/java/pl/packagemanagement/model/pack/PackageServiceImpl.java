package pl.packagemanagement.model.pack;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.model.code.CodeRepository;
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
    private final CodeRepository codeRepository;


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
    public Page<Package> findByUsers(List<User> users, int pageNumber, int pageSize, String orderBy, String direction) {
        Page<Package> pagedPackage;
        if(direction.equals(Sort.Direction.ASC.name()))
            pagedPackage = packageRepository.findAllByUsers(users, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, orderBy)));
        else
            pagedPackage = packageRepository.findAllByUsers(users, PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, orderBy)));

        return pagedPackage;
    }

    @Override
    public void delete(Package pack) {
        packageRepository.delete(pack);

    }

    @Override
    public Package save(Package pack, User user) {
        String generatedString = RandomStringUtils.random(13, false, true);
        if(packageRepository.findByPackageNumber(generatedString).isEmpty()) {
            pack.getCode().setFilePath(generatedString);
            pack.setCode(codeRepository.save(pack.getCode()));
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

    @Override
    public Package update(Package pack) {
        return packageRepository.save(pack);
    }
}
