package ojles.cursework.catalogue.service;

import lombok.RequiredArgsConstructor;
import ojles.cursework.catalogue.dao.ProductDao;
import ojles.cursework.catalogue.dao.ProductGroupDao;
import ojles.cursework.catalogue.domain.ProductGroup;
import ojles.cursework.catalogue.dto.FindProductRequest;
import ojles.cursework.catalogue.dto.FindProductResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;
    private final ProductGroupDao productGroupDao;

    /**
     *
     * Need to return:
     *  - products and their parameters. Don't aggregate all parameters, do that in another service
     *  - page size and index
     *
     */

    public FindProductResponse findProducts(FindProductRequest request) {
        if (request.getSearchQuery() == null) {
            // find products by searchQuery and customProperties
            // ignore groupId
        } else {
            ProductGroup group = null; // find in database
            if (group.isLeaf()) {
                // find products in group by customParameters
                // ignore searchQuery
            } else {
                // return group children
            }
        }
        return null;
    }
}
