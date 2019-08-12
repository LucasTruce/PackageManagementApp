package pl.packagemanagement.service;

import pl.packagemanagement.entity.Sender;

import java.util.List;
import java.util.Optional;

public interface SenderService {
    List<Sender> findAll();
    Optional<Sender> findById(Long id);
    Sender save(Sender sender);
    void delete(Sender sender);
}
