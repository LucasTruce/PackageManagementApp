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
import pl.packagemanagement.model.history.History;
import pl.packagemanagement.model.history.HistoryRepository;
import pl.packagemanagement.model.packagestatus.PackageStatus;
import pl.packagemanagement.model.packagestatus.PackageStatusRepository;
import pl.packagemanagement.model.packagestatus.PackageStatusService;
import pl.packagemanagement.model.user.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;
    private final PackageStatusRepository packageStatusRepository;
    private final CodeRepository codeRepository;
    private final HistoryRepository historyRepository;


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
            PackageStatus packageStatus = packageStatusRepository.findById(3l).get();
            packageStatus.getPackages().add(pack);
            pack.setPackageStatus(packageStatus);
            pack.setPackageNumber(generatedString);
            pack.getUsers().add(user);
            user.getPackages().add(pack);
            pack = packageRepository.save(pack); //zapis paczki do bazy
            //utworzenie nowej historii dla paczki, przy tworzeniu paczki powstaje historia W oczekiwaniu na kuriera...
            History history = historyRepository.save(new History(null, "W oczekiwaniu na kuriera", LocalDateTime.now(ZoneId.of("Europe/Warsaw")), "U nadawcy", pack));
            pack.getHistories().add(history); //dodanie nowej historii do paczki

        }
        else
            this.save(pack, user);
        return pack; //zwracamy paczke
    }

    @Override
    public Package update(Package pack) {
        Package tempPack = packageRepository.findById(pack.getId()).orElseThrow(() -> new EntityNotFoundException("Package not found"));
        PackageStatus packageStatus = packageStatusRepository.findById(pack.getPackageStatus().getId()).get();
        History tempHistory;
        if(tempPack.getPackageStatus().getId() != pack.getPackageStatus().getId()) {
            if(pack.getWarehouses().size() > 0)
                tempHistory = historyRepository.save(new History(null, packageStatus.getName(), LocalDateTime.now(ZoneId.of("Europe/Warsaw")), pack.getWarehouses().get(0).getCity(), tempPack));
            else{
                tempHistory = historyRepository.save(new History(null, packageStatus.getName(), LocalDateTime.now(ZoneId.of("Europe/Warsaw")), "U nadawcy", tempPack));
            }
            pack.getHistories().add(tempHistory);
            if(tempPack.getPackageStatus().getId() == 3)
                pack.setDate(LocalDateTime.now());
        }
        return packageRepository.save(pack);
    }
}
