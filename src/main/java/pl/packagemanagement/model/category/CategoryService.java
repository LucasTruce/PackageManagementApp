package pl.packagemanagement.model.category;

import pl.packagemanagement.model.category.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Optional<Category> findById(Long id);
    Category save(Category category);
    void delete(Category category);

}
