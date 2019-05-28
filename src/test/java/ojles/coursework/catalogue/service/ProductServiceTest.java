package ojles.coursework.catalogue.service;

import ojles.coursework.catalogue.dao.ProductDao;
import ojles.coursework.catalogue.dao.ProductGroupDao;
import ojles.coursework.catalogue.domain.Manufacturer;
import ojles.coursework.catalogue.dto.FindProductRequest;
import ojles.coursework.catalogue.dao.model.ParameterAvailableValues;
import ojles.coursework.catalogue.domain.Product;
import ojles.coursework.catalogue.domain.ProductGroup;
import ojles.coursework.catalogue.dto.FindProductResponse;
import ojles.coursework.catalogue.dto.ProductDto;
import ojles.coursework.catalogue.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private List<Long> productIds = new ArrayList<>();

    @Before
    public void configureDao() {
        productIds.add(2L);
        productIds.add(8L);

        parentGroup.addChildGroup(leaf);
        leaf.addProduct(product1);
        leaf.addProduct(product2);
        leaf.addProduct(product3);

        when(productGroupDao.findById(1L)).thenReturn(Optional.of(parentGroup));
        when(productGroupDao.findById(2L)).thenReturn(Optional.empty());
        when(productGroupDao.findById(3L)).thenReturn(Optional.of(leaf));

        when(productDao.findByIdIn(productIds)).thenAnswer(invocation -> {
            List<Long> ids = (List<Long>) invocation.getArguments()[0];
            ReflectionTestUtils.setField(product1, "id", ids.get(0));
            ReflectionTestUtils.setField(product2, "id", ids.get(1));
            return Arrays.asList(product1, product2);
        });

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

        when(productDao.findAllParameters(Mockito.any(FindProductRequest.class))).thenAnswer(invocation -> {
            FindProductRequest request = (FindProductRequest) invocation.getArguments()[0];
            if (request.getGroupId() == 3L) {
                ParameterAvailableValues parameter1 = new ParameterAvailableValues();
                parameter1.setName("name1");
                parameter1.setValues("value11,value12");
                ParameterAvailableValues parameter2 = new ParameterAvailableValues();
                parameter2.setName("name2");
                parameter2.setValues("value21,value22,value23");
                ParameterAvailableValues parameter3 = new ParameterAvailableValues();
                parameter3.setName("name3");
                parameter3.setValues("value31");
                List<ParameterAvailableValues> parameters = new ArrayList<>();
                parameters.add(parameter1);
                parameters.add(parameter2);
                parameters.add(parameter3);
                return parameters;
            } else {
                throw new RuntimeException("Unrecognized groupId=" + request.getGroupId());
            }
        });
    }

    @Test
    public void testFindProductShouldReturnProductsByIds() {
        request.setProductIds(productIds);
        FindProductResponse response = productService.findProducts(request);
        assertThat(response.getProducts().size(), equalTo(2));
        List<Long> ids = response.getProducts().stream()
                .map(ProductDto::getId)
                .collect(Collectors.toList());
        assertThat(ids, hasItems(2L, 8L));
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
        assertThat(response.getAvailableParameters().size(), equalTo(3));
    }
}
