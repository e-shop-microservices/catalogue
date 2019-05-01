package ojles.cursework.catalogue.controller;

import ojles.cursework.catalogue.dto.FindProductRequest;
import ojles.cursework.catalogue.service.ProductService;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;


public class ProductControllerTest {
    private ProductService productService = mock(ProductService.class);

    @Test
    public void testPropertiesInFindProductRequestShouldBeRemovedFromMap() throws Exception {
        ProductController productController = new ProductController(productService);

        MultiValueMap<String, String> customParameters = new LinkedMultiValueMap<>();
        customParameters.add("searchQuery", "shoes");
        customParameters.add("groupId", "582");
        customParameters.add("pageIndex", "3");
        customParameters.add("pageSize", "50");
        customParameters.add("customProperty1", "qwe");
        customParameters.add("customProperty2", "rty");
        FindProductRequest request = new FindProductRequest();
        productController.getProducts(request, customParameters);

        assertThat(request.getCustomProperties(), equalTo(customParameters));
        assertThat(customParameters, not(hasEntry("searchQuery", singletonList("shoes"))));
        assertThat(customParameters, not(hasEntry("groupId", singletonList("582"))));
        assertThat(customParameters, not(hasEntry("pageIndex", singletonList("3"))));
        assertThat(customParameters, not(hasEntry("pageSize", singletonList("50"))));
        assertThat(customParameters, hasEntry("customProperty1", singletonList("qwe")));
        assertThat(customParameters, hasEntry("customProperty2", singletonList("rty")));
    }
}
