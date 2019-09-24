package pl.packagemanagement.model.code;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.packagemanagement.model.pack.Package;
import pl.packagemanagement.model.product.Product;
import pl.packagemanagement.model.product.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {
    private final CodeRepository codeRepository;

    @Override
    public List<Code> findAll() {
        return codeRepository.findAll();
    }

    @Override
    public Optional<Code> findById(Long id) {
        return codeRepository.findById(id);
    }

    @Override
    public void save(Code code) {
        codeRepository.save(code);
    }

    @Override
    public void saveAll(List<Code> codes) {
        codeRepository.saveAll(codes);
    }

    @Override
    public void delete(Code code) {
        codeRepository.delete(code);
    }
}
