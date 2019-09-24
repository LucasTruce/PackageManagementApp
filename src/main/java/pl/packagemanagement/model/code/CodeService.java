package pl.packagemanagement.model.code;

import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.product.Product;

import java.util.List;
import java.util.Optional;

public interface CodeService {
    List<Code> findAll();
    Optional<Code> findById(Long id);
    void save(Code code);
    void saveAll(List<Code> codes);
    void delete(Code code);
}
