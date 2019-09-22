package pl.packagemanagement.model.content;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {
    private final ContentRepository contentRepository;

    @Override
    public List<Content> findAll() {
        return contentRepository.findAll();
    }

    @Override
    public Optional<Content> findById(Long id) {
        return contentRepository.findById(id);
    }

    @Override
    public Content save(Content content) {
        return contentRepository.save(content);
    }

    @Override
    public void delete(Content content) {
        contentRepository.delete(content);
    }
}
