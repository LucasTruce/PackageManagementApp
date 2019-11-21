package pl.packagemanagement.model.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.packagemanagement.exception.EntityNotFoundException;
import pl.packagemanagement.model.category.Category;
import pl.packagemanagement.model.category.CategoryRepository;
import pl.packagemanagement.model.code.Code;
import pl.packagemanagement.model.code.CodeRepository;
import pl.packagemanagement.model.code.CodeService;
import pl.packagemanagement.model.content.Content;
import pl.packagemanagement.model.content.ContentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CodeRepository codeRepository;

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
    public List<Product> saveAll(List<Product> products) {
        for (Product product: products) {
            Category tempCategory = categoryRepository.findCategoryById(product.getCategory().getId());
            product.setCategory(tempCategory);
            Code code = codeRepository.save(product.getCode());
            product.setCode(code);
        }

        return productRepository.saveAll(products);
    }

    @Override
    public void deleteAll(List<Product> products) {
        productRepository.deleteAll(products);
    }
}
