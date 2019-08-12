package pl.packagemanagement.service;

import pl.packagemanagement.entity.Content;

import java.util.List;
import java.util.Optional;

public interface ContentService {
    List<Content> findAll();
    Optional<Content> findById(Long id);
    Content save(Content content);
    void delete(Content content);
}
