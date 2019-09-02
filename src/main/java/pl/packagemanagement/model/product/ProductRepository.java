package pl.packagemanagement.model.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.packagemanagement.model.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
