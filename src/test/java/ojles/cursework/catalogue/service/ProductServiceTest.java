package ojles.cursework.catalogue.service;

import ojles.cursework.catalogue.dao.ProductDao;
import ojles.cursework.catalogue.dao.ProductGroupDao;
import ojles.cursework.catalogue.domain.Manufacturer;
import ojles.cursework.catalogue.domain.Product;
import ojles.cursework.catalogue.domain.ProductGroup;
import ojles.cursework.catalogue.dto.FindProductRequest;
import ojles.cursework.catalogue.dto.FindProductResponse;
import ojles.cursework.catalogue.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    private final ProductDao productDao = mock(ProductDao.class);
    private final ProductGroupDao productGroupDao = mock(ProductGroupDao.class);

    private ProductService productService = new ProductService(productDao, productGroupDao);

    private FindProductRequest request = new FindProductRequest();

    private ProductGroup parentGroup = new ProductGroup("parent", "imgParent");
    private ProductGroup leaf = new ProductGroup("leaf", "imgLeaf");

    private Product product1 = new Product("name1", "descr1", 123L, "imgPath1", new Manufacturer());
    private Product product2 = new Product("name2", "descr2", 234L, "imgPath2", new Manufacturer());
    private Product product3 = new Product("name3", "descr3", 345L, "imgPath3", new Manufacturer());

    @Before
    public void configureDao() {
        when(productGroupDao.findById(1L)).thenReturn(Optional.of(parentGroup));
        when(productGroupDao.findById(2L)).thenReturn(Optional.empty());
        when(productGroupDao.findById(3L)).thenReturn(Optional.of(leaf));
        when(productDao.findProducts(Mockito.any(FindProductRequest.class))).thenAnswer(invocation -> {
            FindProductRequest request = (FindProductRequest) invocation.getArguments()[0];
            if (request.getGroupId() == 3L) {
                return Arrays.asList(product1, product2, product3);
            } else {
                throw new RuntimeException("Unrecognized groupId=" + request.getGroupId());
            }
        });
        when(productDao.countProducts(Mockito.any(FindProductRequest.class))).thenAnswer(invocation -> {
            FindProductRequest request = (FindProductRequest) invocation.getArguments()[0];
            if (request.getGroupId() == 3L) {
                return 120L;
            } else {
                throw new RuntimeException("Unrecognized groupId=" + request.getGroupId());
            }
        });

        parentGroup.addChildGroup(leaf);

        leaf.addProduct(product1);
        leaf.addProduct(product2);
        leaf.addProduct(product3);
    }

    @Test
    public void testFindProductsShouldReturnChildGroupsWhenRequestedGroupIsNotALeaf() {
        request.setGroupId(1L);
        FindProductResponse response = productService.findProducts(request);
        assertThat(response.getChildGroups().size(), equalTo(1));
        assertThat(response.getProducts(), is(nullValue()));
        assertThat(response.getTotalAmount(), is(nullValue()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testFindProductsShouldThrowExceptionWhenGroupNotFound() {
        request.setGroupId(2L);
        productService.findProducts(request);
    }

    @Test
    public void testFindProductsShouldReturnProductsWhenGroupIsLeaf() {
        request.setGroupId(3L);
        FindProductResponse response = productService.findProducts(request);
        assertThat(response.getProducts().size(), equalTo(3));
        assertThat(response.getTotalAmount(), equalTo(120L));
    }
}
