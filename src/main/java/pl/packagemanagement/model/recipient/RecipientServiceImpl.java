package pl.packagemanagement.model.recipient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipientServiceImpl implements RecipientService {
    private final ReicpientRepository reicpientRepository;

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
