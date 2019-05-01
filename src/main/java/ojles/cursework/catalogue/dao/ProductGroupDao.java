package ojles.cursework.catalogue.dao;

import ojles.cursework.catalogue.domain.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductGroupDao extends JpaRepository<ProductGroup, Long> {
}
