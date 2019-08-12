package pl.packagemanagement.service;

import pl.packagemanagement.entity.Code;

import java.util.List;
import java.util.Optional;

public interface CodeService {
    List<Code> findAll();
    Optional<Code> findById(Long id);
    Code save(Code code);
    void delete(Code code);
}
