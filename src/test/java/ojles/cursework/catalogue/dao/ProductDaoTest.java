package ojles.cursework.catalogue.dao;

import ojles.cursework.catalogue.domain.Manufacturer;
import ojles.cursework.catalogue.domain.Product;
import ojles.cursework.catalogue.domain.ProductGroup;
import ojles.cursework.catalogue.dto.FindProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@Sql("/sql/productCustomSearch.sql")
public class ProductDaoTest {
    @Autowired
    private ProductDao productDao;

    private FindProductRequest request = new FindProductRequest();

    @Test
    public void testFindProductsSearchQueryFilter() {
        request.setSearchQuery("Lasting");

        List<Product> products = productDao.findProducts(request);
        assertThat(products.size(), equalTo(2));
        assertThat(products.get(0).getName(), containsString("Lasting"));
        assertThat(products.get(1).getName(), containsString("Lasting"));

        request.setSearchQuery("descripti");
        products = productDao.findProducts(request);

        // all products
        assertThat(products.size(), equalTo(7));
    }

    @Test
    public void testFindProductsMinPriceFilter() {
        long minPrice = 1995L;
        request.setMinPrice(minPrice);

        List<Product> products = productDao.findProducts(request);

        assertThat(products.size(), equalTo(4));
        for (Product product : productDao.findProducts(request)) {
            assertThat(product.getPrice(), greaterThanOrEqualTo(minPrice));
        }
    }

    @Test
    public void testFindProductsMaxPriceFilter() {
        long maxPrice = 5000L;
        request.setMaxPrice(maxPrice);

        List<Product> products = productDao.findProducts(request);

        assertThat(products.size(), equalTo(5));
        for (Product product : products) {
            assertThat(product.getPrice(), lessThanOrEqualTo(maxPrice));
        }
    }

    @Test
    public void testFindProductsMinAndMaxPriceFilter() {
        long minPrice = 300L;
        long maxPrice = 3194L;
        request.setMinPrice(minPrice);
        request.setMaxPrice(maxPrice);

        List<Product> products = productDao.findProducts(request);

        assertThat(products.size(), equalTo(3));
        for (Product product : products) {
            assertThat(product.getPrice(), allOf(
                    greaterThanOrEqualTo(minPrice),
                    lessThanOrEqualTo(maxPrice)
            ));
        }
    }

    @Test
    public void testFindProductsGroupIdFilter() {
        long groupId = 2L;
        request.setGroupId(groupId);

        List<Product> products = productDao.findProducts(request);
        assertThat(products.size(), equalTo(4));

        ProductGroup anyGroup = (ProductGroup) ReflectionTestUtils.getField(products.get(0), "group");
        assertThat(anyGroup.getId(), equalTo(groupId));

        for (Product product : products) {
            ProductGroup otherGroup = (ProductGroup) ReflectionTestUtils.getField(product, "group");
            assertThat(anyGroup, equalTo(otherGroup));
        }
    }

    @Test
    public void testFindProductsManufacturerIdFilter() {
        int manufacturerId = 1;
        request.setManufacturerId(manufacturerId);

        List<Product> products = productDao.findProducts(request);
        assertThat(products.size(), equalTo(2));

        Manufacturer anyManufacturer = (Manufacturer) ReflectionTestUtils.getField(products.get(0), "manufacturer");
        assertThat(anyManufacturer.getId(), equalTo(manufacturerId));

        for (Product product : products) {
            Manufacturer otherManufacturer = (Manufacturer) ReflectionTestUtils.getField(product, "manufacturer");
            assertThat(anyManufacturer, equalTo(otherManufacturer));
        }
    }

    @Test
    public void testFindProductsSingleParameterFilter() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("season", "summer");
        request.setParameters(parameters);

        List<Product> products = productDao.findProducts(request);
        assertThat(products.size(), equalTo(1));
        assertThat(products.get(0).getId(), equalTo(4L));
    }

    @Test
    public void testFindProductMultipleParametersFilter() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("color", "green");
        parameters.put("gender", "male");
        request.setParameters(parameters);

        List<Product> products = productDao.findProducts(request);
        assertThat(products.size(), equalTo(1));
        assertThat(products.get(0).getId(), equalTo(3L));
    }

    @Test
    public void testFindProductSearchQueryAndMinPriceAndParametersFilter() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("season", "winter");

        request.setParameters(parameters);
        request.setSearchQuery("Salewa");
        request.setMinPrice(4000L);

        List<Product> products = productDao.findProducts(request);
        assertThat(products.size(), equalTo(1));
        assertThat(products.get(0).getId(), equalTo(3L));
    }

    @Test
    public void testCountProductsSearchQueryFilter() {
        request.setSearchQuery("tI");
        long count = productDao.countProducts(request);
        // all products
        assertThat(count, equalTo(7L));

        request.setSearchQuery("x-socks");
        count = productDao.countProducts(request);
        assertThat(count, equalTo(1L));
    }

    @Test
    public void testCountProductsMinPriceFilter() {
        long minPrice = 1995L;
        request.setMinPrice(minPrice);
        long count = productDao.countProducts(request);
        assertThat(count, equalTo(4L));
    }

    @Test
    public void testCountProductsMaxPriceFilter() {
        long maxPrice = 5000L;
        request.setMaxPrice(maxPrice);
        long count = productDao.countProducts(request);
        assertThat(count, equalTo(5L));
    }

    @Test
    public void testCountProductsMinAndMaxPriceFilter() {
        long minPrice = 300L;
        long maxPrice = 3194L;
        request.setMinPrice(minPrice);
        request.setMaxPrice(maxPrice);
        long count = productDao.countProducts(request);
        assertThat(count, equalTo(3L));
    }

    @Test
    public void testCountProductsGroupIdFilter() {
        long groupId = 2L;
        request.setGroupId(groupId);
        long count = productDao.countProducts(request);
        assertThat(count, equalTo(4L));
    }

    @Test
    public void testCountProductsManufacturerIdFilter() {
        int manufacturerId = 1;
        request.setManufacturerId(manufacturerId);
        long count = productDao.countProducts(request);
        assertThat(count, equalTo(2L));
    }

    @Test
    public void testCountProductsSingleParameterFilter() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("season", "summer");
        request.setParameters(parameters);
        long count = productDao.countProducts(request);
        assertThat(count, equalTo(1L));
    }

    @Test
    public void testCountProductMultipleParametersFilter() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("color", "green");
        parameters.put("gender", "male");
        request.setParameters(parameters);
        long count = productDao.countProducts(request);
        assertThat(count, equalTo(1L));
    }

    @Test
    public void testCountProductSearchQueryAndMinPriceAndParametersFilter() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("season", "winter");
        request.setParameters(parameters);
        request.setSearchQuery("Salewa");
        request.setMinPrice(4000L);
        long count = productDao.countProducts(request);
        assertThat(count, equalTo(1L));
    }
}