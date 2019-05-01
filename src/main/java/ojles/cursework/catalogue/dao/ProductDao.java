package ojles.cursework.catalogue.dao;

import ojles.cursework.catalogue.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Long> {
}
