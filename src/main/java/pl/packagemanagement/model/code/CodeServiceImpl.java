package pl.packagemanagement.model.code;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodeServiceImpl implements CodeService {
    CodeRepository codeRepository;

    public CodeServiceImpl(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    @Override
    public List<Code> findAll() {
        return codeRepository.findAll();
    }

    @Override
    public Optional<Code> findById(Long id) {
        return codeRepository.findById(id);
    }

    @Override
    public Code save(Code code) {
        return codeRepository.save(code);
    }

    @Override
    public void delete(Code code) {
        codeRepository.delete(code);
    }
}
