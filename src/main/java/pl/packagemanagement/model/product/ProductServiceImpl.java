package pl.packagemanagement.model.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.packagemanagement.model.category.Category;
import pl.packagemanagement.model.category.CategoryRepository;
import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.model.code.CodeService;
import pl.packagemanagement.model.content.Content;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> saveAll(List<Product> products, Content tempContent) {
        for (Product product: products) {
            Category tempCategory = categoryRepository.findCategoryById(product.getCategory().getId());
            tempCategory.getProducts().add(product);
            product.setContent(tempContent);
            tempContent.getProducts().add(product);
        }
        return productRepository.saveAll(products);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }
}
