package ec.com.ups.electronic.repository;

import ec.com.ups.electronic.source.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
