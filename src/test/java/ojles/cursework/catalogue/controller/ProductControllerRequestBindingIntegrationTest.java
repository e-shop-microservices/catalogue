package ojles.cursework.catalogue.controller;

import ojles.cursework.catalogue.dto.FindProductRequest;
import ojles.cursework.catalogue.dto.FindProductResponse;
import ojles.cursework.catalogue.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@WebMvcTest
@RunWith(SpringRunner.class)
@MockBean(ProductService.class)
public class ProductControllerRequestBindingIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductController productController;

    @Test
    public void testFindProductRequestShouldBindCorrectly() throws Exception {
        when(productController.getProducts(any(FindProductRequest.class), any(MultiValueMap.class)))
                .thenAnswer(invocation -> {
                    FindProductRequest request = (FindProductRequest) invocation.getArguments()[0];
                    assertThat(request.getSearchQuery(), equalTo("shoes"));
                    assertThat(request.getGroupId(), equalTo(913L));
                    assertThat(request.getPageIndex(), equalTo(2));
                    assertThat(request.getPageSize(), equalTo(25));
                    assertThat(request.getMinPrice(), equalTo(1234L));
                    assertThat(request.getMaxPrice(), equalTo(2345L));
                    assertThat(request.getManufacturerId(), equalTo(384));
                    assertThat(request.getParameters().size(), equalTo(0));
                    return new FindProductResponse();
                });

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .param("searchQuery", "shoes")
                        .param("groupId", "913")
                        .param("pageIndex", "2")
                        .param("pageSize", "25")
                        .param("minPrice", "1234")
                        .param("maxPrice", "2345")
                        .param("manufacturerId", "384")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testParametersMapShouldMapCorrectly() throws Exception {
        when(productController.getProducts(any(FindProductRequest.class), any(MultiValueMap.class)))
                .thenAnswer(invocation -> {
                    MultiValueMap<String, String> parameters = (MultiValueMap<String, String>) invocation.getArguments()[1];
                    assertThat(parameters.size(), is(equalTo(2)));
                    assertThat(parameters.get("customParameter1").size(), equalTo(1));
                    assertThat(parameters.get("customParameter2").size(), equalTo(2));
                    assertThat(parameters.get("customParameter1").get(0), equalTo("value1"));
                    assertThat(parameters.get("customParameter2").get(0), equalTo("value2"));
                    assertThat(parameters.get("customParameter2").get(1), equalTo("value3"));
                    return new FindProductResponse();
                });

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .param("customParameter1", "value1")
                        .param("customParameter2", "value2")
                        .param("customParameter2", "value3")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
