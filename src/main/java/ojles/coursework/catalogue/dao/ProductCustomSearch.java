package ojles.coursework.catalogue.dao;

import ojles.coursework.catalogue.dto.FindProductRequest;
import ojles.coursework.catalogue.dao.model.ParameterAvailableValues;
import ojles.coursework.catalogue.domain.Product;

import java.util.List;

public interface ProductCustomSearch {
    List<Product> findProducts(FindProductRequest request);

    long countProducts(FindProductRequest request);

    List<ParameterAvailableValues> findAllParameters(FindProductRequest request);
}
