package ojles.cursework.catalogue.dao;

import ojles.cursework.catalogue.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Long>, ProductCustomSearch {
    List<Product> findByIdIn(List<Long> ids);
}
