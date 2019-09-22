package pl.packagemanagement.model.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.model.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryById(Long id);
}
