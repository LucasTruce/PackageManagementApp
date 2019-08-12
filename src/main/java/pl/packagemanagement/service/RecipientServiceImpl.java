package pl.packagemanagement.service;

import org.springframework.stereotype.Service;
import pl.packagemanagement.entity.Recipient;
import pl.packagemanagement.repository.ReicpientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipientServiceImpl implements RecipientService {
    ReicpientRepository reicpientRepository;

    public RecipientServiceImpl(ReicpientRepository reicpientRepository) {
        this.reicpientRepository = reicpientRepository;
    }

    @Override
    public List<Recipient> findAll() {
        return reicpientRepository.findAll();
    }

    @Override
    public Optional<Recipient> findById(Long id) {
        return reicpientRepository.findById(id);
    }

    @Override
    public Recipient save(Recipient recipient) {
        return reicpientRepository.save(recipient);
    }

    @Override
    public void delete(Recipient recipient) {
        reicpientRepository.delete(recipient);
    }
}
