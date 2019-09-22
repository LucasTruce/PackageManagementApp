package pl.packagemanagement.model.product;

import pl.packagemanagement.model.content.Content;
import pl.packagemanagement.model.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    List<Product> saveAll(List<Product> products, Content tempContent);
    void delete(Product product);
}
