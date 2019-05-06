package ojles.cursework.catalogue.service;

import lombok.RequiredArgsConstructor;
import ojles.cursework.catalogue.dao.ProductDao;
import ojles.cursework.catalogue.dao.ProductGroupDao;
import ojles.cursework.catalogue.domain.Product;
import ojles.cursework.catalogue.domain.ProductGroup;
import ojles.cursework.catalogue.dto.FindProductRequest;
import ojles.cursework.catalogue.dto.FindProductResponse;
import ojles.cursework.catalogue.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;
    private final ProductGroupDao productGroupDao;

    /**
     * Need to return:
     * - products and their parameters. Don't aggregate all parameters, do that in another service
     * - page size and index
     */
    @Transactional
    public FindProductResponse findProducts(FindProductRequest request) {
        FindProductResponse response = new FindProductResponse();
        if (request.getGroupId() != null) {
            ProductGroup group = productGroupDao.findById(request.getGroupId()).orElse(null);
            if (group == null) {
                throw new ResourceNotFoundException("Couldn't find group with id=" + request.getGroupId());
            }
            if (!group.isLeaf()) {
                response.setChildGroups(group.getChildren());
                return response;
            }
        }
        List<Product> products = productDao.findProducts(request);
        long count = productDao.countProducts(request);
        response.setProducts(products);
        response.setTotalAmount(count);
        return response;
    }
}
