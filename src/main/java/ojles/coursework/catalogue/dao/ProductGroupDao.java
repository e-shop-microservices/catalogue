package ojles.coursework.catalogue.dao;

import ojles.coursework.catalogue.domain.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductGroupDao extends JpaRepository<ProductGroup, Long> {
}
