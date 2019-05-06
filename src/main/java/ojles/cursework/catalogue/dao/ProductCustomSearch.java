package ojles.cursework.catalogue.dao;

import ojles.cursework.catalogue.domain.Product;
import ojles.cursework.catalogue.dto.FindProductRequest;

import java.util.List;

public interface ProductCustomSearch {
    List<Product> findProducts(FindProductRequest request);

    long countProducts(FindProductRequest request);
}
