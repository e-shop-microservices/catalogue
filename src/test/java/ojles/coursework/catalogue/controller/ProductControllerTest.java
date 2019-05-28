package ojles.coursework.catalogue.controller;

import ojles.coursework.catalogue.dto.FindProductRequest;
import ojles.coursework.catalogue.service.ProductService;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;


public class ProductControllerTest {
    private ProductService productService = mock(ProductService.class);

    @Test
    public void testParametersThatAreInFindProductRequestShouldBeRemovedFromMap() {
        ProductController productController = new ProductController(productService);

        MultiValueMap<String, String> customParameters = new LinkedMultiValueMap<>();
        customParameters.add("searchQuery", "shoes");
        customParameters.add("groupId", "582");
        customParameters.add("pageIndex", "3");
        customParameters.add("pageSize", "50");
        customParameters.add("minPrice", "3");
        customParameters.add("maxPrice", "50");
        customParameters.add("manufacturerId", "50");
        customParameters.add("customParameter1", "qwe");
        customParameters.add("customParameter2", "rty");
        FindProductRequest request = new FindProductRequest();
        List<Long> productIds = new ArrayList<>();
        productIds.add(32L);
        productController.getProducts(productIds, request, customParameters);

        assertThat(request.getProductIds(), equalTo(productIds));
        assertThat(request.getParameters(), equalTo(customParameters));
        assertThat(customParameters, not(hasKey("id")));
        assertThat(customParameters, not(hasKey("searchQuery")));
        assertThat(customParameters, not(hasKey("groupId")));
        assertThat(customParameters, not(hasKey("pageIndex")));
        assertThat(customParameters, not(hasKey("pageSize")));
        assertThat(customParameters, not(hasKey("minPrice")));
        assertThat(customParameters, not(hasKey("maxPrice")));
        assertThat(customParameters, not(hasKey("manufacturerId")));
        assertThat(customParameters, hasKey("customParameter1"));
        assertThat(customParameters, hasKey("customParameter2"));
    }
}
