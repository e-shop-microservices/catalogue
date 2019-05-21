package ojles.cursework.catalogue.service;

import lombok.RequiredArgsConstructor;
import ojles.cursework.catalogue.dao.ProductDao;
import ojles.cursework.catalogue.dao.ProductGroupDao;
import ojles.cursework.catalogue.dao.model.ParameterAvailableValues;
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

    @Transactional
    public FindProductResponse findProducts(FindProductRequest request) {
        if (request.getProductIds() != null) {
            List<Product> products = productDao.findByIdIn(request.getProductIds());
            return FindProductResponse.productsByIds(products);
        }

        if (request.getGroupId() != null) {
            ProductGroup group = productGroupDao.findById(request.getGroupId()).orElse(null);
            if (group == null) {
                throw new ResourceNotFoundException("Couldn't find group with id=" + request.getGroupId());
            }
            if (!group.isLeaf()) {
                return FindProductResponse.childGroups(group.getChildren());
            }
        }

        List<Product> products = productDao.findProducts(request);
        long count = productDao.countProducts(request);
        List<ParameterAvailableValues> parameters = productDao.findAllParameters(request);
        return FindProductResponse.products(products, count, parameters);
    }

    @Transactional
    public Product findById(long productId) {
        Product product = productDao.findById(productId).orElse(null);
        if (product == null) {
            throw new ResourceNotFoundException("Couldn't find product with id=" + productId);
        }
        return product;
    }
}
