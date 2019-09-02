package pl.packagemanagement.model.product;

import pl.packagemanagement.model.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Product save(Product product);
    void delete(Product product);
}