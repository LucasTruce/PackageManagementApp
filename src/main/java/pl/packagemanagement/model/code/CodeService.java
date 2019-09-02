package pl.packagemanagement.model.code;

import pl.packagemanagement.model.code.Code;

import java.util.List;
import java.util.Optional;

public interface CodeService {
    List<Code> findAll();
    Optional<Code> findById(Long id);
    Code save(Code code);
    void delete(Code code);
}
