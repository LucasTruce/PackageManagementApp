package pl.packagemanagement.service;

import org.springframework.stereotype.Service;
import pl.packagemanagement.entity.Sender;
import pl.packagemanagement.repository.SenderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SenderServiceImpl implements SenderService {
    SenderRepository senderRepository;

    public SenderServiceImpl(SenderRepository senderRepository) {
        this.senderRepository = senderRepository;
    }

    @Override
    public List<Sender> findAll() {
        return senderRepository.findAll();
    }

    @Override
    public Optional<Sender> findById(Long id) {
        return senderRepository.findById(id);
    }

    @Override
    public Sender save(Sender sender) {
        return senderRepository.save(sender);
    }

    @Override
    public void delete(Sender sender) {
        senderRepository.delete(sender);
    }
}
