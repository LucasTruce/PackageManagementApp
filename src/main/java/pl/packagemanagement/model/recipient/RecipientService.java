package pl.packagemanagement.model.recipient;

import pl.packagemanagement.model.recipient.Recipient;

import java.util.List;
import java.util.Optional;

public interface RecipientService {
    List<Recipient> findAll();
    Optional<Recipient> findById(Long id);
    Recipient save(Recipient recipient);
    void delete(Recipient recipient);
}
