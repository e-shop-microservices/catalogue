package ojles.coursework.catalogue.service;

import lombok.RequiredArgsConstructor;
import ojles.coursework.catalogue.dao.ProductDao;
import ojles.coursework.catalogue.dao.ProductGroupDao;
import ojles.coursework.catalogue.dao.model.ParameterAvailableValues;
import ojles.coursework.catalogue.domain.Product;
import ojles.coursework.catalogue.domain.ProductGroup;
import ojles.coursework.catalogue.dto.FindProductRequest;
import ojles.coursework.catalogue.dto.FindProductResponse;
import ojles.coursework.catalogue.exception.ResourceNotFoundException;
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
