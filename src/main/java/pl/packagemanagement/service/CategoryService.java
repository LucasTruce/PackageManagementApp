package pl.packagemanagement.service;

import pl.packagemanagement.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category category);
    void delete(Category category);

}
