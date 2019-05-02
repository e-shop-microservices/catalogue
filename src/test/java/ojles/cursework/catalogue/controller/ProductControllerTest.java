package ojles.cursework.catalogue.controller;

import ojles.cursework.catalogue.dto.FindProductRequest;
import ojles.cursework.catalogue.service.ProductService;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;


public class ProductControllerTest {
    private ProductService productService = mock(ProductService.class);

    @Test
    public void testPropertiesThatAreInFindProductRequestShouldBeRemovedFromMap() {
        ProductController productController = new ProductController(productService);

        Map<String, String> customParameters = new HashMap<>();
        customParameters.put("searchQuery", "shoes");
        customParameters.put("groupId", "582");
        customParameters.put("pageIndex", "3");
        customParameters.put("pageSize", "50");
        customParameters.put("minPrice", "3");
        customParameters.put("maxPrice", "50");
        customParameters.put("manufacturerId", "50");
        customParameters.put("customProperty1", "qwe");
        customParameters.put("customProperty2", "rty");
        FindProductRequest request = new FindProductRequest();
        productController.getProducts(request, customParameters);

        assertThat(request.getParameters(), equalTo(customParameters));
        assertThat(customParameters, not(hasKey("searchQuery")));
        assertThat(customParameters, not(hasKey("groupId")));
        assertThat(customParameters, not(hasKey("pageIndex")));
        assertThat(customParameters, not(hasKey("pageSize")));
        assertThat(customParameters, not(hasKey("minPrice")));
        assertThat(customParameters, not(hasKey("maxPrice")));
        assertThat(customParameters, not(hasKey("manufacturerId")));
        assertThat(customParameters, hasKey("customProperty1"));
        assertThat(customParameters, hasKey("customProperty2"));
    }
}
