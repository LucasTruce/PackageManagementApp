package pl.packagemanagement.model.packagestatus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageStatusImpl implements PackageStatusService {
    private final PackageStatusRepository packageStatusRepository;

    @Override
    public List<PackageStatus> findAll() {
        return packageStatusRepository.findAll();
    }

    @Override
    public Optional<PackageStatus> findById(Long id) {
        return packageStatusRepository.findById(id);
    }

    @Override
    public void delete(PackageStatus packageStatus) {
        packageStatusRepository.delete(packageStatus);
    }

    @Override
    public PackageStatus save(PackageStatus packageStatus) {
        return packageStatusRepository.save(packageStatus);
    }
}
